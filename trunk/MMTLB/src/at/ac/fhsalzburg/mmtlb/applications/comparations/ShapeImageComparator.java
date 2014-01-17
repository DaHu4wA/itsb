package at.ac.fhsalzburg.mmtlb.applications.comparations;

import at.ac.fhsalzburg.mmtlb.applications.filters.SobelFilter;
import at.ac.fhsalzburg.mmtlb.applications.tools.HistogramTools;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

public class ShapeImageComparator extends AbstractImageComparator {

	private final int r;

	public ShapeImageComparator(ComparationFinishedCallback finishedCallback, int r) {
		super(finishedCallback);
		this.r = r;
	}

	@Override
	public void compare(MMTImage image1, MMTImage image2, ComparationFinishedCallback finishedCallback) {
		MMTImage angleImage1 = null;
		MMTImage angleImage2 = null;
		try {
			angleImage1 = new SobelFilter().performAngleSobel(image1);
			angleImage2 = new SobelFilter().performAngleSobel(image2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int[] hist1 = HistogramTools.getHistogram(angleImage1);
		int[] hist2 = HistogramTools.getHistogram(angleImage2);

		int length = 8;

		Double result = 0d;
		for (int i = 0; i < length; i++) {
			result += Math.pow(Math.abs(hist1[i] - hist2[i]), r);
		}

		result = Math.pow(result, 1 / r);

		finishedCallback.comparationFinsihed(result.intValue());

		LOG.debug("Compare Result of " + image1.getName() + " and " + image2.getName() + ": \t" + result.intValue());
	}

}
