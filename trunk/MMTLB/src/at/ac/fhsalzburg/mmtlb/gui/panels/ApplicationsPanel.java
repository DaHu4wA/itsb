package at.ac.fhsalzburg.mmtlb.gui.panels;

import java.awt.BorderLayout;
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
	private JPanel additionalDataPanel = null;
	JPanel modificationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
	
	private ImageCombinationPanel imageCombinationPanel;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ApplicationsPanel() {

		modificationTypeBox = new JComboBox(ImageModificationType.values());
		imageCombinationPanel = new ImageCombinationPanel();

		setLayout(new BorderLayout(10,10));
		modificationTypeBox.setEnabled(false);

		
		modificationPanel.add(modificationTypeBox);
		add(modificationPanel, BorderLayout.WEST);
		add(imageCombinationPanel, BorderLayout.EAST);
		setEnabled(false);
	}

	public void setAdditionalDataPanel(JPanel p) {

		if (additionalDataPanel != null) {
			modificationPanel.remove(additionalDataPanel);
		}

		this.additionalDataPanel = p;
		modificationPanel.add(additionalDataPanel, BorderLayout.CENTER);
		revalidate();
	}

	public void removeAdditionalDataPanel() {
		if (additionalDataPanel == null) {
			return;
		}
		modificationPanel.remove(additionalDataPanel);
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

	public ImageCombinationPanel getImageCombinationPanel() {
		return imageCombinationPanel;
	}
}
