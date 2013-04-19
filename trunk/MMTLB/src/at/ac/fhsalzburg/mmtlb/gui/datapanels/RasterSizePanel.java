package at.ac.fhsalzburg.mmtlb.gui.datapanels;

import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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

		JLabel label = new JLabel("Raster: ");
		final JLabel label2 = new JLabel("(" + defaulValue + "x" + defaulValue + ")");
		JPanel rasterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		rasterPanel.setOpaque(false);
		rasterPanel.add(label);
		rasterPanel.add(values);

		values.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getItem() != null && e.getItem() instanceof Integer)
					label2.setText("(" + e.getItem() + "x" + e.getItem() + ")");
			}
		});

		add(rasterPanel);
		add(label2);
		add(go);
	}

	public JButton getGo() {
		return go;
	}

	public Integer getValue() {
		return (Integer) values.getSelectedItem();
	}

}
