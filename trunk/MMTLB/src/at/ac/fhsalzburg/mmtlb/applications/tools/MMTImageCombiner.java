package at.ac.fhsalzburg.mmtlb.applications.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import at.ac.fhsalzburg.mmtlb.applications.AbstractImageModificationWorker;
import at.ac.fhsalzburg.mmtlb.applications.filters.LaplacianFilter;
import at.ac.fhsalzburg.mmtlb.applications.filters.LaplacianFilterType;
import at.ac.fhsalzburg.mmtlb.applications.filters.SobelFilter;
import at.ac.fhsalzburg.mmtlb.gui.IFImageController;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageReader;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageWriter;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Util to combine a original {@link MMTImage} with a modified image
 * 
 * @author Stefan Huber
 */
public class MMTImageCombiner extends AbstractImageModificationWorker {

	private final double factor;
	private final MMTImage currentImage;

	public MMTImageCombiner(IFImageController controller, MMTImage sourceImage, MMTImage currentImage, double factor) {
		super(controller, sourceImage);
		this.currentImage = currentImage;
		this.factor = factor;
	}

	/**
	 * Combine image with a given factor
	 * 
	 * @param base
	 *            root image where other image will be added to
	 * @param other
	 *            image to add
	 * @param factor
	 *            the factor the other image will be added
	 * @returns a combined image
	 */
	public MMTImage combine(MMTImage base, MMTImage other, double factor) {
		MMTImage result = new MMTImage(base.getHeight(), base.getWidth());
		result.setName(base.getName());

		for (int i = 0; i < base.getImageData().length; i++) {

			// add other image to base image
			publishProgress(base, i);
			int grayVal = (int) ((double) base.getImageData()[i] + ((double) factor * (double) other.getImageData()[i]));

			// clipping
			grayVal = grayVal < 0 ? 0 : grayVal;
			grayVal = grayVal > 255 ? 255 : grayVal;

			result.getImageData()[i] = grayVal;
		}
		return result;
	}

	@Override
	protected MMTImage modifyImage(MMTImage sourceImage) {
		return combine(sourceImage, currentImage, factor);
	}
	
	
	public static void main(String[] args) throws IOException {
		
		
		System.out.println("Image Sharpening tool, text version");
		System.out.println("Enter the full path to a picture: ");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String path = br.readLine();

		MMTImage image = FileImageReader.read(path);

		if(image == null){
			System.err.println("Wrong image path! aborting ..");
			return;
		}

		System.out.println("Please now enter (L)aplacian or (S)obel: ");
		String type = br.readLine();

		MMTImage enhanced = null;
		if(type.equals("S")){
			enhanced = new SobelFilter().performSobel(image);
		}else if(type.equals("L")){
			enhanced = new LaplacianFilter().performLaplacian(image, LaplacianFilterType.FOUR_NEIGHBOURHOOD);
		}
		else{
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
