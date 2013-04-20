package at.ac.fhsalzburg.mmtlb.applications.threshold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import at.ac.fhsalzburg.mmtlb.applications.AbstractImageModificationWorker;
import at.ac.fhsalzburg.mmtlb.applications.tools.HistogramTools;
import at.ac.fhsalzburg.mmtlb.gui.IFImageController;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageReader;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageWriter;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Applies otsu filter to a {@link MMTImage}
 * 
 * @author Stefan Huber
 */
public class OtsuThresholding extends AbstractImageModificationWorker {
	private static final Logger LOG = Logger.getLogger("OtsuThresholding");

	private static final int MIN_GRAY = 0;
	private static final int MAX_GRAY = 255;

	public OtsuThresholding() {
		super(null, null);
	}

	public OtsuThresholding(IFImageController controller, MMTImage sourceImage) {
		super(controller, sourceImage);
	}

	/**
	 * Applies the otsu thresholding method to a given {@link MMTImage}
	 * 
	 * @param sourceImage the source iage
	 * @return a otsu filtered image
	 */
	public MMTImage performOtsu(MMTImage sourceImage) {

		// calculate the thresholds
		BigDecimal[] thresholds = calculateThresholds(sourceImage);
		int k = getResultThreshold(thresholds);
		LOG.info("Result threshold: " + k);
		// global thresholding with resulting value
		return new GlobalThresholding().performThresholding(sourceImage, k);
	}

	private BigDecimal[] calculateThresholds(MMTImage sourceImage) {
		BigDecimal[] gammas = new BigDecimal[MAX_GRAY];

		// Normalized Histogram as basic for calculation
		BigDecimal[] nHist = HistogramTools.getNormalizedHistogram(sourceImage);

		// Determine global mean value
		BigDecimal globalMean = getMeanValue(nHist, MIN_GRAY, MAX_GRAY);
		LOG.info("Global mean: " + globalMean);
		// iterate over every k from 0 to 254 to determine thresholds
		for (int k = MIN_GRAY; k < MAX_GRAY; k++) {

			int progress = (k * 100) / MAX_GRAY;
			publish(progress % 5);

			BigDecimal propLower = getSumOf(nHist, MIN_GRAY, k);
			BigDecimal propHigher = BigDecimal.ONE.subtract(propLower);

			// include null checks for divisions
			BigDecimal meanLower = propLower.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : getMeanValue(nHist, 0, k).divide(
					propLower, BigDecimal.ROUND_HALF_UP);
			BigDecimal meanHigher = propHigher.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : getMeanValue(nHist, k + 1, MAX_GRAY)
					.divide(propHigher, BigDecimal.ROUND_HALF_UP);

			BigDecimal lowerExpression = meanLower.subtract(globalMean);
			lowerExpression = lowerExpression.pow(2);
			lowerExpression = lowerExpression.multiply(propLower);

			BigDecimal higherExpression = meanHigher.subtract(globalMean);
			higherExpression = higherExpression.pow(2);
			higherExpression = higherExpression.multiply(propHigher);

			gammas[k] = lowerExpression.add(higherExpression);

			LOG.info("Threshold for " + k + ": " + gammas[k]);
		}
		return gammas;
	}

	private BigDecimal getMeanValue(BigDecimal[] nHist, int start, int stop) {
		BigDecimal globalMean = BigDecimal.ZERO;

		for (int i = start; i <= stop; i++) {
			globalMean = globalMean.add(nHist[i].multiply(new BigDecimal(i)));
		}
		return globalMean;
	}

	/**
	 * Sum up values for a given range
	 */
	private BigDecimal getSumOf(BigDecimal[] nHist, int start, int end) {
		BigDecimal prop = BigDecimal.ZERO;

		for (int i = start; i <= end; i++) {
			prop = prop.add(nHist[i]);
		}
		return prop;
	}

	/**
	 * Returns the resulting threshold
	 * 
	 * @param gammas where to get the highest from
	 * @return resulting threshold
	 */
	private int getResultThreshold(BigDecimal[] gammas) {

		List<Integer> highestPositions = new ArrayList<Integer>();
		BigDecimal max = BigDecimal.ZERO;

		for (int i = 0; i < gammas.length; i++) {
			BigDecimal current = gammas[i].abs();

			if (current.compareTo(max) > 0) {
				// new list of maximum occurences
				highestPositions.clear();
				highestPositions.add(i);
				max = current;
			} else if (current.compareTo(max) == 0) {
				// same value like highest again
				highestPositions.add(i);
			}
		}
		if (highestPositions.size() > 1) {
			LOG.info("Averaging gray values");
			int sum = 0;
			for (int pos : highestPositions) {
				sum += pos;
			}
			return sum / highestPositions.size();
		} else {
			return highestPositions.get(0);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected MMTImage modifyImage(MMTImage sourceImage) {
		return performOtsu(sourceImage);
	}

	public static void main(String[] args) throws IOException {

		System.out.println("Otsu filter");
		System.out.println("Enter the full path to a picture: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String path = br.readLine();

		MMTImage image = FileImageReader.read(path);

		MMTImage newImage = new OtsuThresholding().performOtsu(image);

		int splitIndex = path.lastIndexOf('.');
		String newPath = path.substring(0, splitIndex) + "_Otsu" + path.substring(splitIndex, path.length());
		FileImageWriter.write(newImage, newPath);
		System.out.println("Otsu image saved as: \n" + newPath);
	}

}
