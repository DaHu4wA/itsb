package at.ac.fhsalzburg.mmtlb.applications;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import at.ac.fhsalzburg.mmtlb.applications.tools.HistogramTools;
import at.ac.fhsalzburg.mmtlb.interfaces.IFImageController;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageReader;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageWriter;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Application to determine the histogram of a given {@link MMTImage}
 * 
 * It can either be started via commandline, or visualized via the main GUI
 * 
 * @author Stefan Huber
 */
public class HistogramEqualization extends AbstractImageModificationWorker {

	public HistogramEqualization() {
		super(null, null);
	}

	public HistogramEqualization(IFImageController controller, MMTImage sourceImage) {
		super(controller, sourceImage);
	}

	public MMTImage performHistogramEqualitzation(MMTImage image) throws InterruptedException {
		MMTImage result = new MMTImage(image.getHeight(), image.getWidth());
		result.setName(image.getName());

		int[] mappedValues = getMappedGrayValues(image); //get resulting gray values for every gray value

		for (int i = 0; i < image.getImageData().length; i++) {
			publishProgress(image, i);
			checkIfInterrupted();
			
			// map the calculated gray value 
			result.getImageData()[i] = mappedValues[image.getImageData()[i]];
		}

		return result;
	}

	/**
	 * Calculates the new gray values (gTilde) for every gray value
	 */
	private static int[] getMappedGrayValues(MMTImage image) {
		int[] result = new int[256];

		BigDecimal[] normHist = HistogramTools.getNormalizedHistogram(image);
		BigDecimal wMax = new BigDecimal(255);

		for (int i = 0; i < result.length; i++) {

			BigDecimal sum = BigDecimal.ZERO;

			for (int x = 0; x < i; x++) {
				sum = sum.add(normHist[x]);
			}
			result[i] = (sum.multiply(wMax)).intValue();
		}

		return result;
	}

	public static void main(String[] args) throws Exception {

		System.out.println("Histogram determination, text version");
		System.out.println("Enter the full path to a picture: ");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String path = br.readLine();

		MMTImage image = FileImageReader.read(path);

		int hist[] = HistogramTools.getHistogram(image);
		System.out.println("Here is the histogram (grayValue: count):\n");
		for (int i = 0; i < hist.length; i++) {
			System.out.println(String.format("%d", hist[i]));
		}

		MMTImage enhanced = new HistogramEqualization().performHistogramEqualitzation(image);

		int splitIndex = path.lastIndexOf('.');
		String newPath = path.substring(0, splitIndex) + "_HISTEQU" + path.substring(splitIndex, path.length());
		FileImageWriter.write(enhanced, newPath);
		System.out.println("Histogram Equalization image saved as: \n" + newPath);
	}

	@Override
	protected MMTImage modifyImage(MMTImage sourceImage) throws InterruptedException {
		return performHistogramEqualitzation(sourceImage);
	}

}
