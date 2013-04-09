package at.ac.fhsalzburg.mmtlb.gui.applications;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

/**
 * Panel for footer informations
 * 
 * @author Stefan Huber
 */
public class FooterPanel extends JPanel {
	private static final long serialVersionUID = 7788538411831753473L;
	private static final String TEXT = " (C) 2013 Stefan Huber, ITSB-B2011-A";

	private JProgressBar progressBar;

	public FooterPanel() {
		setLayout(new BorderLayout());
		add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.NORTH);

		add(new JLabel(TEXT), BorderLayout.WEST);

		initProgressBar();
		progressBar.setPreferredSize(new Dimension(180, 15));
		add(progressBar, BorderLayout.EAST);
	}

	private void initProgressBar() {
		progressBar = new JProgressBar(0, 100);
//		progressBar.setVisible(false);
	}

	public void setProgress(int progress) {
		if (progress < 0 || progress > 100) {
			progressBar.setValue(0);
			return;
		}
		progressBar.setValue(progress);
		repaint();
	}

	public void showProgressBar(boolean show) {
		//progressBar.setVisible(show);
		progressBar.setStringPainted(show);
		progressBar.setValue(0);
		repaint();
	}

}
