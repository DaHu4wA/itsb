package at.ac.fhsalzburg.mmtlb.gui.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import at.ac.fhsalzburg.mmtlb.gui.panels.accordion.BorderPanel;

/**
 * Panel for footer informations
 * 
 * @author Stefan Huber
 */
public class FooterPanel extends JPanel {
	private static final long serialVersionUID = 7788538411831753473L;
	private static final String TEXT = " (C) 2013 Stefan Huber, ITSB-B2011-A";

	Timer resetProgressTimer = new Timer(2000, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			showProgressBar(false);
		}
	});

	private JProgressBar progressBar;
	private JSlider scaleSlider;
	private JLabel currVal;

	public FooterPanel() {
		setLayout(new BorderLayout());
		add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.NORTH);

		add(new JLabel(TEXT), BorderLayout.CENTER);

		initProgressBar();

		scaleSlider = new JSlider(10, 300);
		scaleSlider.setValue(100);
		scaleSlider.setOpaque(false);
		scaleSlider.setMajorTickSpacing(100);
		scaleSlider.setMinorTickSpacing(10);
		scaleSlider.setPaintTicks(true);
		scaleSlider.setPreferredSize(new Dimension(180, 15));
		scaleSlider.setEnabled(false);

		currVal = new JLabel("Scale: 1.0");
		currVal.setPreferredSize(new Dimension(75, 15));

		JPanel scalePanel = new JPanel(new FlowLayout());
		scalePanel.add(scaleSlider);
		scalePanel.add(currVal);
		add(scalePanel, BorderLayout.WEST);

		progressBar.setPreferredSize(new Dimension(170, 15));
		add(new BorderPanel(progressBar), BorderLayout.EAST);

		scaleSlider.addChangeListener(new javax.swing.event.ChangeListener() {
			@Override
			public void stateChanged(javax.swing.event.ChangeEvent e) {
				double scale = scaleSlider.getValue();
				currVal.setText("Scale: " + (scale / 100));
			}
		});
	}

	public JSlider getScaleSlider() {
		return scaleSlider;
	}

	private void initProgressBar() {
		progressBar = new JProgressBar(0, 100);
	}

	public void setProgress(int progress) {
		if (progress < 0 || progress > 100) {
			progressBar.setValue(0);
			return;
		}

		if (progress == 100 && !resetProgressTimer.isRunning()) {
			resetProgressTimer.setRepeats(false);
			resetProgressTimer.start();
		}

		progressBar.setValue(progress);
		repaint();
	}

	public void showProgressBar(boolean show) {
		// progressBar.setVisible(show);
		progressBar.setStringPainted(show);
		progressBar.setValue(0);
		repaint();
	}

}
