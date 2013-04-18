package at.ac.fhsalzburg.mmtlb.applications.filters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import at.ac.fhsalzburg.mmtlb.applications.tools.MMTImageCombiner;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageReader;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageWriter;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Applies {@link SobelFilter} or {@link LaplacianFilter} to a image and uses
 * the {@link MMTImageCombiner} to create combined output image
 * 
 * @author Stefan Huber
 */
public class ImageSharpeningFilter {

	public static void main(String[] args) throws IOException {

		System.out.println("Image Sharpening tool, text version");
		System.out.println("Enter the full path to a picture: ");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String path = br.readLine();

		MMTImage image = FileImageReader.read(path);

		if (image == null) {
			System.err.println("Wrong image path! aborting ..");
			return;
		}

		System.out.println("Please now enter (L)aplacian or (S)obel: ");
		String type = br.readLine();

		MMTImage enhanced = null;
		if (type.equals("S")) {
			enhanced = new SobelFilter().performSobel(image);
		} else if (type.equals("L")) {
			enhanced = new LaplacianFilter().performLaplacian(image, LaplacianFilterType.FOUR_NEIGHBOURHOOD);
		} else {
			System.err.println("Wrong input! aborting ..");
			return;
		}

		System.out.println("Now enter a factor k (> 0) .. (eg. 0.3): ");
		String factor = br.readLine();
		Double k = new Double(factor);
		enhanced = new MMTImageCombiner(null, null, null, 0).combine(image, enhanced, k);

		int splitIndex = path.lastIndexOf('.');
		String newPath = path.substring(0, splitIndex) + "_Sharpened" + path.substring(splitIndex, path.length());
		FileImageWriter.write(enhanced, newPath);
		System.out.println("Sharpened image saved as: \n" + newPath);

	}

}
