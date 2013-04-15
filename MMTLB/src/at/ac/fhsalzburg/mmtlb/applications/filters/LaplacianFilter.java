package at.ac.fhsalzburg.mmtlb.applications.filters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import at.ac.fhsalzburg.mmtlb.applications.AbstractImageModificationWorker;
import at.ac.fhsalzburg.mmtlb.applications.tools.SurroudingPixelHelper;
import at.ac.fhsalzburg.mmtlb.gui.IFImageController;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageReader;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageWriter;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Applies an laplacian filter to a {@link MMTImage}
 * 
 * @author Stefan Huber
 */
public class LaplacianFilter extends AbstractImageModificationWorker {

	private LaplacianFilterType filterType = null;

	public LaplacianFilter(IFImageController controller, MMTImage sourceImage) {
		super(controller, sourceImage);
	}

	@Override
	protected MMTImage modifyImage(MMTImage sourceImage) {
		return performLaplacian(sourceImage, filterType);
	}

	/**
	 * TODO comment
	 * 
	 * 4-neighourhood laplacian
	 */
	public MMTImage performLaplacian(MMTImage image, LaplacianFilterType filterType) {
		MMTImage result = new MMTImage(image.getHeight(), image.getWidth());
		result.setName(image.getName());

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				publishProgress(image, x + y * image.getWidth());
				result.setPixel2D(x, y, getLaplacianValueForPosition(image, filterType, x, y));
			}
		}
		return result;
	}

	private static int getLaplacianValueForPosition(MMTImage originalImage, LaplacianFilterType filterType, int xPos, int yPos) {

		int neighbourGraySum = 0;
		int factor = 0;

		if (!SurroudingPixelHelper.isOutOfSpace(originalImage, xPos + 1, yPos)) {
			factor++;
			neighbourGraySum += originalImage.getPixel2D(xPos + 1, yPos) * -1;
		}
		if (!SurroudingPixelHelper.isOutOfSpace(originalImage, xPos, yPos + 1)) {
			factor++;
			neighbourGraySum += originalImage.getPixel2D(xPos, yPos + 1) * -1;
		}
		if (!SurroudingPixelHelper.isOutOfSpace(originalImage, xPos - 1, yPos)) {
			factor++;
			neighbourGraySum += originalImage.getPixel2D(xPos - 1, yPos) * -1;
		}
		if (!SurroudingPixelHelper.isOutOfSpace(originalImage, xPos, yPos - 1)) {
			factor++;
			neighbourGraySum += originalImage.getPixel2D(xPos, yPos - 1) * -1;
		}

		if (LaplacianFilterType.EIGHT_NEIGHBOURHOOD == filterType) {

			if (!SurroudingPixelHelper.isOutOfSpace(originalImage, xPos + 1, yPos + 1)) {
				factor++;
				neighbourGraySum += originalImage.getPixel2D(xPos + 1, yPos + 1) * -1;
			}
			if (!SurroudingPixelHelper.isOutOfSpace(originalImage, xPos + 1, yPos - 1)) {
				factor++;
				neighbourGraySum += originalImage.getPixel2D(xPos + 1, yPos - 1) * -1;
			}
			if (!SurroudingPixelHelper.isOutOfSpace(originalImage, xPos - 1, yPos + 1)) {
				factor++;
				neighbourGraySum += originalImage.getPixel2D(xPos - 1, yPos + 1) * -1;
			}
			if (!SurroudingPixelHelper.isOutOfSpace(originalImage, xPos - 1, yPos - 1)) {
				factor++;
				neighbourGraySum += originalImage.getPixel2D(xPos - 1, yPos - 1) * -1;
			}

		}

		int result = originalImage.getPixel2D(xPos, yPos) * factor;
		result = result + neighbourGraySum;

		result = result < 0 ? 0 : result;
		result = result > 255 ? 255 : result;

		return result;
	}

	public static void main(String[] args) throws IOException {

		System.out.println("Laplacian filter, text version");
		System.out.println("Enter the full path to a picture: ");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String path = br.readLine();

		MMTImage image = FileImageReader.read(path);

		MMTImage enhanced = new LaplacianFilter(null, null).performLaplacian(image, LaplacianFilterType.FOUR_NEIGHBOURHOOD);

		int splitIndex = path.lastIndexOf('.');
		String newPath = path.substring(0, splitIndex) + "_LAPL4F" + path.substring(splitIndex, path.length());
		FileImageWriter.write(enhanced, newPath);
		System.out.println("Laplacian image saved as: \n" + newPath);
	}

	public void setFilterType(LaplacianFilterType filterType) {
		this.filterType = filterType;
	}

}
