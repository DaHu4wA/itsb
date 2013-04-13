package at.ac.fhsalzburg.mmtlb.gui.imagepanel;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class AdditionalIntDataPanel extends JPanel {
	private static final long serialVersionUID = 8019287358335341062L;

	private JComboBox<Integer> values;
	private JButton go;

	public AdditionalIntDataPanel(Integer[] items, Integer defaulValue) {
		setOpaque(false);
		go = new JButton("Go!", new ImageIcon(AdditionalIntDataPanel.class.getResource("go.png")));

		values = new JComboBox<Integer>(items);
		values.setSelectedItem(defaulValue);
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
