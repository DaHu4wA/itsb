package at.ac.fhsalzburg.mmtlb.applications.tools;

import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Helper for averaging filters etc
 * 
 * @author Stefan Huber
 */
public class SurroudingPixelHelper {

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
