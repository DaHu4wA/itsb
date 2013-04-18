package at.ac.fhsalzburg.mmtlb.gui.comparation;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import at.ac.fhsalzburg.mmtlb.gui.panels.MMTImagePanel;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Utility to compare modified with original image
 * 
 * @author Stefan Huber
 */
public class ImageComparator extends JDialog {
	private static final long serialVersionUID = 1L;

	private MMTImagePanel currentImagePanel;
	private MMTImagePanel originalImagePanel;
	private JSplitPane splitPane;

	public ImageComparator(JFrame owner, MMTImage currentImage, MMTImage originalImage, double scale) {
		super(owner);
		setTitle("ORIGINAL image  <-(" + originalImage.getName() + ")->  CURRENT image");

		currentImagePanel = new MMTImagePanel(currentImage);
		originalImagePanel = new MMTImagePanel(originalImage);

		currentImagePanel.setScale(scale);
		originalImagePanel.setScale(scale);
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(originalImagePanel), new JScrollPane(currentImagePanel));
		splitPane.setResizeWeight(.5d);
		splitPane.setDividerLocation(.5d);

		add(splitPane);

		pack();
		setLocationRelativeTo(owner);
	}

}
