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
 * Applies a sobel filter to a {@link MMTImage}
 * 
 * @author Stefan Huber
 */
public class SobelFilter extends AbstractImageModificationWorker {

	public SobelFilter(IFImageController controller, MMTImage sourceImage) {
		super(controller, sourceImage);
	}

	@Override
	protected MMTImage modifyImage(MMTImage sourceImage) {
		return performSobel(sourceImage);
	}

	public MMTImage performSobel(MMTImage image) {
		MMTImage result = new MMTImage(image.getHeight(), image.getWidth());
		result.setName(image.getName());

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				publishProgress(image, x + y * image.getWidth());
				result.setPixel2D(x, y, getValue(image, x, y));
			}
		}
		return result;
	}

	private int getValue(MMTImage image, int x, int y) {
		double filtered = Math.sqrt(Math.pow(getHval(image, x, y), 2) + Math.pow(getVval(image, x, y), 2));

		int filter = filtered < 0 ? 0 : (int) filtered;
		filter = filtered > 255 ? 255 : (int) filtered;

		return filter;
	}

	private double getHval(MMTImage image, int x, int y) {
		double horizontalSum = 0;

		// upper left
		if (!SurroudingPixelHelper.isOutOfSpace(image, x - 1, y - 1)) {
			horizontalSum += image.getPixel2D(x - 1, y - 1) * -1;
		}
		// upper middle
		if (!SurroudingPixelHelper.isOutOfSpace(image, x, y - 1)) {
			horizontalSum += image.getPixel2D(x, y - 1) * -2;
		}
		// upper right
		if (!SurroudingPixelHelper.isOutOfSpace(image, x + 1, y - 1)) {
			horizontalSum += image.getPixel2D(x + 1, y - 1) * -1;
		}

		// lower left
		if (!SurroudingPixelHelper.isOutOfSpace(image, x - 1, y + 1)) {
			horizontalSum += image.getPixel2D(x - 1, y + 1) * 1;
		}
		// lower middle
		if (!SurroudingPixelHelper.isOutOfSpace(image, x, y + 1)) {
			horizontalSum += image.getPixel2D(x, y + 1) * 2;
		}
		// lower right
		if (!SurroudingPixelHelper.isOutOfSpace(image, x + 1, y + 1)) {
			horizontalSum += image.getPixel2D(x + 1, y + 1) * 1;
		}

		return horizontalSum;
	}

	private double getVval(MMTImage image, int x, int y) {

		double verticalSum = 0;

		// upper left
		if (!SurroudingPixelHelper.isOutOfSpace(image, x - 1, y - 1)) {
			verticalSum += image.getPixel2D(x - 1, y - 1) * -1;
		}
		// middle left
		if (!SurroudingPixelHelper.isOutOfSpace(image, x - 1, y)) {
			verticalSum += image.getPixel2D(x - 1, y) * -2;
		}
		// lower left
		if (!SurroudingPixelHelper.isOutOfSpace(image, x - 1, y + 1)) {
			verticalSum += image.getPixel2D(x - 1, y + 1) * -1;
		}

		// upper right
		if (!SurroudingPixelHelper.isOutOfSpace(image, x + 1, y - 1)) {
			verticalSum += image.getPixel2D(x + 1, y - 1) * 1;
		}
		// middle right
		if (!SurroudingPixelHelper.isOutOfSpace(image, x + 1, y)) {
			verticalSum += image.getPixel2D(x + 1, y) * 2;
		}
		// lower right
		if (!SurroudingPixelHelper.isOutOfSpace(image, x + 1, y + 1)) {
			verticalSum += image.getPixel2D(x + 1, y + 1) * 1;
		}
		return verticalSum;
	}

	public static void main(String[] args) throws IOException {

		System.out.println("Sobel filter, text version");
		System.out.println("Enter the full path to a picture: ");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String path = br.readLine();

		MMTImage image = FileImageReader.read(path);

		MMTImage enhanced = new SobelFilter(null, null).performSobel(image);

		int splitIndex = path.lastIndexOf('.');
		String newPath = path.substring(0, splitIndex) + "_SOBEL" + path.substring(splitIndex, path.length());
		FileImageWriter.write(enhanced, newPath);
		System.out.println("Sobel filtered image saved as: \n" + newPath);
	}

}
