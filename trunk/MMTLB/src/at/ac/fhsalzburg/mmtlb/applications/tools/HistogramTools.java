package at.ac.fhsalzburg.mmtlb.applications.tools;

import java.math.BigDecimal;
import java.math.RoundingMode;

import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Tools for histogram
 * 
 * @author Stefan Huber
 */
public class HistogramTools {

	private static final int MIN_GRAY = 0;
	private static final int MAX_GRAY = 255;
	
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

	/**
	 * @param image source image
	 * @return a normalized histogram
	 */
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
	
	public static int getGlobalMean(MMTImage image) {
		BigDecimal globalMean = BigDecimal.ZERO;
		BigDecimal[] nHist = getNormalizedHistogram(image);
		for (int i = MIN_GRAY; i <= MAX_GRAY; i++) {
			globalMean = globalMean.add(nHist[i].multiply(new BigDecimal(i)));
		}
		return globalMean.intValue();
	}

}
