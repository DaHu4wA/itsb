package at.ac.fhsalzburg.mmtlb.gui.panels;

import java.awt.Color;
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
	// private Image paintedImage;
	private double scaleFactor = 1d;
	private int width = 0;
	private int height = 0;

	public MMTImagePanel() {
		image = null;
		// paintedImage = null;
		setBackground(Color.white);
	}

	public MMTImagePanel(MMTImage mmtImage) {
		setImage(mmtImage);
		setBackground(Color.white);
	}

	public void setImage(MMTImage mmtImage) {
		image = mmtImage.toBufferedImage();

		width = mmtImage.getWidth();
		height = mmtImage.getHeight();

		setScale(scaleFactor);
	}

	public void setScale(final double factor) {

		scaleFactor = factor;
		int tempWidth = (int) (width * scaleFactor);
		int tempHeight = (int) (height * scaleFactor);

		setPreferredSize(new Dimension(tempWidth, tempHeight));
		invalidate();
		repaint();
	}

	public Image getImage() {
		return image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int w = 0, h = 0;
		if (image != null) {
			w = (int) (scaleFactor * image.getWidth(null));
			h = (int) (scaleFactor * image.getHeight(null));
		}
		g.drawImage(image, 5, 5, w + 5, h + 5, Color.white, null);
	}

}
