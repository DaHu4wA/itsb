package at.ac.fhsalzburg.mmtlb.applications.threshold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import at.ac.fhsalzburg.mmtlb.applications.AbstractImageModificationWorker;
import at.ac.fhsalzburg.mmtlb.gui.IFImageController;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageReader;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageWriter;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Applies otsu filter to a {@link MMTImage}
 * 
 * @author Stefan Huber
 */
public class OtsuThresholding extends AbstractImageModificationWorker {
	private static final Logger LOG = Logger.getLogger("OtsuThresholding");

	private static final int MIN_GRAY = 0;
	private static final int MAX_GRAY = 255;

	public OtsuThresholding() {
		super(null, null);
	}

	public OtsuThresholding(IFImageController controller, MMTImage sourceImage) {
		super(controller, sourceImage);
	}

	public MMTImage performOtsu(MMTImage sourceImage) {
		MMTImage result = new MMTImage(sourceImage.getHeight(), sourceImage.getWidth());
		result.setName(sourceImage.getName());
		
		
		// TODO do this! screen presentation page 189
		

		return result;
	}

	@Override
	protected MMTImage modifyImage(MMTImage sourceImage) {
		return performOtsu(sourceImage);
	}

	public static void main(String[] args) throws IOException {

		System.out.println("Otsu filter");
		System.out.println("Enter the full path to a picture: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String path = br.readLine();

		MMTImage image = FileImageReader.read(path);

		MMTImage newImage = new OtsuThresholding().performOtsu(image);

		int splitIndex = path.lastIndexOf('.');
		String newPath = path.substring(0, splitIndex) + "_Otsu" + path.substring(splitIndex, path.length());
		FileImageWriter.write(newImage, newPath);
		System.out.println("Otsu image saved as: \n" + newPath);
	}

}
