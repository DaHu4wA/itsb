package at.ac.fhsalzburg.mmtlb.applications.comparations;

import at.ac.fhsalzburg.mmtlb.applications.tools.HistogramTools;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

public class VarianceImageComparator extends AbstractImageComparator {

	public VarianceImageComparator(ComparationFinishedCallback finishedCallback) {
		super(finishedCallback);
	}

	@Override
	public void compare(MMTImage image1, MMTImage image2, ComparationFinishedCallback finishedCallback) {

		double mean1 = HistogramTools.getGlobalVariance(image1);
		double mean2 = HistogramTools.getGlobalVariance(image2);

		Double result = Math.abs(mean1 - mean2);

		finishedCallback.comparationFinsihed(result.intValue());

		LOG.debug("Compare Result of " + image1.getName() + " and " + image2.getName() + ": \t" + result.intValue());
	}

}
