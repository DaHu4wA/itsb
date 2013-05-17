package at.ac.fhsalzburg.mmtlb.applications.filters;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import at.ac.fhsalzburg.mmtlb.applications.AbstractImageModificationWorker;
import at.ac.fhsalzburg.mmtlb.applications.tools.SurroudingPixelHelper;
import at.ac.fhsalzburg.mmtlb.interfaces.IFImageController;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageReader;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageWriter;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Applies an laplacian filter to a {@link MMTImage}
 * 
 * @author Stefan Huber
 */
public class LaplacianFilter extends AbstractImageModificationWorker {

	private LaplacianFilterType filterType = LaplacianFilterType.FOUR_NEIGHBOURHOOD; // default

	public LaplacianFilter() {
		super(null, null);
	}

	public LaplacianFilter(IFImageController controller, MMTImage sourceImage, LaplacianFilterType filterType) {
		super(controller, sourceImage);
		this.filterType = filterType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected MMTImage modifyImage(MMTImage sourceImage) throws InterruptedException {
		return performLaplacian(sourceImage, filterType);
	}

	public MMTImage performLaplacian(MMTImage image, LaplacianFilterType filterType) throws InterruptedException {
		MMTImage result = new MMTImage(image.getHeight(), image.getWidth());
		result.setName(image.getName());

		// calculate the filter values for every pixel
		for (int y = 0; y < image.getHeight(); y++) {
			checkIfInterrupted();
			for (int x = 0; x < image.getWidth(); x++) {
				publishProgress(image, x + y * image.getWidth());
				result.setPixel2D(x, y, getLaplacianValueForPosition(image, filterType, x, y));
			}
		}
		return result;
	}

	/**
	 * Applies the laplacian filter for a given pixel
	 * 
	 * @param originalImage the original image
	 * @param filterType the filter type
	 * @param xPos the current x position to apply the filter to
	 * @param yPos the current y position to apply the filter to
	 * @return laplacian filtered value for the pixel
	 */
	private static int getLaplacianValueForPosition(MMTImage originalImage, LaplacianFilterType filterType, int xPos, int yPos) {

		int neighbourGraySum = 0;
		int factor = 0;

		// INFO: this could have also been programmed using an array as mask,
		// but I found this one easier to read and to debug

		// right position
		if (!SurroudingPixelHelper.isOutOfSpace(originalImage, xPos + 1, yPos)) {
			factor++;
			neighbourGraySum += originalImage.getPixel2D(xPos + 1, yPos) * -1;
		}
		// lower position
		if (!SurroudingPixelHelper.isOutOfSpace(originalImage, xPos, yPos + 1)) {
			factor++;
			neighbourGraySum += originalImage.getPixel2D(xPos, yPos + 1) * -1;
		}
		// left position
		if (!SurroudingPixelHelper.isOutOfSpace(originalImage, xPos - 1, yPos)) {
			factor++;
			neighbourGraySum += originalImage.getPixel2D(xPos - 1, yPos) * -1;
		}
		// upper position
		if (!SurroudingPixelHelper.isOutOfSpace(originalImage, xPos, yPos - 1)) {
			factor++;
			neighbourGraySum += originalImage.getPixel2D(xPos, yPos - 1) * -1;
		}

		if (LaplacianFilterType.EIGHT_NEIGHBOURHOOD == filterType) {
			// lower right
			if (!SurroudingPixelHelper.isOutOfSpace(originalImage, xPos + 1, yPos + 1)) {
				factor++;
				neighbourGraySum += originalImage.getPixel2D(xPos + 1, yPos + 1) * -1;
			}
			// upper right
			if (!SurroudingPixelHelper.isOutOfSpace(originalImage, xPos + 1, yPos - 1)) {
				factor++;
				neighbourGraySum += originalImage.getPixel2D(xPos + 1, yPos - 1) * -1;
			}
			// lower left
			if (!SurroudingPixelHelper.isOutOfSpace(originalImage, xPos - 1, yPos + 1)) {
				factor++;
				neighbourGraySum += originalImage.getPixel2D(xPos - 1, yPos + 1) * -1;
			}
			// upper left
			if (!SurroudingPixelHelper.isOutOfSpace(originalImage, xPos - 1, yPos - 1)) {
				factor++;
				neighbourGraySum += originalImage.getPixel2D(xPos - 1, yPos - 1) * -1;
			}

		}

		// sum up the neighbour pixels and current pixel with the factor
		int result = originalImage.getPixel2D(xPos, yPos) * factor;
		result = result + neighbourGraySum;

		result = result < 0 ? 0 : result;
		result = result > 255 ? 255 : result;

		return result;
	}

	public static void main(String[] args) throws Exception {

		System.out.println("Laplacian filter, text version");
		System.out.println("Enter the full path to a picture: ");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String path = br.readLine();

		MMTImage image = FileImageReader.read(path);

		System.out.println("Enter \"4\" or \"8\" to select filter type: ");
		String type = br.readLine();

		int t = new Integer(type);
		LaplacianFilterType fType = null;

		if (t == 4) {
			fType = LaplacianFilterType.FOUR_NEIGHBOURHOOD;
		}
		if (t == 8) {
			fType = LaplacianFilterType.EIGHT_NEIGHBOURHOOD;
		} else {
			System.err.println("Invalid argument! exiting...");
			return;
		}

		MMTImage enhanced = new LaplacianFilter().performLaplacian(image, fType);

		int splitIndex = path.lastIndexOf('.');
		String newPath = path.substring(0, splitIndex) + "_LAPL" + path.substring(splitIndex, path.length());
		FileImageWriter.write(enhanced, newPath);
		System.out.println("Laplacian image saved as: \n" + newPath);
	}

}
