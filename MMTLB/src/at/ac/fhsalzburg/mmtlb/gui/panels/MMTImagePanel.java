package at.ac.fhsalzburg.mmtlb.gui.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Display the {@link MMTImage} in a panel
 * 
 * @author Stefan Huber
 */
public class MMTImagePanel extends JPanel {
	private static final long serialVersionUID = 8105730431947916489L;

	private Image image;
	private Image paintedImage;
	private double scaleFactor = 1d;
	private int width = 0;
	private int height = 0;

	public MMTImagePanel() {
		image = null;
		paintedImage = null;
	}

	public MMTImagePanel(MMTImage mmtImage) {
		setImage(mmtImage);
	}

	public void setImage(MMTImage mmtImage) {
		image = mmtImage.toBufferedImage();

		width = mmtImage.getWidth();
		height = mmtImage.getHeight();

		setScaleFactor(scaleFactor);
	}

	public void setScaleFactor(final double factor) {

		scaleFactor = factor;
		int tempWidth = (int) ((double) width * scaleFactor);
		int tempHeight = (int) ((double) height * scaleFactor);

		paintedImage = image.getScaledInstance(tempWidth, tempHeight, java.awt.Image.SCALE_SMOOTH);
		setPreferredSize(new Dimension(tempWidth, tempHeight));
	}

	public Image getImage() {
		return image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(paintedImage, 0, 0, null);
	}

}
