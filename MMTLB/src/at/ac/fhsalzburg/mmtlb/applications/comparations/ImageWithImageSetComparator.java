package at.ac.fhsalzburg.mmtlb.applications.comparations;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageReader;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageWriter;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

public class ImageWithImageSetComparator {

	public static final int AVERAGE_OPTION = 0;
	public static final int VARIANCE_OPTION = 1;

	public static String compare(MMTImage referenceImage, File directory, int type) {

		List<MMTImage> otherImages = new ArrayList<MMTImage>();

		File[] files = directory.listFiles();

		for (File file : files) {
			MMTImage img = FileImageReader.read(file);
			if (img != null) {
				otherImages.add(img);
			}
		}
		return compare(referenceImage, otherImages, type, directory);
	}

	public static String compare(MMTImage referenceImage, List<MMTImage> otherImages, int type, File directory) {

		final List<RatedMMTImage> scoreList = new ArrayList<RatedMMTImage>();

		// compare all otherImages with referenceImage
		for (final MMTImage otherImage : otherImages) {
			if (AVERAGE_OPTION == type) {
				compareGlobalMean(referenceImage, scoreList, otherImage);
			} else if (VARIANCE_OPTION == type) {
				compareVariance(referenceImage, scoreList, otherImage);
			} else {
				throw new IllegalArgumentException("invalid type!");
			}
		}

		// sort by lowest score first (lower -> better!!)
		Collections.sort(scoreList, new Comparator<RatedMMTImage>() {

			@Override
			public int compare(RatedMMTImage o1, RatedMMTImage o2) {
				return o1.getScore() - o2.getScore();
			}
		});

		String rootDir = directory.getPath() + "\\search";
		File rootFile = new File(rootDir);
		rootFile.mkdir();

		String result = "Top 10 images similar to " + referenceImage.getName() + "\n";
		// print top 10
		int score = 1;
		for (RatedMMTImage ratedMMTImage : scoreList) {
			if (score > 10) {
				break;
			}
			result = result + "\n#" + score + ": " + ratedMMTImage.getImage().getName() + "  (diff: " + ratedMMTImage.getScore() + ")";
			String scoredFilePath = rootDir + "\\" + score + "-" + ratedMMTImage.getImage().getName() + ".png"; 
			FileImageWriter.write(ratedMMTImage.getImage(), scoredFilePath, "png");
			score++;
		}
		result = result + "\n\nTop 10 Files saved into: "+rootDir;
		return result;
	}

	/**
	 * Use Global Mean Technique
	 */
	private static void compareGlobalMean(MMTImage referenceImage, final List<RatedMMTImage> scoreList, final MMTImage otherImage) {
		new GlobalMeanImageComparator(new ComparationFinishedCallback() {

			@Override
			public void comparationFinsihed(Integer comparationResult) {
				scoreList.add(new RatedMMTImage(otherImage, (Integer) comparationResult));
			}
		}).compareImages(referenceImage, otherImage);
	}

	/**
	 * Use Variance Method
	 */
	private static void compareVariance(MMTImage referenceImage, final List<RatedMMTImage> scoreList, final MMTImage otherImage) {
		new VarianceImageComparator(new ComparationFinishedCallback() {

			@Override
			public void comparationFinsihed(Integer comparationResult) {
				scoreList.add(new RatedMMTImage(otherImage, (Integer) comparationResult));
			}
		}).compareImages(referenceImage, otherImage);
	}

}
