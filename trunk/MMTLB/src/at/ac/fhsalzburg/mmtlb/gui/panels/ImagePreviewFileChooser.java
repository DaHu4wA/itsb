package at.ac.fhsalzburg.mmtlb.gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 * basic idea comes from:
 * 
 * http://niravjavadeveloper.blogspot.co.at/2011/05/image-preview-in-
 * jfilechooser_19.html
 */
public class ImagePreviewFileChooser extends JPanel implements PropertyChangeListener {
	private static final long serialVersionUID = 9037688411878902475L;
	private static final Color backgroundColour = Color.white;
	private JFileChooser jfc;
	private Image img;

	public ImagePreviewFileChooser(JFileChooser jfc) {
		this.jfc = jfc;
		Dimension sz = new Dimension(250, 250);
		setPreferredSize(sz);
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		try {
			File file = jfc.getSelectedFile();
			updateImage(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void updateImage(File file) throws IOException {
		if (file == null) {
			return;
		}
		img = ImageIO.read(file);
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(backgroundColour);
		g.fillRect(0, 0, getWidth(), getHeight());

		if (img != null) {
			// calculate the scaling factor
			int w = img.getWidth(null);
			int h = img.getHeight(null);
			int side = Math.max(w, h);

			int ow = w;
			int oh = h;

			double scale = 250.0 / side;
			w = (int) (scale * w);
			h = (int) (scale * h);
			// draw the image
			g.drawImage(img, 0, 0, w, h, null);

			// draw the image dimensions
			String dim = ow + " x " + oh;
			g.setColor(Color.black);
			g.drawString(dim, 11, 16);
			g.setColor(Color.white);
			g.drawString(dim, 10, 15);

		} else {
			// print a message
			String msg = "No image selected";
			g.setColor(Color.black);
			g.drawString(msg, 11, 16);
		}
	}

}
