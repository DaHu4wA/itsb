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

	double k = 1.0; // default is unsharp mask
	int rasterSize = 3; // default raster size

	public HighboostFilter() {
		super(null, null);
	}

	public HighboostFilter(IFImageController controller, MMTImage sourceImage, int rasterSize, double factor) {
		super(controller, sourceImage);
		this.k = factor;
		this.rasterSize = rasterSize;
	}

	@Override
	protected MMTImage modifyImage(MMTImage sourceImage) {
		return perfomHighBoost(sourceImage, rasterSize, k);
	}

	public MMTImage perfomHighBoost(MMTImage sourceImage, int rasterSize, double factor) {
		MMTImage result = new MMTImage(sourceImage.getHeight(), sourceImage.getWidth());
		result.setName(sourceImage.getName());
		publish(25);

		// 1. Blur image
		result = new MedianFilter().performMedianFilter(sourceImage, rasterSize);
		publish(50);

		// 2. Substract blurred from original to create mask
		result = new MMTImageCombiner().substract(sourceImage, result);
		publish(75);

		// 3. add unsharpened mask to original with factor
		result = new MMTImageCombiner().combine(sourceImage, result, factor);
		publish(100);

		return result;
	}

}
