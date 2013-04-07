package at.ac.fhsalzburg.mmtlb.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import at.ac.fhsalzburg.mmtlb.gui.accordion.AccordionContentPanel;
import at.ac.fhsalzburg.mmtlb.gui.accordion.AccordionPanel;
import at.ac.fhsalzburg.mmtlb.gui.accordion.ColoredPanel;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * View for MMT app
 * 
 * @author Stefan Huber
 */
public class MainView extends AccordionPanel {
	private static final long serialVersionUID = 5436775872668198881L;

	public static String OPEN_IMAGE_TEXT = "Open image";
	public static String CONVERT_FOLDER_TEXT = "Convert *folder content* from .jpg to .png";
	public static String SAVE_FILE_TEXT = "Save as .jpg";

	private JButton openFileButton;
	private JButton convertWholeFolderToPNG;
	private JButton convertFileButton;

	private TitledMMTImagePanel mmtImagePanel;

	public MainView() {
		super(false);
		initialize();
		setBackground(Color.red);
	}

	private void initialize() {
		openFileButton = new JButton(OPEN_IMAGE_TEXT);
		convertWholeFolderToPNG = new JButton(CONVERT_FOLDER_TEXT);
		convertFileButton = new JButton(SAVE_FILE_TEXT);

		convertFileButton.setEnabled(false);

		// Panel to add the buttons
		JPanel fileActionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		fileActionPanel.add(openFileButton);
		fileActionPanel.add(convertFileButton);

		addFoldable(new AccordionContentPanel(" File actions", new ColoredPanel(fileActionPanel), new Color(0xceff9e)), false);

		// Panel to add the buttons
		JPanel folderActionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		folderActionPanel.add(convertWholeFolderToPNG);

		addFoldable(new AccordionContentPanel(" Image converters", new ColoredPanel(folderActionPanel), new Color(0xfbf88e)), true);

		mmtImagePanel = new TitledMMTImagePanel();
		addContent(mmtImagePanel);
		setFooter(createFooterPanel());
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
