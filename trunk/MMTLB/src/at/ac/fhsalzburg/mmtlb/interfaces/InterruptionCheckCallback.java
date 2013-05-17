package at.ac.fhsalzburg.mmtlb.interfaces;

/**
 * This is used to be able to stop the worker when [ESC] is pressed in the GUI
 * 
 * @author Stefan Huber
 */
public interface InterruptionCheckCallback {

	/**
	 * called by an external swingWoker to check if the caller has been canceled
	 */
	void checkIfStopped() throws InterruptedException;

}
