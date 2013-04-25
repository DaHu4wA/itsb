package at.ac.fhsalzburg.mmtlb;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import at.ac.fhsalzburg.mmtlb.gui.MainController;

/**
 * Main method for Assignments
 * 
 * RUN THIS TO START THE APPLICATION
 * 
 * @author Stefan Huber
 */
public class ApplicationMainStart {
	private static final Logger LOG = Logger.getLogger(ApplicationMainStart.class.getSimpleName());

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainController(); // everything is started from here
				LOG.info("Application started");
			}
		});

	}

}
