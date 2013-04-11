package at.ac.fhsalzburg.mmtlb.gui.applications;

import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import at.ac.fhsalzburg.mmtlb.applications.ImageModificationType;

/**
 * Panel for image applications that can directly be modified
 * 
 * @author Stefan Huber
 */
public class ApplicationsPanel extends JPanel {
	private static final long serialVersionUID = 3137071099064109809L;

	@SuppressWarnings("rawtypes")
	private JComboBox modificationTypeBox;
	// private JButton btnApply;
	private JPanel additionalDataPanel = null;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ApplicationsPanel() {

		modificationTypeBox = new JComboBox(ImageModificationType.values());
		// btnApply = new JButton(" Apply effect", new
		// ImageIcon(ApplicationsPanel.class.getResource("edit-picture.png")));

		setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

		add(modificationTypeBox);
		// add(btnApply);
		setEnabled(false);
	}

	public void setAdditionalDataPanel(JPanel p) {

		if (additionalDataPanel != null) {
			remove(additionalDataPanel);
		}

		this.additionalDataPanel = p;
		add(additionalDataPanel);
		revalidate();
	}

	public void removeAdditionalDataPanel() {
		if (additionalDataPanel == null) {
			return;
		}
		remove(additionalDataPanel);
		this.additionalDataPanel = null;
		repaint();
	}

	@SuppressWarnings("rawtypes")
	public JComboBox getModificationTypeBox() {
		return modificationTypeBox;
	}

	@SuppressWarnings("rawtypes")
	public void setModificationTypeBox(JComboBox modificationTypeBox) {
		this.modificationTypeBox = modificationTypeBox;
	}

	// public JButton getBtnApply() {
	// return btnApply;
	// }
	//
	// public void setBtnApply(JButton btnApply) {
	// this.btnApply = btnApply;
	// }
}
