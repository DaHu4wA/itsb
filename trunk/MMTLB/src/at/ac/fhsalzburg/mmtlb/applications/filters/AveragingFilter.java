package at.ac.fhsalzburg.mmtlb.applications.filters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import at.ac.fhsalzburg.mmtlb.applications.AbstractImageModificationWorker;
import at.ac.fhsalzburg.mmtlb.applications.tools.SurroudingPixelHelper;
import at.ac.fhsalzburg.mmtlb.interfaces.IFImageController;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageReader;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageWriter;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Applies an averaging filter to a {@link MMTImage}
 * 
 * @author Stefan Huber
 */
public class AveragingFilter extends AbstractImageModificationWorker {

	private int raster = 3; // default size of raster

	public AveragingFilter() {
		super(null, null);
	}

	public AveragingFilter(IFImageController controller, MMTImage sourceImage, int rasterSize) {
		super(controller, sourceImage);
		this.raster = rasterSize;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws InterruptedException
	 */
	@Override
	protected MMTImage modifyImage(MMTImage sourceImage) throws InterruptedException {
		return performAveraging(sourceImage, raster);
	}

	/**
	 * Performs average filter
	 * 
	 * @param image the image to average
	 * @param rasterSize UNEVEN (3x3, 5x5 etc) value
	 * @return a new averaged image
	 * @throws InterruptedException
	 */
	public MMTImage performAveraging(MMTImage image, int rasterSize) throws InterruptedException {
		MMTImage result = new MMTImage(image.getHeight(), image.getWidth());
		result.setName(image.getName());

		for (int y = 0; y < image.getHeight(); y++) {
			checkIfInterrupted();
			for (int x = 0; x < image.getWidth(); x++) {
				publishProgress(image, x + y * image.getWidth());

				// get the averaged value for every position
				result.setPixel2D(x, y, getAverageValueForPosition(image, rasterSize, x, y));
			}
		}
		return result;
	}

	/**
	 * Returns the averaged value for this position
	 * 
	 * @param originalImage the unmodified image
	 * @param rasterSize has to be uneven!!
	 * @param xPos current x position starting from 0
	 * @param yPos current y position starting from 0
	 */
	public static int getAverageValueForPosition(MMTImage originalImage, int rasterSize, int xPos, int yPos) {

		int graySum = 0;
		int fieldSum = 0; // this is the divisor for the average value (not
							// always mask size because of edges)

		int xStartPos = xPos - (rasterSize / 2);
		int xEndPos = xStartPos + rasterSize;

		int yStartPos = yPos - (rasterSize / 2);
		int yEndPos = yStartPos + rasterSize;

		// iterate over the mask
		for (int y = yStartPos; y <= yEndPos; y++) {
			for (int x = xStartPos; x <= xEndPos; x++) {

				if (SurroudingPixelHelper.isOutOfSpace(originalImage, x, y)) {
					continue; // ignore fields out of space
				}
				fieldSum++;
				graySum += originalImage.getPixel2D(x, y);
			}
		}

		// return the averaged value
		return graySum / fieldSum;
	}

	public static void main(String[] args) throws IOException, NumberFormatException, InterruptedException {

		System.out.println("Averaging filter, text version");
		System.out.println("Enter the full path to a picture: ");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String path = br.readLine();

		System.out.println("Raster size: Please enter an UNEVEN number, at least 3: ");
		String rast = br.readLine();

		MMTImage image = FileImageReader.read(path);

		MMTImage enhanced = new AveragingFilter().performAveraging(image, new Integer(rast));

		int splitIndex = path.lastIndexOf('.');
		String newPath = path.substring(0, splitIndex) + "_AVERA" + path.substring(splitIndex, path.length());
		FileImageWriter.write(enhanced, newPath);
		System.out.println("Averaged image saved as: \n" + newPath);
	}

}
