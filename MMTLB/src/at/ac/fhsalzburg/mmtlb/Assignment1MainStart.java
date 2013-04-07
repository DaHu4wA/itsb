package at.ac.fhsalzburg.mmtlb;

import org.apache.log4j.Logger;

import at.ac.fhsalzburg.mmtlb.gui.MainController;

/**
 * Main method for Assignment 1
 * 
 * RUN THIS TO START THE APPLICATION
 * 
 * @author Stefan Huber
 */
public class Assignment1MainStart {
	private static final Logger LOG = Logger.getLogger(Assignment1MainStart.class.getSimpleName());

	public static void main(String[] args) {
		LOG.info("Application started");

		new MainController(); // everything is started from here

	}

}
