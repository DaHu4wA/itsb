package at.ac.fhsalzburg.mmtlb.applications.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.ac.fhsalzburg.mmtlb.applications.filters.LaplacianFilterType;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Helper for averaging filters etc
 * 
 * @author Stefan Huber
 */
public class SurroudingPixelHelper {

	/**
	 * Returns the averaged value for this position
	 * 
	 * @param originalImage
	 *            the unmodified image
	 * @param rasterSize
	 *            has to be uneven!!
	 * @param xPos
	 *            current x position starting from 0
	 * @param yPos
	 *            current y position starting from 0
	 */
	public static int getAverageValueForPosition(MMTImage originalImage, int rasterSize, int xPos, int yPos) {

		int graySum = 0;
		int fieldSum = 0;

		int xStartPos = xPos - (rasterSize / 2);
		int xEndPos = xStartPos + rasterSize;

		int yStartPos = yPos - (rasterSize / 2);
		int yEndPos = yStartPos + rasterSize;

		for (int y = yStartPos; y <= yEndPos; y++) {
			for (int x = xStartPos; x <= xEndPos; x++) {

				if (isOutOfSpace(originalImage, x, y)) {
					continue; // ignore fields out of space
				}
				fieldSum++;
				graySum += originalImage.getPixel2D(x, y);
			}
		}

		int averaged = graySum / fieldSum;

		return averaged;
	}

	/**
	 * Returns the median value for this position
	 * 
	 * @param originalImage
	 *            the unmodified image
	 * @param rasterSize
	 *            has to be uneven!!
	 * @param xPos
	 *            current x position starting from 0
	 * @param yPos
	 *            current y position starting from 0
	 */
	public static int getMedianValueForPosition(MMTImage originalImage, int rasterSize, int xPos, int yPos) {

		List<Integer> values = new ArrayList<Integer>();

		int xStartPos = xPos - (rasterSize / 2);
		int xEndPos = xStartPos + rasterSize;

		int yStartPos = yPos - (rasterSize / 2);
		int yEndPos = yStartPos + rasterSize;

		for (int y = yStartPos; y <= yEndPos; y++) {
			for (int x = xStartPos; x <= xEndPos; x++) {

				if (isOutOfSpace(originalImage, x, y)) {
					continue; // ignore fields out of space
				}
				values.add(originalImage.getPixel2D(x, y));
			}
		}

		int[] vals = new int[values.size()];
		for (int i = 0; i < vals.length; i++) {
			vals[i] = values.get(i);
		}
		Arrays.sort(vals);
		return (int) vals[(vals.length - 1) / 2];
	}

	public static int getLaplacianValueForPosition(MMTImage originalImage, LaplacianFilterType filterType, int xPos, int yPos) {

		int neighbourGraySum = 0;
		int factor = 0;

		if (!isOutOfSpace(originalImage, xPos + 1, yPos)) {
			factor++;
			neighbourGraySum += originalImage.getPixel2D(xPos + 1, yPos) * -1;
		}
		if (!isOutOfSpace(originalImage, xPos, yPos + 1)) {
			factor++;
			neighbourGraySum += originalImage.getPixel2D(xPos, yPos + 1) * -1;
		}
		if (!isOutOfSpace(originalImage, xPos - 1, yPos)) {
			factor++;
			neighbourGraySum += originalImage.getPixel2D(xPos - 1, yPos) * -1;
		}
		if (!isOutOfSpace(originalImage, xPos, yPos - 1)) {
			factor++;
			neighbourGraySum += originalImage.getPixel2D(xPos, yPos - 1) * -1;
		}

		if (LaplacianFilterType.EIGHT_NEIGHBOURHOOD == filterType) {

			if (!isOutOfSpace(originalImage, xPos + 1, yPos + 1)) {
				factor++;
				neighbourGraySum += originalImage.getPixel2D(xPos + 1, yPos + 1) * -1;
			}
			if (!isOutOfSpace(originalImage, xPos + 1, yPos - 1)) {
				factor++;
				neighbourGraySum += originalImage.getPixel2D(xPos + 1, yPos - 1) * -1;
			}
			if (!isOutOfSpace(originalImage, xPos - 1, yPos + 1)) {
				factor++;
				neighbourGraySum += originalImage.getPixel2D(xPos - 1, yPos + 1) * -1;
			}
			if (!isOutOfSpace(originalImage, xPos - 1, yPos - 1)) {
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

	private static boolean isOutOfSpace(MMTImage originalImage, int x, int y) {

		if (x < 0 || y < 0) {
			return true;
		}
		if (x >= originalImage.getWidth()) {
			return true;
		}

		if (y >= originalImage.getHeight()) {
			return true;
		}

		return false;
	}

}
