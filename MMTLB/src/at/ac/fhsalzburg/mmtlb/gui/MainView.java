package at.ac.fhsalzburg.mmtlb.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import at.ac.fhsalzburg.mmtlb.gui.panels.ApplicationsPanel;
import at.ac.fhsalzburg.mmtlb.gui.panels.FooterPanel;
import at.ac.fhsalzburg.mmtlb.gui.panels.MMTImagePanel;
import at.ac.fhsalzburg.mmtlb.gui.panels.accordion.AccordionContentPanel;
import at.ac.fhsalzburg.mmtlb.gui.panels.accordion.AccordionPanel;
import at.ac.fhsalzburg.mmtlb.gui.panels.accordion.ColoredPanel;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * View for MMT appplication
 * 
 * @author Stefan Huber
 */
public class MainView extends AccordionPanel {
	private static final long serialVersionUID = 5436775872668198881L;

	public static Color fileActionsColor = new Color(0xE9F9FF);
	public static Color imageConverterColor = new Color(0xEFEFEF);
	public static Color imageModColor = new Color(0xFFFFC4);

	public static String FILE_TITLE = "File Actions";

	public static String OPEN_IMAGE_TEXT = "Open Image";
	public static String OPEN_IMAGE_TEXT_TOOLTIP = "Open a image file";

	public static String CONVERT_FOLDER_TEXT = "Convert *Folder Content* From .jpg To .png";

	public static String SAVE_FILE_TEXT = "Save Current Image";
	public static String SAVE_FILE_TEXT_TOOLTOP = "Save image currently shown";

	public static String COMPARE_TEXT = "Compare";
	public static String COMPARE_TEXT_TOOLTIP = "Compare current image with the original (using scale from current image)";

	public static String REVERT_TEXT = "Undo";
	public static String REVERT_TEXT_TOOLTIP = "Undo last change";

	public ApplicationsPanel getApplicationsPanel() {
		return applicationsPanel;
	}

	private AccordionContentPanel fileActionsPanel;

	public AccordionContentPanel getFileActionsPanel() {
		return fileActionsPanel;
	}

	private JButton openFileButton;
	private JButton revertButton;
	private JButton compareButton;
	private JButton convertWholeFolderToPNG;
	private JButton saveButton;

	private JButton compareGlobalMean;
	private JButton compareVariance;
	private JButton compareGlobalMeanFolder;
	private JButton compareVarianceFolder;
	private JButton compareHistogramFolder;
	private JButton compareShapeFolder;

	private ApplicationsPanel applicationsPanel;
	private FooterPanel footerPanel;

	private MMTImagePanel mmtImagePanel;

	public MainView() {
		super(false);
		initialize();
	}

	private void initialize() {
		openFileButton = new JButton(OPEN_IMAGE_TEXT, new ImageIcon(MainView.class.getResource("add.png")));
		openFileButton.setToolTipText(OPEN_IMAGE_TEXT_TOOLTIP);

		compareButton = new JButton(COMPARE_TEXT, new ImageIcon(MainView.class.getResource("compare.png")));
		compareButton.setToolTipText(COMPARE_TEXT_TOOLTIP);

		revertButton = new JButton(REVERT_TEXT, new ImageIcon(MainView.class.getResource("undo.png")));
		revertButton.setToolTipText(REVERT_TEXT_TOOLTIP);

		convertWholeFolderToPNG = new JButton(CONVERT_FOLDER_TEXT, new ImageIcon(MainView.class.getResource("copy.png")));
		compareGlobalMean = new JButton("Pair Compare Average (2 files)");
		compareVariance = new JButton("Pair Compare Variance (2 files)");
		compareGlobalMeanFolder = new JButton("Folder Compare Average (file + path)");
		compareVarianceFolder = new JButton("Folder Compare Variance (file + path)");
		compareHistogramFolder = new JButton("Folder Compare Histogram (file + path)");
		compareShapeFolder = new JButton("Folder Compare Shape (file + path)");

		saveButton = new JButton(SAVE_FILE_TEXT, new ImageIcon(MainView.class.getResource("save.png")));
		saveButton.setToolTipText(SAVE_FILE_TEXT_TOOLTOP);

		applicationsPanel = new ApplicationsPanel();
		footerPanel = new FooterPanel();

		saveButton.setEnabled(false);
		revertButton.setEnabled(false);
		compareButton.setEnabled(false);

		// Panel to add the buttons
		JPanel fileActionPanel = new JPanel(new BorderLayout());
		JPanel fileActionLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		fileActionLeftPanel.setOpaque(false);
		JPanel fileActionRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 13, 13));
		fileActionRightPanel.setOpaque(false);

		fileActionLeftPanel.add(new JLabel(new ImageIcon(MainView.class.getResource("icon.png"))));
		fileActionLeftPanel.add(openFileButton);
		fileActionLeftPanel.add(saveButton);
		fileActionRightPanel.add(compareButton);
		fileActionRightPanel.add(revertButton);

		fileActionPanel.add(fileActionLeftPanel, BorderLayout.WEST);
		fileActionPanel.add(fileActionRightPanel, BorderLayout.EAST);

		// Panel to add the buttons
		JPanel folderActionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		folderActionPanel.add(convertWholeFolderToPNG);
		folderActionPanel.add(compareGlobalMean);
		folderActionPanel.add(compareVariance);
		folderActionPanel.add(compareGlobalMeanFolder);
		folderActionPanel.add(compareVarianceFolder);
		folderActionPanel.add(compareHistogramFolder);
		folderActionPanel.add(compareShapeFolder);

		fileActionsPanel = new AccordionContentPanel(FILE_TITLE, ColoredPanel.createRaised(fileActionsColor, fileActionPanel), fileActionsColor);
		addFoldable(fileActionsPanel, false);
		addFoldable(new AccordionContentPanel("Image Converters and misc. Tools", ColoredPanel.createRaised(imageConverterColor, folderActionPanel),
				imageConverterColor), true);
		addFoldable(new AccordionContentPanel("Image modifications", ColoredPanel.createRaised(imageModColor, applicationsPanel), imageModColor), true);

		mmtImagePanel = new MMTImagePanel();
		addContent(new JScrollPane(mmtImagePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		setFooter(footerPanel);
	}

	public FooterPanel getFooterPanel() {
		return footerPanel;
	}

	public JButton getUndoButton() {
		return revertButton;
	}

	/**
	 * @param image
	 *            the image that should be displayed
	 */
	public void setMMTImage(MMTImage image) {
		mmtImagePanel.setImage(image);
		fileActionsPanel.getSeparatorPanel().setTitle(FILE_TITLE + "    -    " + image.getName() + "      " + image.getWidth() + "x" + image.getHeight());
		repaint();
	}

	public JButton getOpenFileButton() {
		return openFileButton;
	}

	public JButton getConvertWholeFolderToPNG() {
		return convertWholeFolderToPNG;
	}

	public JButton getSaveButton() {
		return saveButton;
	}

	public MMTImagePanel getMmtImagePanel() {
		return mmtImagePanel;
	}

	public JButton getCompareButton() {
		return compareButton;
	}

	public JButton getCompareGlobalMean() {
		return compareGlobalMean;
	}

	public JButton getCompareVariance() {
		return compareVariance;
	}

	public JButton getCompareGlobalMeanFolder() {
		return compareGlobalMeanFolder;
	}

	public JButton getCompareVarianceFolder() {
		return compareVarianceFolder;
	}

	public JButton getCompareHistogramFolder() {
		return compareHistogramFolder;
	}

	public JButton getCompareShapeFolder() {
		return compareShapeFolder;
	}

}
