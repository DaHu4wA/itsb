package at.ac.fhsalzburg.mmtlb.gui.datapanels;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 * Panel with "go" button and integer selection combobox
 * 
 * @author Stefan Huber
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AdditionalComboBoxDataPanel extends JPanel {
	private static final long serialVersionUID = 8019287358335341062L;

	private JComboBox values;
	private JButton go;

	public AdditionalComboBoxDataPanel(Object[] items, Object defaulValue) {
		setOpaque(false);
		setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		go = new JButton("Go!", new ImageIcon(AdditionalComboBoxDataPanel.class.getResource("go.png")));

		values = new JComboBox(items);
		values.setSelectedItem(defaulValue);
		add(values);
		add(go);
	}

	public JButton getGo() {
		return go;
	}

	public Object getValue() {
		return values.getSelectedItem();
	}

}
