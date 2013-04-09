package at.ac.fhsalzburg.mmtlb.gui.imagepanel;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class AdditionalDataPanel extends JPanel {
	private static final long serialVersionUID = 8019287358335341062L;

	JSlider slider;
	JLabel currVal;
	JButton go;

	public AdditionalDataPanel(int min, int max, int defaultVal) {
		setOpaque(false);
		go = new JButton("Go!", new ImageIcon(AdditionalDataPanel.class.getResource("go.png")));
		currVal = new JLabel("Gamma: 1.0");
		currVal.setPreferredSize(new Dimension(75, 15));

		slider = new JSlider(min, max);
		slider.setValue(defaultVal);
		slider.setOpaque(false);

		slider.setMajorTickSpacing(100);
		slider.setMinorTickSpacing(10);

		slider.setPaintTicks(true);

		slider.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				currVal.setText("Gamma: " + (double) slider.getValue() / 100);
			}
		});

		add(slider);
		add(currVal);
		add(go);
	}

	public JButton getGo() {
		return go;
	}

	public int getValue() {
		return slider.getValue();
	}
	
	public JSlider getSlider(){
		return slider;
	}

}
