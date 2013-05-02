package at.ac.fhsalzburg.mmtlb.applications.filters;

public interface InterruptionCheckCallback {

	/**
	 * called by an external swingWoker to check if the caller has been canceled
	 */
	void checkIfInterrupted();

}
