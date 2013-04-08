package at.ac.fhsalzburg.mmtlb.applications;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageReader;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Application to determine the histogram of a given {@link MMTImage}
 * 
 * It can either be started via commandline, or visualized via the main GUI
 * 
 * @author Stefan Huber
 */
public class HistogramDetermination {

	// TODO also a command line tool

	public static int[] getHistogram(MMTImage image) {

		// We have a counter for every gray value (from 0 to 255).
		// Per java definition, all values of an array are zero per default, so
		// no further initializing is needed
		int[] hist = new int[256];

		// Iterate over all pixels
		for (int i = 0; i < image.getImageData().length; i++) {
			// +1 for the position of gray array
			hist[image.getImageData()[i]]++;
			// TODO maybe check if imagedata is higher than 255?
		}

		return hist;
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
			System.out.println(String.format("%d: %d", i, hist[i]));
		}

	}

}
