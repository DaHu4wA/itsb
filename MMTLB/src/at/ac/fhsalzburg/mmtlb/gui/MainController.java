package at.ac.fhsalzburg.mmtlb.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import at.ac.fhsalzburg.mmtlb.applications.ContrastStretching;
import at.ac.fhsalzburg.mmtlb.applications.FileImageConverter;
import at.ac.fhsalzburg.mmtlb.applications.ImageModificationType;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageReader;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageWriter;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Main window for the application with buttons and a image view panel
 * 
 * @author Stefan Huber
 */
public class MainController extends JFrame {
	private static final long serialVersionUID = -958626226425855658L;
	private static final Logger LOG = Logger.getLogger(MainController.class.getSimpleName());
	public static String TITLE_TEXT = "Da Hu4wA's Photoshop - Professional Edition";

	final MainView view;

	MMTImage originalImage = null;
	MMTImage currentImage = null;

	public MainController() {
		setSize(800, 600);
		setTitle(TITLE_TEXT);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setIconImage(new ImageIcon(MainController.class.getResource("icon.png")).getImage());

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

	}

	/**
	 * Adds the buttons to the gui and sets them enabled (or not)
	 */
	/**
	 * File chooser for opening an image
	 */
	private void openImageFile() {
		JFileChooser fileChooser = new JFileChooser();
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
		fileChooser.setSelectedFile(new File("result-assignm1-" + System.currentTimeMillis() + ".jpg"));

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
		currentImage = new MMTImage(originalImage);

		view.getOpenFileButton().setEnabled(true);
		view.getOpenFileButton().setText(MainView.OPEN_IMAGE_TEXT);
		view.getApplicationsPanel().setEnabled(true);

		if (originalImage == null) {
			JOptionPane.showMessageDialog(view, "File could not be opened! \nOnly pictures are supported!", "Error: Unsupported file type!",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		view.setMMTImage(currentImage);

		view.getConvertFileButton().setEnabled(true);
		// if (!isMaximumSizeSet())
		// pack();
		repaint();
	}

	private void setApplicationsListeners() {

		view.getApplicationsPanel().getBtnApply().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ImageModificationType selected = (ImageModificationType) view.getApplicationsPanel().getModificationTypeBox().getSelectedItem();

				doImageModification(selected);
			}
		});
	}

	private void doImageModification(ImageModificationType action) {

		if (currentImage == null) {
			JOptionPane.showMessageDialog(view, "No image opened!", "Image has to be opened", JOptionPane.ERROR_MESSAGE);
			return;
		}

		switch (action) {
		case CONTRAST_STRETCHING:
			doContrastStretching();
			break;

		default:
			JOptionPane.showMessageDialog(view, "Sorry, \nit looks like this function has not been implemented yet!", "Feature not implemented yet",
					JOptionPane.ERROR_MESSAGE);
			break;
		}
		view.getRevertButton().setEnabled(true);
	}

	private void doContrastStretching() {
		currentImage = ContrastStretching.stretchContrast(currentImage);
		view.setMMTImage(currentImage);
		repaint();
	}

}
