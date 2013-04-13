package at.ac.fhsalzburg.mmtlb.gui.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Panel including the name of the image
 * 
 * @author Stefan Huber
 */
public class TitledMMTImagePanel extends JPanel {
	private static final long serialVersionUID = -6951145767984299645L;

	private MMTImagePanel mmtImagePanel = new MMTImagePanel();

	public TitledMMTImagePanel() {
		setLayout(new BorderLayout());
		add(mmtImagePanel, BorderLayout.CENTER);
	}

	public void setImage(MMTImage mmtImage) {
		mmtImagePanel.setImage(mmtImage);
		invalidate();
		repaint();
	}
	
	public void setScale(double scale){
		mmtImagePanel.setScaleFactor(scale);
		invalidate();
		repaint();
	}

}
