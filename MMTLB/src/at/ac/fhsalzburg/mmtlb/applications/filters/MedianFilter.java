package at.ac.fhsalzburg.mmtlb.applications.filters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import at.ac.fhsalzburg.mmtlb.applications.AbstractImageModificationWorker;
import at.ac.fhsalzburg.mmtlb.applications.tools.SurroudingPixelHelper;
import at.ac.fhsalzburg.mmtlb.gui.IFImageController;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageReader;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageWriter;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Applies an median filter to a {@link MMTImage}
 * 
 * @author Stefan Huber
 */
public class MedianFilter extends AbstractImageModificationWorker {

	private int raster = 3; // default size

	public MedianFilter(IFImageController controller, MMTImage sourceImage) {
		super(controller, sourceImage);
	}

	@Override
	protected MMTImage modifyImage(MMTImage sourceImage) {
		return performMedianFilter(sourceImage, raster);
	}

	/**
	 * Performs median filter
	 * 
	 * @param image
	 *            the image to apply median filter to
	 * @param rasterSize
	 *            UNEVEN (3x3, 5x5 etc) value
	 * @return a new median filtered image
	 */
	public static MMTImage performMedianFilter(MMTImage image, int rasterSize) {
		MMTImage result = new MMTImage(image.getHeight(), image.getWidth());
		result.setName(image.getName());

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				result.setPixel2D(x, y, SurroudingPixelHelper.getMedianValueForPosition(image, rasterSize, x, y));
			}
		}
		return result;
	}

	public int getRaster() {
		return raster;
	}

	public void setRaster(int raster) {
		this.raster = raster;
	}

	public static void main(String[] args) throws IOException {

		System.out.println("Median filter, text version");
		System.out.println("Enter the full path to a picture: ");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String path = br.readLine();

		System.out.println("Raster size: Please enter an UNEVEN number, at least 3: ");
		BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
		String rast = br2.readLine();

		MMTImage image = FileImageReader.read(path);

		MMTImage enhanced = performMedianFilter(image, new Integer(rast));

		int splitIndex = path.lastIndexOf('.');
		String newPath = path.substring(0, splitIndex) + "_MF" + path.substring(splitIndex, path.length());
		FileImageWriter.write(enhanced, newPath);
		System.out.println("Median filtered image saved as: \n" + newPath);
	}

}
