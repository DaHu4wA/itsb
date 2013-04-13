package at.ac.fhsalzburg.mmtlb.gui.imagepanel;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * created by:
 * http://niravjavadeveloper.blogspot.co.at/2011/05/image-preview-in-
 * jfilechooser_19.html
 */
public class ImagePreviewFileChooser extends JPanel implements PropertyChangeListener {
	private static final long serialVersionUID = 9037688411878902475L;
	private static final Color backgroundColout = new Color(0xEEEEEE);
	private JFileChooser jfc;
	private Image img;

	public ImagePreviewFileChooser(JFileChooser jfc) {
		this.jfc = jfc;
		Dimension sz = new Dimension(200, 200);
		setPreferredSize(sz);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		try {
			File file = jfc.getSelectedFile();
			updateImage(file);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
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

	public void paintComponent(Graphics g) {
		// fill the background
		g.setColor(backgroundColout);
		g.fillRect(0, 0, getWidth(), getHeight());

		if (img != null) {
			// calculate the scaling factor
			int w = img.getWidth(null);
			int h = img.getHeight(null);
			int side = Math.max(w, h);

			int ow = w;
			int oh = h;

			double scale = 200.0 / (double) side;
			w = (int) (scale * (double) w);
			h = (int) (scale * (double) h);
			// draw the image
			g.drawImage(img, 0, 0, w, h, null);

			// draw the image dimensions
			String dim = ow + " x " + oh;
			g.setColor(Color.black);
			g.drawString(dim, 31, 196);
			g.setColor(Color.white);
			g.drawString(dim, 30, 195);

		} else {
			// print a message
			g.setColor(Color.black);
			g.drawString("", 30, 100);
		}
	}

}
