package at.ac.fhsalzburg.mmtlb.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import at.ac.fhsalzburg.mmtlb.applications.ImageModificationType;
import at.ac.fhsalzburg.mmtlb.applications.comparations.ComparationFinishedCallback;
import at.ac.fhsalzburg.mmtlb.applications.comparations.GlobalMeanImageComparator;
import at.ac.fhsalzburg.mmtlb.applications.comparations.ImageWithImageSetComparator;
import at.ac.fhsalzburg.mmtlb.applications.comparations.VarianceImageComparator;
import at.ac.fhsalzburg.mmtlb.applications.tools.FileImageConverter;
import at.ac.fhsalzburg.mmtlb.applications.tools.MMTImageCombiner;
import at.ac.fhsalzburg.mmtlb.gui.comparation.ImageComparator;
import at.ac.fhsalzburg.mmtlb.gui.panels.ImagePreviewFileChooser;
import at.ac.fhsalzburg.mmtlb.gui.panels.MmtGlassPane;
import at.ac.fhsalzburg.mmtlb.interfaces.IFImageController;
import at.ac.fhsalzburg.mmtlb.interfaces.Stoppable;
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
	public static String TITLE_TEXT = "Da Hu4wA's Image Filter App";
	private static final int CHANGE_HISTORY_SIZE = 50;

	final MainView view;
	final MmtGlassPane glassPane;

	private final ImageModificationHelper modificationHelper;

	// the list data with index 0 is the original image
	private List<MMTImage> changeHistory = new ArrayList<MMTImage>();
	private MMTImage currentImage = null;

	public MainController() {
		setSize(800, 800);
		setTitle(TITLE_TEXT);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
		modificationHelper = new ImageModificationHelper(this);
		glassPane = new MmtGlassPane();
		setGlassPane(glassPane);
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
						openImageFile(true, "Choose an image to open");
					}
				});
				t.start();
				view.getOpenFileButton().setEnabled(false);
				view.getOpenFileButton().setText("Opening file selector...");
			}
		});

		view.getSaveButton().addActionListener(new ActionListener() {

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

		view.getCompareGlobalMean().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				compareImagesAverage();
			}
		});

		view.getCompareVariance().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				compareImagesVariance();
			}
		});

		view.getCompareGlobalMeanFolder().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				compareWithFolderImages(ImageWithImageSetComparator.AVERAGE_OPTION, null);
			}
		});

		view.getCompareVarianceFolder().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				compareWithFolderImages(ImageWithImageSetComparator.VARIANCE_OPTION, null);
			}
		});

		view.getCompareHistogramFolder().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int r = Integer.parseInt(JOptionPane.showInputDialog(MainController.this, "Enter \"r\" for ", 1).toString());

				compareWithFolderImages(ImageWithImageSetComparator.HISTOGRAM_OPTION, r);
			}
		});
		
		view.getCompareShapeFolder().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int r = Integer.parseInt(JOptionPane.showInputDialog(MainController.this, "Enter \"r\" for ", 1).toString());

				compareWithFolderImages(ImageWithImageSetComparator.SHAPE_BASED_OPTION, r);
			}
		});

		view.getUndoButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int lastIndex = changeHistory.size() - 1;
				currentImage = new MMTImage(changeHistory.get(lastIndex));
				changeHistory.remove(lastIndex);

				view.setMMTImage(currentImage);

				if (changeHistory.size() <= 1) {
					enableUndoAndCompare(false);
				}
				repaint();
			}
		});

		view.getCompareButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double scale = view.getFooterPanel().getScaleSlider().getValue();
				ImageComparator c = new ImageComparator(MainController.this, currentImage, changeHistory.get(0), scale / 100);
				c.setVisible(true);
			}
		});

		setApplicationsListeners();

		view.getFooterPanel().getScaleSlider().addChangeListener(new javax.swing.event.ChangeListener() {
			@Override
			public void stateChanged(javax.swing.event.ChangeEvent e) {
				double scale = view.getFooterPanel().getScaleSlider().getValue();
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
	private MMTImage openImageFile(boolean show, String dialogTitle) {
		MMTImage result = null;
		JFileChooser fileChooser = new JFileChooser();

		ImagePreviewFileChooser preview = new ImagePreviewFileChooser(fileChooser);
		fileChooser.addPropertyChangeListener(preview);
		fileChooser.setAccessory(preview);

		fileChooser.setDialogTitle(dialogTitle);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnVal = fileChooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			LOG.debug(String.format("File selected: %s", file.getName()));

			result = openImageFile(file, show);

		} else {
			LOG.info("No file selected");
			view.getOpenFileButton().setEnabled(true);
			view.getOpenFileButton().setText(MainView.OPEN_IMAGE_TEXT);
		}
		return result;
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
				+ FileImageConverter.RESULT_SUBFOLDER_NAME + "*", "Conversion finished!", JOptionPane.INFORMATION_MESSAGE);
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
	private MMTImage openImageFile(File file, boolean showImage) {

		if (!changeHistory.isEmpty() && showImage) {
			view.getApplicationsPanel().getModificationTypeBox().setSelectedItem(ImageModificationType.CONTRAST_STRETCHING);
			setModificationDataPanel(ImageModificationType.CONTRAST_STRETCHING);
		}

		MMTImage openedImage = FileImageReader.read(file);

		if (showImage) {
			view.getOpenFileButton().setEnabled(true);
			view.getOpenFileButton().setText(MainView.OPEN_IMAGE_TEXT);
			view.getApplicationsPanel().setEnabled(true);
		}

		if (openedImage == null) {
			JOptionPane.showMessageDialog(view, "File could not be opened! \nOnly pictures are supported!", "Error: Unsupported file type!",
					JOptionPane.ERROR_MESSAGE);
			changeHistory.clear();
			changeHistory.add(new MMTImage(currentImage));
			return null;
		}

		if (showImage) {
			changeHistory.clear();
			currentImage = new MMTImage(openedImage);
			changeHistory.add(openedImage);
			view.setMMTImage(currentImage);
			setTitle(TITLE_TEXT + "  - " + currentImage.getName());
			enableFunctions();
			setModificationDataPanel(ImageModificationType.CONTRAST_STRETCHING);
			repaint();
		}

		return openedImage;
	}

	public void enableFunctions() {
		view.getSaveButton().setEnabled(true);
		view.getFooterPanel().getScaleSlider().setEnabled(true);
		view.getApplicationsPanel().getModificationTypeBox().setEnabled(true);
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

		view.getApplicationsPanel().getImageCombinationPanel().getOk().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Double factor = view.getApplicationsPanel().getImageCombinationPanel().getFactor();
				MMTImageCombiner combiner = new MMTImageCombiner(MainController.this, changeHistory.get(0), currentImage, factor);
				combiner.execute();
			}
		});
	}

	private void setModificationDataPanel(ImageModificationType action) {

		if (currentImage == null) {
			JOptionPane.showMessageDialog(view, "No image opened!", "Image has to be opened", JOptionPane.ERROR_MESSAGE);
			return;
		}

		view.getApplicationsPanel().removeAdditionalDataPanel();

		switch (action) {

		case CONTRAST_STRETCHING:
			modificationHelper.applyContrastStretching();
			break;

		case GAMMA_CORRECTION:
			modificationHelper.applyGammaCorrection();
			break;

		case HISTOGRAM_EQUALIZATION:
			modificationHelper.applyHistogramEqualization();
			break;

		case AVERAGING_FILTER:
			modificationHelper.applyAveragingFilter();
			break;

		case MEDIAN_FILTER:
			modificationHelper.applyMedianFilter();
			break;

		case LAPLACIAN_FILTER:
			modificationHelper.applyLaplacianFilter();
			break;

		case SOBEL_FILTER:
			modificationHelper.applySobelFilter();
			break;

		case HIGHBOOST_FILTER:
			modificationHelper.applyHighboostFilter();
			break;

		case GLOBAL_THRESHOLDING:
			modificationHelper.applyGlobalThresholdFilter();
			break;

		case ITERATIVE_THRESHOLDING:
			modificationHelper.applyIterativeThesholdingFilter();
			break;

		case OTSU_THRESHOLDING:
			modificationHelper.applyOtsuThresholding();
			break;

		default:
			displayNotImplementedWarning();
			break;
		}

	}

	@Override
	public void setCurrentImage(MMTImage newImage) {

		addToHistory();

		currentImage = newImage;
		view.setMMTImage(currentImage);
		enableUndoAndCompare(true);
	}

	private void addToHistory() {

		// to keep the max size of history and the original image
		while (changeHistory.size() >= CHANGE_HISTORY_SIZE) {
			changeHistory.remove(1);
			LOG.info("Change history too big - cutting of oldest change");
		}

		changeHistory.add(currentImage);
	}

	public MMTImage getCurrentImage() {
		return currentImage;
	}

	@Override
	public void setProgressBarVisible(boolean visible) {
		view.getFooterPanel().showProgressBar(visible);
	}

	@Override
	public void setProgressStatus(int progress) {
		view.getFooterPanel().setProgress(progress);
	}

	public void enableUndoAndCompare(boolean enabled) {
		view.getUndoButton().setEnabled(enabled);
		view.getCompareButton().setEnabled(enabled);
		view.getApplicationsPanel().getImageCombinationPanel().setEnabled(enabled);
	}

	private void displayNotImplementedWarning() {
		JOptionPane.showMessageDialog(view, "Sorry, \nit looks like this function has not been implemented yet!", "Feature not implemented yet",
				JOptionPane.ERROR_MESSAGE);
	}

	public MainView getView() {
		return view;
	}

	@Override
	public void unblockController() {
		glassPane.setVisible(false);
	}

	@Override
	public void blockController(Stoppable stoppable) {
		glassPane.setStoppable(stoppable);
		glassPane.setVisible(true);
	}

	public void compareImagesAverage() {
		MMTImage image1 = openImageFile(false, "Select FIRST image");
		MMTImage image2 = openImageFile(false, "Select SECOND image");

		new GlobalMeanImageComparator(new ComparationFinishedCallback() {
			@Override
			public void comparationFinsihed(Integer comparationResult) {
				JOptionPane.showMessageDialog(view, "Global Mean Comparation result: " + comparationResult, "Result", JOptionPane.INFORMATION_MESSAGE);
			}
		}).compareImages(image1, image2);

	}

	public void compareImagesVariance() {
		MMTImage img1 = openImageFile(false, "Select FIRST image");
		MMTImage img2 = openImageFile(false, "Select SECOND image");

		new VarianceImageComparator(new ComparationFinishedCallback() {
			@Override
			public void comparationFinsihed(Integer comparationResult) {
				JOptionPane.showMessageDialog(view, "Variance Comparation result: " + comparationResult, "Result", JOptionPane.INFORMATION_MESSAGE);
			}
		}).compareImages(img1, img2);
	}

	public void compareWithFolderImages(int type, Object additionalData) {
		String result = "no result!";

		MMTImage refImg = openImageFile(false, "Select REFERENCE image");

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select folder containing reference images");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fileChooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File directory = fileChooser.getSelectedFile();

			result = ImageWithImageSetComparator.compare(refImg, directory, type, additionalData);
		}
		JOptionPane.showMessageDialog(view, result, "Result", JOptionPane.INFORMATION_MESSAGE);
	}
}
