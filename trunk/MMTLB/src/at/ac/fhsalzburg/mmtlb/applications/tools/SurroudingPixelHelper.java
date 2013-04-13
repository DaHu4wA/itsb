package at.ac.fhsalzburg.mmtlb.applications.tools;

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
