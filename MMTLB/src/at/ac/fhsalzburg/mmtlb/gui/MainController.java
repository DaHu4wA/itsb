package at.ac.fhsalzburg.mmtlb.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import at.ac.fhsalzburg.mmtlb.applications.ContrastStretching;
import at.ac.fhsalzburg.mmtlb.applications.FileImageConverter;
import at.ac.fhsalzburg.mmtlb.applications.GammaCorrection;
import at.ac.fhsalzburg.mmtlb.applications.HistogramTools;
import at.ac.fhsalzburg.mmtlb.applications.ImageModificationType;
import at.ac.fhsalzburg.mmtlb.gui.applications.MainView;
import at.ac.fhsalzburg.mmtlb.gui.imagepanel.AdditionalDataPanel;
import at.ac.fhsalzburg.mmtlb.gui.imagepanel.ImagePreviewFileChooser;
import at.ac.fhsalzburg.mmtlb.gui.imagepanel.NoAdditionalDataPanel;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageReader;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageWriter;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Main window for the application with buttons and a image view panel
 * 
 * @author Stefan Huber
 */
public class MainController extends JFrame implements IFImageController {
	private static final long serialVersionUID = -958626226425855658L;
	private static final Logger LOG = Logger.getLogger(MainController.class.getSimpleName());
	public static String TITLE_TEXT = "Da Hu4wA's Photoshop - Professional Edition";

	final MainView view;

	private MMTImage originalImage = null;
	private MMTImage currentImage = null;

	public MainController() {
		setSize(800, 800);
		// setExtendedState(JFrame.MAXIMIZED_BOTH);
		setTitle(TITLE_TEXT);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon(MainController.class.getResource("icon.png")).getImage());
		setLocationRelativeTo(null); // set window centered to screen

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		view = new MainView();

		setContentPane(view);
		addButtonListeners();

		setVisible(true);
	}

	private void addButtonListeners() {

		view.getOpenFileButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread(new Runnable() {
					// new thread to prevent file access from gui-thread
					// (EDT-Thread)
					@Override
					public void run() {
						openImageFile();
					}
				});
				t.start();
				view.getOpenFileButton().setEnabled(false);
				view.getOpenFileButton().setText("Opening file selector...");
			}
		});

		view.getConvertFileButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						saveFile();
					}
				});
				t.start();

			}
		});

		view.getConvertWholeFolderToPNG().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						convertFromJpgToPng();
					}
				});
				t.start();
				view.getConvertWholeFolderToPNG().setEnabled(false);
			}
		});

		view.getRevertButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentImage = new MMTImage(originalImage);
				view.setMMTImage(currentImage);
				view.getRevertButton().setEnabled(false);
				repaint();
			}
		});

		setApplicationsListeners();

		view.getFooterPanel().getScaleSlider().addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent e) {
				double scale = (double) view.getFooterPanel().getScaleSlider().getValue();
				view.getMmtImagePanel().setScale(scale / 100);
			}
		});
	}

	/**
	 * Adds the buttons to the gui and sets them enabled (or not)
	 */
	/**
	 * File chooser for opening an image
	 */
	private void openImageFile() {
		JFileChooser fileChooser = new JFileChooser();
		
		ImagePreviewFileChooser preview = new ImagePreviewFileChooser(fileChooser);
		fileChooser.addPropertyChangeListener(preview);
		fileChooser.setAccessory(preview);
		
		fileChooser.setDialogTitle("Choose an image to open");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnVal = fileChooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			LOG.debug(String.format("File selected: %s", file.getName()));

			openImageFile(file);

		} else {
			LOG.info("No file selected");
			view.getOpenFileButton().setEnabled(true);
			view.getOpenFileButton().setText(MainView.OPEN_IMAGE_TEXT);
		}

	}

	private void convertFromJpgToPng() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select folder to convert");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fileChooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File directory = fileChooser.getSelectedFile();

			// do the real conversion
			int count = FileImageConverter.convertFolderFromJpgToPng(directory);

			postFileConvertSuccessMessage(count);
		} else {
			LOG.info("No folder selected");
		}
		view.getConvertWholeFolderToPNG().setEnabled(true);
	}

	private void postFileConvertSuccessMessage(int count) {
		JOptionPane.showMessageDialog(this, "Conversion finished!\n" + "\n" + count + " jpg file(s) converted." + "\nResults saved into subfolder *"
				+ FileImageConverter.SUBFOLDER_NAME + "*", "Conversion finished!", JOptionPane.INFORMATION_MESSAGE);
	}

	private void saveFile() {

		if (currentImage == null) {
			LOG.error("Aborting - MMTImage not exists!!");
			return;
		}

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save converted image");
		fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		fileChooser.setSelectedFile(new File(currentImage.getName() + "_modified" + ".jpg"));

		int returnVal = fileChooser.showSaveDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// write file if path was selected
			File file = fileChooser.getSelectedFile();
			FileImageWriter.write(currentImage, file);

		} else {
			LOG.info("No file selected");
		}
	}

	/**
	 * @param file
	 *            that should be displayed
	 */
	private void openImageFile(File file) {
		originalImage = FileImageReader.read(file);

		view.getOpenFileButton().setEnabled(true);
		view.getOpenFileButton().setText(MainView.OPEN_IMAGE_TEXT);
		view.getApplicationsPanel().setEnabled(true);

		if (originalImage == null) {
			JOptionPane.showMessageDialog(view, "File could not be opened! \nOnly pictures are supported!", "Error: Unsupported file type!",
					JOptionPane.ERROR_MESSAGE);
			originalImage = new MMTImage(currentImage);
			return;
		}

		currentImage = new MMTImage(originalImage);
		view.setMMTImage(currentImage);
		view.getConvertFileButton().setEnabled(true);
		view.getFooterPanel().getScaleSlider().setEnabled(true);

		setModificationDataPanel(ImageModificationType.CONTRAST_STRETCHING);

		repaint();
	}

	private void setApplicationsListeners() {

		view.getApplicationsPanel().getModificationTypeBox().addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					ImageModificationType selectedType = (ImageModificationType) e.getItem();
					setModificationDataPanel(selectedType);
				}
			}
		});

		// view.getApplicationsPanel().getBtnApply().addActionListener(new
		// ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		//
		// ImageModificationType selected = (ImageModificationType)
		// view.getApplicationsPanel().getModificationTypeBox().getSelectedItem();
		//
		// addModificationDataPanel(selected);
		// }
		// });
	}

	private void setModificationDataPanel(ImageModificationType action) {

		if (currentImage == null) {
			JOptionPane.showMessageDialog(view, "No image opened!", "Image has to be opened", JOptionPane.ERROR_MESSAGE);
			return;
		}

		view.getApplicationsPanel().removeAdditionalDataPanel();

		switch (action) {

		case CONTRAST_STRETCHING:

			NoAdditionalDataPanel go = new NoAdditionalDataPanel();
			view.getApplicationsPanel().setAdditionalDataPanel(go);

			go.getGo().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ContrastStretching stretcher = new ContrastStretching(MainController.this, currentImage);
					stretcher.execute();
				}
			});
			break;

		case GAMMA_CORRECTION:

			final AdditionalDataPanel addData = new AdditionalDataPanel(0, 1000, 100);
			view.getApplicationsPanel().setAdditionalDataPanel(addData);

			addData.getGo().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					GammaCorrection gammaCorr = new GammaCorrection(MainController.this, currentImage);
					double gamma = ((new Double((double) addData.getValue())) / 100d);
					LOG.info("Gamma: " + gamma);
					gammaCorr.setGamma(gamma);
					gammaCorr.execute();
				}
			});
			break;

		case HISTOGRAM_EQUALIZATION:
			NoAdditionalDataPanel go2 = new NoAdditionalDataPanel();
			view.getApplicationsPanel().setAdditionalDataPanel(go2);

			go2.getGo().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					HistogramTools hist = new HistogramTools(MainController.this, currentImage);
					hist.execute();
				}
			});
			break;

		default:
			JOptionPane.showMessageDialog(view, "Sorry, \nit looks like this function has not been implemented yet!", "Feature not implemented yet",
					JOptionPane.ERROR_MESSAGE);
			break;
		}

	}

	@Override
	public void setCurrentImage(MMTImage newImage) {
		currentImage = newImage;
		view.setMMTImage(currentImage);
		view.getRevertButton().setEnabled(true);
	}

	@Override
	public void setProgressBarVisible(boolean visible) {
		view.getFooterPanel().showProgressBar(visible);
	}

	@Override
	public void setProgressStatus(int progress) {
		view.getFooterPanel().setProgress(progress);
	}

}
