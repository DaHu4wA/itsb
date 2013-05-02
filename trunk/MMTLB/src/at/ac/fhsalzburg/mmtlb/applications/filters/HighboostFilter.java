package at.ac.fhsalzburg.mmtlb.applications.filters;

import at.ac.fhsalzburg.mmtlb.applications.AbstractImageModificationWorker;
import at.ac.fhsalzburg.mmtlb.applications.tools.MMTImageCombiner;
import at.ac.fhsalzburg.mmtlb.gui.IFImageController;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Applies a highboost filter to a given {@link MMTImage}
 * 
 * @author Stefan Huber
 */
public class HighboostFilter extends AbstractImageModificationWorker {

	double k = 1.0; // default factor is a classic "unsharp mask"
	int rasterSize = 3; // default raster size

	public HighboostFilter() {
		super(null, null);
	}

	public HighboostFilter(IFImageController controller, MMTImage sourceImage, int rasterSize, double factor) {
		super(controller, sourceImage);
		this.k = factor;
		this.rasterSize = rasterSize;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected MMTImage modifyImage(MMTImage sourceImage) throws InterruptedException {
		publish(25);
		return perfomHighBoost(sourceImage, rasterSize, k);
	}

	/**
	 * Applies a highboost filter to a image
	 * 
	 * @param sourceImage the image to apply the filter to
	 * @param rasterSize the raster size
	 * @param factor the factor to add the filter response to the original image
	 * @return a ned filtered {@link MMTImage}
	 * @throws InterruptedException
	 */
	public MMTImage perfomHighBoost(MMTImage sourceImage, int rasterSize, double factor) throws InterruptedException {
		MMTImage result = new MMTImage(sourceImage.getHeight(), sourceImage.getWidth());
		result.setName(sourceImage.getName());

		// 1. Blur image, here a Median Filter is used
		result = new MedianFilter().performMedianFilter(sourceImage, new InterruptionCheckCallback() {

			@Override
			public void checkIfInterrupted() {
				checkIfInterrupted();
			}
		}, rasterSize);
		publish(50);
		checkIfInterrupted();
		// 2. Substract blurred response from original to create unsharp mask
		result = new MMTImageCombiner().substract(sourceImage, result);
		publish(75);
		checkIfInterrupted();

		// 3. add unsharp mask to original image with factor
		result = new MMTImageCombiner().combine(sourceImage, result, factor);
		publish(100);

		return result;
	}

}
