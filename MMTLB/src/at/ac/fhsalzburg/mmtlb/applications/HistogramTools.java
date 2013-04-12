package at.ac.fhsalzburg.mmtlb.applications;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;

import at.ac.fhsalzburg.mmtlb.gui.IFImageController;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageReader;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageWriter;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Application to determine the histogram of a given {@link MMTImage}
 * 
 * It can either be started via commandline, or visualized via the main GUI
 * 
 * @author Stefan Huber
 */
public class HistogramTools extends AbstractImageModificationWorker {
	// private static final Logger LOG =
	// Logger.getLogger(HistogramTools.class.getSimpleName());

	public HistogramTools(IFImageController controller, MMTImage sourceImage) {
		super(controller, sourceImage);
	}

	public static int[] getHistogram(MMTImage image) {

		// We have a counter for every gray value (from 0 to 255).
		// Per java definition, all values of an array are zero per default, so
		// no further initializing is needed
		int[] hist = new int[256];

		// Iterate over all pixels
		for (int i = 0; i < image.getImageData().length; i++) {
			// +1 for the grayValue of the current position
			hist[image.getImageData()[i]]++;
		}

		return hist;
	}

	public static BigDecimal[] getNormalizedHistogram(MMTImage image) {
		int[] hist = getHistogram(image);
		BigDecimal[] normalized = new BigDecimal[hist.length];
		BigDecimal n = BigDecimal.ZERO;

		// count all occurences
		for (int i = 0; i < hist.length; i++) {
			n = n.add(new BigDecimal(hist[i]));
		}

		// divide single values through n
		for (int i = 0; i < hist.length; i++) {
			BigDecimal value = new BigDecimal("0.00000000");
			value = value.add(new BigDecimal(hist[i]));
			normalized[i] = value.divide(n, RoundingMode.HALF_UP);
		}

		return normalized;
	}

	/**
	 * @returns the lowest gray value contained
	 */
	public static int getLowestGrayValue(MMTImage image) {
		int gMin = 0;
		int[] hist = getHistogram(image);

		for (int i = 0; i < hist.length; i++) {
			if (hist[i] == 0) {
				gMin = i;
			} else {
				break; // lowest found
			}
		}
		return gMin;
	}

	/**
	 * @returns the highest gray value of the image
	 */
	public static int getHighestGrayValue(MMTImage image) {
		int gMax = 255;
		int[] hist = getHistogram(image);

		for (int i = (hist.length - 1); i >= 0; i--) {
			if (hist[i] == 0) {
				gMax = i;
			} else {
				break; // highest found
			}
		}
		return gMax;
	}

	public static MMTImage performHistogramEqualitzation(MMTImage image) {
		MMTImage result = new MMTImage(image.getHeight(), image.getWidth());

		int[] mappedValues = getMappedGrayValues(image);

		for (int i = 0; i < image.getImageData().length; i++) {
			result.getImageData()[i] = mappedValues[image.getImageData()[i]];
		}

		return result;
	}

	private static int[] getMappedGrayValues(MMTImage image) {
		int[] result = new int[256];

		BigDecimal[] normHist = getNormalizedHistogram(image);
		BigDecimal wMax = new BigDecimal(255);
		System.out.println(String.format("Wmax of original: %d", wMax.intValue()));

		for (int i = 0; i < result.length; i++) {

			BigDecimal sum = BigDecimal.ZERO;

			for (int x = 0; x < i; x++) {
				sum = sum.add(normHist[x]);
			}

			result[i] = (sum.multiply(wMax)).intValue();
		}

		return result;
	}

	public static void main(String[] args) throws IOException {

		System.out.println("Histogram determination, text version");
		System.out.println("Enter the full path to a picture: ");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String path = br.readLine();

		MMTImage image = FileImageReader.read(path);

		int hist[] = getHistogram(image);
		System.out.println("Here is the histogram (grayValue: count):\n");
		for (int i = 0; i < hist.length; i++) {
			System.out.println(String.format("%d", hist[i]));
		}

		MMTImage enhanced = performHistogramEqualitzation(image);

		FileImageWriter.write(enhanced, path);
		int splitIndex = path.lastIndexOf('.');
		String newPath = path.substring(0, splitIndex) + "_HE" + path.substring(splitIndex, path.length());
		FileImageWriter.write(enhanced, newPath);
		System.out.println("Histogram Equalization image saved as: \n" + newPath);

	}

	@Override
	protected MMTImage modifyImage(MMTImage sourceImage) {
		return performHistogramEqualitzation(sourceImage);
	}

}
