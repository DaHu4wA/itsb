package at.ac.fhsalzburg.mmtlb.gui.imagepanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Display the {@link MMTImage} in a panel
 * 
 * @author Stefan Huber
 */
public class MMTImagePanel extends JPanel {
	private static final long serialVersionUID = 8105730431947916489L;

	private BufferedImage image;
	
	public MMTImagePanel() {
		image = null;
	}

	public MMTImagePanel(MMTImage mmtImage) {
		setImage(mmtImage);
	}

	public void setImage(MMTImage mmtImage) {
		image = mmtImage.toBufferedImage();
		setPreferredSize(new Dimension(mmtImage.getWidth(), mmtImage.getHeight()));
	}
	
	public BufferedImage getImage(){
		return image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}

}