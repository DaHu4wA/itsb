package at.ac.fhsalzburg.mmtlb.gui.imagepanel;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class NoAdditionalDataPanel extends JPanel {
	private static final long serialVersionUID = 8019287308335341062L;

	private JButton go;

	public NoAdditionalDataPanel() {
		setOpaque(false);
		go = new JButton("Go!", new ImageIcon(NoAdditionalDataPanel.class.getResource("go.png")));
		add(go);
	}

	public JButton getGo() {
		return go;
	}
}
