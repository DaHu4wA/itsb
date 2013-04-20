package at.ac.fhsalzburg.mmtlb.applications.threshold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import at.ac.fhsalzburg.mmtlb.applications.AbstractImageModificationWorker;
import at.ac.fhsalzburg.mmtlb.applications.tools.HistogramTools;
import at.ac.fhsalzburg.mmtlb.gui.IFImageController;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageReader;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageWriter;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Applies a iterative global thresholding
 * 
 * @author Stefan Huber
 */
public class IterativeGlobalThresholding extends AbstractImageModificationWorker {
	private static final Logger LOG = Logger.getLogger("IterativeThresholding");

	private static final int DELTA_THRESHOLD = 1;
	private static final int MAX_ITERATIONS = 200; // to prevent a loop

	public IterativeGlobalThresholding() {
		super(null, null);
	}

	public IterativeGlobalThresholding(IFImageController controller, MMTImage sourceImage) {
		super(controller, sourceImage);
	}

	/**
	 * Perform the iterative global thresholding
	 * 
	 * @param sourceImage source image
	 * @return the result binary thresholded image
	 */
	public MMTImage performIterativeThresholding(MMTImage sourceImage) {
		int iterationCount = 0;
		int threshold = 0;
		int delta = 255;

		// Calculate the global mean value as start value
		int newThreshold = HistogramTools.getGlobalMean(sourceImage);
		LOG.info("Start threshold: " + newThreshold);
		while ((delta > DELTA_THRESHOLD) && iterationCount < MAX_ITERATIONS) {
			threshold = newThreshold;
			iterationCount++;

			List<Integer> lowerValues = new ArrayList<Integer>();
			List<Integer> upperValues = new ArrayList<Integer>();

			for (int i = 0; i < sourceImage.getImageData().length; i++) {

				int value = sourceImage.getImageData()[i];

				if (value > threshold) {
					upperValues.add(value);
				} else {
					lowerValues.add(value);
				}
			}
			int m1 = getAverage(lowerValues);
			int m2 = getAverage(upperValues);

			newThreshold = (m1 + m2) / 2;
			delta = Math.abs(threshold - newThreshold);

			LOG.debug("Current delta: " + delta);
			LOG.debug("Current newThreshold: " + newThreshold);
		}
		publish(75);
		LOG.info("Sum of iterations: " + iterationCount + 1);
		LOG.info("Final threshold for global thresholding: " + newThreshold);
		return new GlobalThresholding().performThresholding(sourceImage, newThreshold);
	}

	private int getAverage(List<Integer> list) {
		if (list.isEmpty()) {
			return 0;
		}
		int sum = 0;
		for (int i : list) {
			sum += i;
		}
		return sum / list.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected MMTImage modifyImage(MMTImage sourceImage) {
		return performIterativeThresholding(sourceImage);
	}

	public static void main(String[] args) throws IOException {

		System.out.println("Iterative Global thresholding");
		System.out.println("Enter the full path to a picture: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String path = br.readLine();

		MMTImage image = FileImageReader.read(path);
		MMTImage newImage = new IterativeGlobalThresholding().performIterativeThresholding(image);

		int splitIndex = path.lastIndexOf('.');
		String newPath = path.substring(0, splitIndex) + "_GIterThreshold" + path.substring(splitIndex, path.length());
		FileImageWriter.write(newImage, newPath);
		System.out.println("Iterative Global thresholded image saved as: \n" + newPath);
	}

}
