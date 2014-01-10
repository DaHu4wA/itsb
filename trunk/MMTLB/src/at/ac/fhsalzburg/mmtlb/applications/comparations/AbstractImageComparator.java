package at.ac.fhsalzburg.mmtlb.applications.comparations;

import org.apache.log4j.Logger;

import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

public abstract class AbstractImageComparator {

	private final ComparationFinishedCallback finishedCallback;

	public AbstractImageComparator(ComparationFinishedCallback finishedCallback) {
		this.finishedCallback = finishedCallback;
	}

	protected static final Logger LOG = Logger.getLogger(AbstractImageComparator.class.getSimpleName());

	protected abstract void compare(MMTImage image1, MMTImage image2, ComparationFinishedCallback finishedCallback);

	public void compareImages(MMTImage image1, MMTImage image2) {
		compare(image1, image2, finishedCallback);
	}

}