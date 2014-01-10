package at.ac.fhsalzburg.mmtlb.applications.comparations;

import at.ac.fhsalzburg.mmtlb.applications.tools.HistogramTools;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

public class GlobalMeanImageComparator extends AbstractImageComparator {

	public GlobalMeanImageComparator(ComparationFinishedCallback finishedCallback) {
		super(finishedCallback);
	}

	@Override
	public void compare(MMTImage image1, MMTImage image2, ComparationFinishedCallback finishedCallback) {

		int mean1 = HistogramTools.getGlobalMean(image1);
		int mean2 = HistogramTools.getGlobalMean(image2);

		Integer result = Math.abs(mean1 - mean2);

		finishedCallback.comparationFinsihed(result);
		
		LOG.debug("Compare Result of " + image1.getName() + " and " + image2.getName() + ": \t" + result);
	}

}
