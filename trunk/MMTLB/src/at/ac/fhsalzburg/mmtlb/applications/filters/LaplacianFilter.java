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
 * Applies an laplacian filter to a {@link MMTImage}
 * 
 * @author Stefan Huber
 */
public class LaplacianFilter extends AbstractImageModificationWorker {

	private LaplacianFilterType filterType = null; // default size

	public LaplacianFilter(IFImageController controller, MMTImage sourceImage) {
		super(controller, sourceImage);
	}

	@Override
	protected MMTImage modifyImage(MMTImage sourceImage) {
		return performLaplacian(sourceImage, filterType);
	}

	/**
	 * TODO comment
	 * 
	 * 4-neighourhood laplacian
	 */
	public MMTImage performLaplacian(MMTImage image, LaplacianFilterType filterType) {
		MMTImage result = new MMTImage(image.getHeight(), image.getWidth());
		result.setName(image.getName());

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				publishProgress(image, x + y * image.getWidth());
				result.setPixel2D(x, y, SurroudingPixelHelper.getLaplacianValueForPosition(image, filterType, x, y));
			}
		}
		return result;
	}

	public static void main(String[] args) throws IOException {

		System.out.println("Laplacian filter, text version");
		System.out.println("Enter the full path to a picture: ");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String path = br.readLine();

		MMTImage image = FileImageReader.read(path);

		MMTImage enhanced = new LaplacianFilter(null, null).performLaplacian(image, LaplacianFilterType.FOUR_NEIGHBOURHOOD);

		int splitIndex = path.lastIndexOf('.');
		String newPath = path.substring(0, splitIndex) + "_L4F" + path.substring(splitIndex, path.length());
		FileImageWriter.write(enhanced, newPath);
		System.out.println("Laplacian image saved as: \n" + newPath);
	}

	public void setFilterType(LaplacianFilterType filterType) {
		this.filterType = filterType;
	}

}
