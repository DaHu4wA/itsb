package at.ac.fhsalzburg.mmtlb.applications.tools;

import at.ac.fhsalzburg.mmtlb.applications.AbstractImageModificationWorker;
import at.ac.fhsalzburg.mmtlb.interfaces.IFImageController;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Util to combine a original {@link MMTImage} with a modified image using a
 * given factor.
 * 
 * @author Stefan Huber
 */
public class MMTImageCombiner extends AbstractImageModificationWorker {
	
	private static final int MIN_GRAY = 0;
	private static final int MAX_GRAY = 255;

	private final double factor;
	private final MMTImage currentImage;

	public MMTImageCombiner() {
		super(null, null);
		factor = 0d;
		currentImage = null;
	}

	public MMTImageCombiner(IFImageController controller, MMTImage sourceImage, MMTImage currentImage, double factor) {
		super(controller, sourceImage);
		this.currentImage = currentImage;
		this.factor = factor;
	}

	/**
	 * Combine image with a given factor
	 * 
	 * @param base root image where other image will be added to
	 * @param other image to add
	 * @param factor the factor the other image will be added
	 * @returns a combined image
	 */
	public MMTImage combine(MMTImage base, MMTImage other, double factor) {
		MMTImage result = new MMTImage(base.getHeight(), base.getWidth());
		result.setName(base.getName());

		for (int i = 0; i < base.getImageData().length; i++) {

			// add other image to base image with given factor
			publishProgress(base, i);
			int grayVal = (int) (base.getImageData()[i] + (factor * other.getImageData()[i]));

			grayVal = doClipping(grayVal);

			result.getImageData()[i] = grayVal;
		}
		return result;
	}

	@Override
	protected MMTImage modifyImage(MMTImage sourceImage) throws InterruptedException {
		return combine(sourceImage, currentImage, factor);
	}

	/**
	 * Used for highboost filtering
	 */
	public MMTImage substract(MMTImage base, MMTImage other) {
		MMTImage result = new MMTImage(base.getHeight(), base.getWidth());
		result.setName(base.getName());

		for (int i = 0; i < base.getImageData().length; i++) {

			// substract other image from base image
			publishProgress(base, i);
			int grayVal = base.getImageData()[i] - other.getImageData()[i];

			grayVal = doClipping(grayVal);

			result.getImageData()[i] = grayVal;
		}
		return result;
	}

	/**
	 * Clip if under min gray val or over max
	 */
	private int doClipping(int grayVal) {
		grayVal = grayVal < MIN_GRAY ? MIN_GRAY : grayVal;
		grayVal = grayVal > MAX_GRAY ? MAX_GRAY : grayVal;
		return grayVal;
	}
}
