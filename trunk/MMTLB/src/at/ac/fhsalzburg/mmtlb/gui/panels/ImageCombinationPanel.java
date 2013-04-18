package at.ac.fhsalzburg.mmtlb.gui.panels;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 * Panel for selecting image combination factor
 * 
 * @author Stefan Huber
 */
public class ImageCombinationPanel extends JPanel {

	private static final long serialVersionUID = -2466829498945619632L;
	private JComboBox<Double> values;
	private JButton ok;

	public ImageCombinationPanel() {
		setOpaque(false);
		setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		ok = new JButton("Combine", new ImageIcon(ImageCombinationPanel.class.getResource("combine.png")));

		Double[] items = { 0.1d, 0.2d, 0.3d, 0.4d, 0.5d, 0.6d, 0.7d, 0.8d, 0.9d, 1d, 1.1d, 1.2d, 1.3d, 1.4d, 1.5d };
		values = new JComboBox<Double>(items);
		values.setSelectedItem(0.1d);
		add(values);
		add(ok);
		setEnabled(false);
	}

	public JButton getOk() {
		return ok;
	}

	public Double getFactor() {
		return (Double) values.getSelectedItem();
	}

	@Override
	public void setEnabled(boolean enabled) {
		values.setEnabled(enabled);
		ok.setEnabled(enabled);
	}

}
