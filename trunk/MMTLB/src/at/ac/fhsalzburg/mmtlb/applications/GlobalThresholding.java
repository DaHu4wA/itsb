package at.ac.fhsalzburg.mmtlb.applications;

import at.ac.fhsalzburg.mmtlb.gui.IFImageController;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Applies global thresholding to a image with given threshold
 * 
 * @author Stefan Huber
 */
public class GlobalThresholding extends AbstractImageModificationWorker {
	private static final int MIN_GRAY = 0;
	private static final int MAX_GRAY = 255;

	private int threshold = 0;

	public GlobalThresholding() {
		super(null, null);
	}

	public GlobalThresholding(IFImageController controller, MMTImage sourceImage, int threshold) {
		super(controller, sourceImage);
		this.threshold = threshold;
	}

	public MMTImage performThresholding(MMTImage sourceImage, int threshold) {
		MMTImage result = new MMTImage(sourceImage.getHeight(), sourceImage.getWidth());

		for (int i = 0; i < sourceImage.getImageData().length; i++) {
			publishProgress(sourceImage, i);

			int value = sourceImage.getImageData()[i];
			result.getImageData()[i] = value > threshold ? MAX_GRAY : MIN_GRAY;
		}

		return result;
	}

	@Override
	protected MMTImage modifyImage(MMTImage sourceImage) {
		return performThresholding(sourceImage, threshold);
	}

}
