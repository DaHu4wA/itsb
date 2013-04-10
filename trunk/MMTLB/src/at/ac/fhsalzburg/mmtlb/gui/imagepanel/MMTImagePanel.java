package at.ac.fhsalzburg.mmtlb.gui.imagepanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
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

    private Image image;
    private float scaleFactor = 1l;

    public MMTImagePanel() {
        image = null;
    }

    public MMTImagePanel(MMTImage mmtImage) {
        setImage(mmtImage);
    }

    public void setImage(MMTImage mmtImage) {
        BufferedImage image = mmtImage.toBufferedImage();

        int width = Math.round((((float) mmtImage.getWidth()) * scaleFactor));
        int height = Math.round((((float) mmtImage.getHeight()) * scaleFactor));

        // int width = (int) getSize().getWidth();
        // int height = (int) getSize().getHeight();

        this.image = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);

        setPreferredSize(new Dimension(width, height));
    }

    public void setScaleFactor(float factor) {
        this.scaleFactor = factor;
    }

    public Image getImage() {
        return image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

}
