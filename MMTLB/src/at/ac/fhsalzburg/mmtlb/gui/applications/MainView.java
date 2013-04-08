package at.ac.fhsalzburg.mmtlb.gui.applications;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import at.ac.fhsalzburg.mmtlb.gui.accordion.AccordionContentPanel;
import at.ac.fhsalzburg.mmtlb.gui.accordion.AccordionPanel;
import at.ac.fhsalzburg.mmtlb.gui.accordion.ColoredPanel;
import at.ac.fhsalzburg.mmtlb.gui.imagepanel.TitledMMTImagePanel;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * View for MMT app
 * 
 * @author Stefan Huber
 */
public class MainView extends AccordionPanel {
	private static final long serialVersionUID = 5436775872668198881L;

	public static String FILE_TITLE = "File actions";
	public static String OPEN_IMAGE_TEXT = "Open image";
	public static String CONVERT_FOLDER_TEXT = "Convert *folder content* from .jpg to .png";
	public static String SAVE_FILE_TEXT = "Save current image";
	public static String REVERT_TEXT = "Undo changes";

	public ApplicationsPanel getApplicationsPanel() {
		return applicationsPanel;
	}

	private AccordionContentPanel fileActionsPanel;
	public AccordionContentPanel getFileActionsPanel() {
		return fileActionsPanel;
	}

	private JButton openFileButton;
	private JButton revertButton;
	private JButton convertWholeFolderToPNG;
	private JButton convertFileButton;

	private ApplicationsPanel applicationsPanel;

	private TitledMMTImagePanel mmtImagePanel;

	public MainView() {
		super(false);
		initialize();
	}

	private void initialize() {
		openFileButton = new JButton(OPEN_IMAGE_TEXT, new ImageIcon(MainView.class.getResource("add.png")));
		revertButton = new JButton(REVERT_TEXT, new ImageIcon(MainView.class.getResource("undo.png")));
		convertWholeFolderToPNG = new JButton(CONVERT_FOLDER_TEXT, new ImageIcon(MainView.class.getResource("copy.png")));
		convertFileButton = new JButton(SAVE_FILE_TEXT, new ImageIcon(MainView.class.getResource("save.png")));

		applicationsPanel = new ApplicationsPanel();

		convertFileButton.setEnabled(false);
		revertButton.setEnabled(false);

		// Panel to add the buttons
		JPanel fileActionPanel = new JPanel(new BorderLayout());
		JPanel fileActionLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		fileActionLeftPanel.setOpaque(false);
		JPanel fileActionRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 13, 13));
		fileActionRightPanel.setOpaque(false);

		fileActionLeftPanel.add(new JLabel(new ImageIcon(MainView.class.getResource("icon.png"))));
		fileActionLeftPanel.add(openFileButton);
		fileActionLeftPanel.add(convertFileButton);
		fileActionRightPanel.add(revertButton);
		
		fileActionPanel.add(fileActionLeftPanel, BorderLayout.WEST);
		fileActionPanel.add(fileActionRightPanel, BorderLayout.EAST);
		
		// Panel to add the buttons
		JPanel folderActionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		folderActionPanel.add(convertWholeFolderToPNG);

		fileActionsPanel = new AccordionContentPanel(FILE_TITLE, new ColoredPanel(fileActionPanel), new Color(0xE6E6FA));
		addFoldable(fileActionsPanel, false);
		addFoldable(new AccordionContentPanel("Image converters", new ColoredPanel(folderActionPanel), new Color(0xFFFACD)), true);
		addFoldable(new AccordionContentPanel("Image modifications", new ColoredPanel(applicationsPanel), new Color(0xFFE4B5)), true);

		mmtImagePanel = new TitledMMTImagePanel();
		addContent(mmtImagePanel);
		setFooter(createFooterPanel());
	}

	public JButton getRevertButton() {
		return revertButton;
	}

	private JPanel createFooterPanel() {
		JPanel footerPanel = new JPanel(new BorderLayout());
		footerPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.NORTH);
		footerPanel.add(new JLabel(" (C) 2013 Stefan Huber, ITSB-B2011-A"), BorderLayout.CENTER);
		return footerPanel;
	}

	/**
	 * @param image
	 *            the image that should be displayed
	 */
	public void setMMTImage(MMTImage image) {
		mmtImagePanel.setImage(image);
		fileActionsPanel.getSeparatorPanel().setTitle(FILE_TITLE+"    -    "+image.getName());
		repaint();
	}

	public JButton getOpenFileButton() {
		return openFileButton;
	}

	public JButton getConvertWholeFolderToPNG() {
		return convertWholeFolderToPNG;
	}

	public JButton getConvertFileButton() {
		return convertFileButton;
	}

}
