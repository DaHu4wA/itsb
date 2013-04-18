package at.ac.fhsalzburg.mmtlb.gui.applications;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel with "go" button and integer selection combobox
 * 
 * @author Stefan Huber
 */
public class HighboostDataPanel extends JPanel {
	private static final long serialVersionUID = 8019287358335341062L;
	private static final Double[] factors = { 0.1d, 0.2d, 0.3d, 0.4d, 0.5d, 0.6d, 0.7d, 0.8d, 0.9d, 1d, 1.1d, 1.2d, 1.3d, 1.4d, 1.5d, 1.7d, 2.0d, 2.5d, 3.0d,
			5.0d };
	private static final Integer[] rasterSizes = { 3, 5, 7, 9, 11, 13, 15, 17, 19, 23, 27, 31, 33, 51 };

	private JComboBox<Double> factorBox;
	private JComboBox<Integer> rasterBox;
	private JButton go;

	public HighboostDataPanel() {
		setOpaque(false);
		setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		go = new JButton("Go!", new ImageIcon(HighboostDataPanel.class.getResource("go.png")));

		JPanel factorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		factorPanel.setOpaque(false);
		factorBox = new JComboBox<Double>(factors);
		factorBox.setSelectedItem(1.0d);
		factorPanel.add(new JLabel("Factor:"));
		factorPanel.add(factorBox);

		JPanel rasterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		rasterPanel.setOpaque(false);
		rasterBox = new JComboBox<Integer>(rasterSizes);
		rasterBox.setSelectedItem(3);
		rasterPanel.add(new JLabel("Raster:"));
		rasterPanel.add(rasterBox);

		add(rasterPanel);
		add(factorPanel);
		add(go);
	}

	public JButton getGo() {
		return go;
	}

	public double getFactor() {
		return (Double) factorBox.getSelectedItem();
	}

	public int getRaster() {
		return (Integer) rasterBox.getSelectedItem();
	}

}
