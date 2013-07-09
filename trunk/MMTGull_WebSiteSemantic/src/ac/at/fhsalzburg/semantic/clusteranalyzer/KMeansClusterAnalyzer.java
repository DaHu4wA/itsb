package ac.at.fhsalzburg.semantic.clusteranalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import ac.at.fhsalzburg.semantic.data.ClusterElement;

public class KMeansClusterAnalyzer {

	private static int MAXCOUNT = 1000;
	int runCount = 0;

	public void analyze(Object[][] map, int clusterSeedCount) {

		List<ClusterElement> clusterElements = new ArrayList<ClusterElement>();

		for (int row = 2; row < map[0].length; row++) {
			clusterElements.add(new ClusterElement(0.0F, getRowOfMap(map, row)));
		}
		long startTime = System.currentTimeMillis();
		System.out.println("\n\n - - DOING K-MEANS CLUSTER ANALYZATION - - \n");

		doClustering(clusterElements, getSeedClusters(clusterElements, clusterSeedCount), true);

		System.out.println("Time for builing k-means cluster: " + ((System.currentTimeMillis() - startTime) / 1000) + " sec");
	}

	private List<ClusterElement> getSeedClusters(List<ClusterElement> clusterElements, int clusterSeedCount) {
		Random r = new Random();

		List<ClusterElement> clusters = new ArrayList<ClusterElement>();
		for (int i = 0; i < clusterSeedCount; i++) {

			int randPos = r.nextInt(clusterElements.size());
			ClusterElement seedCluster = new ClusterElement(true, clusterElements.get(randPos));
			clusters.add(seedCluster);
		}
		return clusters;
	}

	private void doClustering(List<ClusterElement> clusterElements, List<ClusterElement> seedClusters, boolean continueClustering) {

		if (continueClustering && runCount < MAXCOUNT) {
			runCount++;
			for (int seedCluster = 0; seedCluster < seedClusters.size() - 1; seedCluster++) {
				ClusterElement seedClusterElem = seedClusters.get(seedCluster);

				for (int cluster = 0; cluster < clusterElements.size(); cluster++) {

					ClusterElement clusterElem1 = clusterElements.get(cluster);
					ClusterElement clusterElem2 = clusterElements.get(cluster + 1);

					float r1 = calculateCorrCoeff1000(seedClusterElem.getWordCounts(), clusterElem1.getWordCounts());
					float r2 = calculateCorrCoeff1000(seedClusterElem.getWordCounts(), clusterElem2.getWordCounts());

					if (Math.round(seedClusterElem.getR()) == Math.round((r1 + r2) / 2)) {
						continueClustering = false;
					} else if (Math.round((r1 + r2) / 2) > Math.round(seedClusterElem.getR())) {
						seedClusterElem.setR((r1 + r2) / 2);
						seedClusterElem.setWordCounts(mergeBlogs(clusterElem1.getWordCounts(), clusterElem2.getWordCounts()));
						seedClusterElem.setChilds(Arrays.asList(clusterElem1, clusterElem2));
					} else {
						continue;
					}
				}
			}

			// TODO zuordnen der einzelnen cluster zu den seedClustern!

			doClustering(clusterElements, seedClusters, continueClustering);
		} else {
			System.out.println("END!");
		}

	}

	private Object[] mergeBlogs(Object[] firstBlog, Object[] secondBlog) {
		Object[] merged = new Object[firstBlog.length];

		merged[0] = "MERGED";
		for (int i = 1; i < firstBlog.length; i++) {
			merged[i] = (((Integer) firstBlog[i]) + ((Integer) secondBlog[i])) / 2;
		}
		return merged;
	}

	private float calculateCorrCoeff1000(Object[] firstBlog, Object[] secondBlog) {

		// sum of words for first blog
		float xAverage = getAverage(firstBlog);
		// sum of words for second blog
		float yAverage = getAverage(secondBlog);

		float sxy = calcSxy(firstBlog, secondBlog, xAverage, yAverage);
		float sxsy = calcSxSy(firstBlog, secondBlog, xAverage, yAverage);

		return (sxy / sxsy) * 1000; // correlation coefficient rxy
	}

	/**
	 * Calculates the value upper fraction line for Pearson (sxy)
	 */
	private float calcSxy(Object[] firstBlog, Object[] secondBlog, float xAverage, float yAverage) {
		float sxy = 0.0F;

		// i = 0 would be the blog name
		for (int i = 1; i < firstBlog.length; i++) {

			int xi = ((Integer) firstBlog[i]);
			int yi = ((Integer) secondBlog[i]);

			sxy += (xi - xAverage) * (yi - yAverage);
		}
		return sxy;
	}

	/**
	 * Calculate the value lower the fraction line (sx*sy)
	 */
	private float calcSxSy(Object[] firstBlog, Object[] secondBlog, float xAverage, float yAverage) {
		float sxq = 0.0F;
		float syq = 0.0F;

		for (int i = 1; i < firstBlog.length; i++) {

			int xi = ((Integer) firstBlog[i]);
			int yi = ((Integer) secondBlog[i]);

			sxq += getLeastSquare(xi, xAverage);
			syq += getLeastSquare(yi, yAverage);
		}
		return (float) Math.sqrt(sxq * syq);
	}

	private float getAverage(Object[] blog) {
		int sum = 0;
		int count = blog.length - 1; // the first Object is the blogname
		for (int i = 1; i < blog.length; i++) {
			sum += ((Integer) blog[i]);
		}
		return sum / count;
	}

	/**
	 * Calcs the two values within the sqrt in the lower fraction
	 */
	private float getLeastSquare(float i, float iAverage) {
		return (float) Math.pow(i - iAverage, 2);
	}

	private Object[] getRowOfMap(Object[][] map, int secondRow) {
		Object[] secondBlog = new Object[map.length];
		for (int i = 0; i < map.length; i++) {
			secondBlog[i] = map[i][secondRow];
		}
		return secondBlog;
	}

}
