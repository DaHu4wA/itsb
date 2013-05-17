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

public class ColoredAveragingFilter {

	public static BufferedImage performAveraging(BufferedImage image, int rasterSize) throws InterruptedException {
		float[] blurKernel = getRaster(rasterSize);
		BufferedImageOp blurOp = new ConvolveOp(new Kernel(rasterSize, rasterSize, blurKernel));
		BufferedImage clone = blurOp.filter(image, null);

		return clone;
	}

	private static float[] getRaster(int rasterSize) {
		rasterSize = rasterSize*rasterSize;
		float raster[] = new float[rasterSize] ;
		float val = 1.0f / rasterSize;
		for(int i = 0; i < rasterSize ; i++){
			raster[i] = val;
		}
		return raster;
	}

	public static void main(String[] args) throws IOException, NumberFormatException, InterruptedException {

		System.out.println("COLORED Averaging filter, text version");
		System.out.println("Enter the full path to a picture: ");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String path = br.readLine();

		System.out.println("Raster size: Please enter an UNEVEN number, at least 3: ");
		String rast = br.readLine();

		BufferedImage coloredImage = ImageIO.read(new File(path));

		BufferedImage result = performAveraging(coloredImage, new Integer(rast));

		int splitIndex = path.lastIndexOf('.');
		String newPath = path.substring(0, splitIndex) + "_AVERAGECOLOR" + path.substring(splitIndex, path.length());

		ImageIO.write(result,  "jpg", new File(newPath));
		
		System.out.println("Averaged image saved as: \n" + newPath);
	}

}
