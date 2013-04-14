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
public class RasterSizePanel extends JPanel {
	private static final long serialVersionUID = 8019287358335341062L;

	private JComboBox<Integer> values;
	private JButton go;

	public RasterSizePanel(Integer[] items, Integer defaulValue) {
		setOpaque(false);
		setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		go = new JButton("Go!", new ImageIcon(RasterSizePanel.class.getResource("go.png")));

		values = new JComboBox<Integer>(items);
		values.setSelectedItem(defaulValue);
		add(new JLabel("Raster: "));
		add(values);
		add(go);
	}

	public JButton getGo() {
		return go;
	}

	public Integer getValue() {
		return (Integer) values.getSelectedItem();
	}

}
