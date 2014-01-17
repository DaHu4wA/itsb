package at.ac.fhsalzburg.mmtlb.applications.comparations;

import at.ac.fhsalzburg.mmtlb.applications.tools.HistogramTools;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

public class HistogramImageComparator extends AbstractImageComparator {

	private final int r;

	public HistogramImageComparator(ComparationFinishedCallback finishedCallback, int r) {
		super(finishedCallback);
		this.r = r;
	}

	@Override
	public void compare(MMTImage image1, MMTImage image2, ComparationFinishedCallback finishedCallback) {

		int[] hist1 = HistogramTools.getHistogram(image1);
		int[] hist2 = HistogramTools.getHistogram(image2);

		int length = hist1.length;

		Double result = 0d;
		for (int i = 0; i < length; i++) {
			result += Math.pow(Math.abs(hist1[i] - hist2[i]), r);
		}

		result = Math.pow(result, 1 / r);

		finishedCallback.comparationFinsihed(result.intValue());

		LOG.debug("Compare Result of " + image1.getName() + " and " + image2.getName() + ": \t" + result.intValue());
	}

}
