package at.ac.fhsalzburg.mmtlbcolor.filters;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

/**
 * Colored version of Laplacian 8-neighbourhood NOT using MMTImage! 
 * 
 * @author Stefan Huber
 */
public class ColoredLaplacianFilter {

	public static BufferedImage laplace8(BufferedImage image) throws InterruptedException {
		float[] raster = { -1.0f, -1.0f, -1.0f, -1.0f, 8.f, -1.0f, -1.0f, -1.0f, -1.0f };
		BufferedImageOp laplacianOP = new ConvolveOp(new Kernel(3, 3, raster));
		return laplacianOP.filter(image, null);
	}

	public static void main(String[] args) throws IOException, NumberFormatException, InterruptedException {
		System.out.println("COLORED Laplacian 8-neighbourhood filter, text version");
		System.out.println("Enter the full path to a picture: ");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String path = br.readLine();

		BufferedImage coloredImage = ImageIO.read(new File(path));
		BufferedImage result = laplace8(coloredImage);

		int splitIndex = path.lastIndexOf('.');
		String newPath = path.substring(0, splitIndex) + "_LAPL8" + path.substring(splitIndex, path.length());

		ImageIO.write(result, "jpg", new File(newPath));
		System.out.println("Laplacian filtered image saved as: \n" + newPath);
	}

}
