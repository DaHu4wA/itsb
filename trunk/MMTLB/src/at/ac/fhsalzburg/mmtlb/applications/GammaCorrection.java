package at.ac.fhsalzburg.mmtlb.applications;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import at.ac.fhsalzburg.mmtlb.gui.IFImageController;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageReader;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageWriter;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Do gamma correction for an image
 * 
 * @author Stefan Huber
 */
public class GammaCorrection extends AbstractImageModificationWorker {
	private static final Logger LOG = Logger.getLogger(GammaCorrection.class.getSimpleName());

	private double gamma = 1d;

	public GammaCorrection() {
		super(null, null);
	}

	public GammaCorrection(IFImageController controller, MMTImage sourceImage, double gamma) {
		super(controller, sourceImage);
		this.gamma = gamma;
	}

	public MMTImage correctGamma(MMTImage image, double gamma) throws InterruptedException {
		MMTImage stretched = new MMTImage(image.getHeight(), image.getWidth());
		stretched.setName(image.getName());

		// To be faster, we calculate the gamma values only once
		int[] mappingValues = calculateGammaValues(image, gamma);

		for (int i = 0; i < image.getImageData().length; i++) {
			// map all values from old to corrected gray value
			publishProgress(image, i);
			checkIfInterrupted();
			stretched.getImageData()[i] = mappingValues[image.getImageData()[i]];
		}
		return stretched;
	}

	private static int[] calculateGammaValues(MMTImage image, double gamma) {
		int[] mapping = new int[256];

		double wMin = 0;
		double wMax = 255;

		for (int g = 0; g < mapping.length; g++) {
			// Determine gTilde (new gamma) for every contrast value

			double base = (g - wMin) / (wMax - wMin);
			double powed = Math.pow(base, gamma);
			mapping[g] = new Long(Math.round(wMax * powed)).intValue();

			LOG.debug("gamma for " + g + ": " + mapping[g]);
		}

		return mapping;
	}

	public static void main(String[] args) throws Exception {

		System.out.println("Gamma correction");

		System.out.println("Enter the full path to a picture: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String path = br.readLine();

		MMTImage image = FileImageReader.read(path);

		System.out.println("Enter a new gamma value: ");
		String gammaString = br.readLine();
		Double gamma = Double.valueOf(gammaString);

		MMTImage newImage = new GammaCorrection().correctGamma(image, gamma);

		int splitIndex = path.lastIndexOf('.');
		String newPath = path.substring(0, splitIndex) + "_GAMMACORR" + path.substring(splitIndex, path.length());
		FileImageWriter.write(newImage, newPath);
		System.out.println("Gamma corrected image saved as: \n" + newPath);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected MMTImage modifyImage(MMTImage sourceImage) throws InterruptedException {
		return correctGamma(sourceImage, gamma);
	}

}
