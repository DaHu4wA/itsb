package at.ac.fhsalzburg.mmtlb.gui.applications;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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

	private JComboBox<ImageModificationType> modificationTypeBox;
	private JButton btnApply;

	public ApplicationsPanel() {

		modificationTypeBox = new JComboBox<ImageModificationType>(ImageModificationType.values());
		btnApply = new JButton(" Apply effect", new ImageIcon(ApplicationsPanel.class.getResource("edit-picture.png")));

		setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

		add(modificationTypeBox);
		add(btnApply);
		setEnabled(false);
	}

	public JComboBox<ImageModificationType> getModificationTypeBox() {
		return modificationTypeBox;
	}

	public void setModificationTypeBox(JComboBox<ImageModificationType> modificationTypeBox) {
		this.modificationTypeBox = modificationTypeBox;
	}

	public JButton getBtnApply() {
		return btnApply;
	}

	public void setBtnApply(JButton btnApply) {
		this.btnApply = btnApply;
	}
}
