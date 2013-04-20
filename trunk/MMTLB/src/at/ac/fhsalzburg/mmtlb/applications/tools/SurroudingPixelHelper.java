package at.ac.fhsalzburg.mmtlb.applications.tools;

import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Helper for averaging filters etc
 * 
 * @author Stefan Huber
 */
public class SurroudingPixelHelper {

	/**
	 * Simple check if the current position is inside image bounds
	 * 
	 * @param originalImage the original image
	 * @param x the current x position
	 * @param y the current y position
	 * @return
	 */
	public static boolean isOutOfSpace(MMTImage originalImage, int x, int y) {

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
