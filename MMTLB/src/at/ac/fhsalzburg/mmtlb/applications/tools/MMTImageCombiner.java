package at.ac.fhsalzburg.mmtlb.applications.tools;

import at.ac.fhsalzburg.mmtlb.applications.AbstractImageModificationWorker;
import at.ac.fhsalzburg.mmtlb.gui.IFImageController;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Util to combine a original {@link MMTImage} with a modified image
 * 
 * @author Stefan Huber
 */
public class MMTImageCombiner extends AbstractImageModificationWorker {

	private final double factor;
	private final MMTImage currentImage;

	public MMTImageCombiner(IFImageController controller, MMTImage sourceImage, MMTImage currentImage, double factor) {
		super(controller, sourceImage);
		this.currentImage = currentImage;
		this.factor = factor;
	}

	/**
	 * Combine image with a given factor
	 * 
	 * @param base
	 *            root image where other image will be added to
	 * @param other
	 *            image to add
	 * @param factor
	 *            the factor the other image will be added
	 * @returns a combined image
	 */
	public MMTImage combine(MMTImage base, MMTImage other, double factor) {
		MMTImage result = new MMTImage(base.getHeight(), base.getWidth());
		result.setName(base.getName());

		for (int i = 0; i < base.getImageData().length; i++) {

			// add other image to base image
			publishProgress(base, i);
			int grayVal = (int) ((double) base.getImageData()[i] + ((double) factor * (double) other.getImageData()[i]));

			// clipping
			grayVal = grayVal < 0 ? 0 : grayVal;
			grayVal = grayVal > 255 ? 255 : grayVal;

			result.getImageData()[i] = grayVal;
		}
		return result;
	}

	@Override
	protected MMTImage modifyImage(MMTImage sourceImage) {
		return combine(sourceImage, currentImage, factor);
	}

}
