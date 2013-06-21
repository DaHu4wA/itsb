package ac.at.fhsalzburg.semantic.clusteranalyzer;

import java.util.ArrayList;
import java.util.List;

import ac.at.fhsalzburg.semantic.data.ClusterElement;

public class HierarchicalClusterAnalyzer {

	public long recursiveIterationCount = 0;

	// columns, rows
	public void analyze(Object[][] map) {

		// methodik ist im dokument beschrieben

		// TODO alle blogs gegenüberstellen, rekursiv berechnen

		List<ClusterElement> clusterElements = new ArrayList<ClusterElement>();

		// iterate over all rows except header and sum row
		for (int row = 2; row < map[0].length; row++) {
			Object[] firstBlog = getRowOfMap(map, row);
			for (int secondRow = 2; secondRow < map[0].length; secondRow++) {
				if (row == secondRow) {
					continue; // don't compare with myself!
				}
				Object[] secondBlog = getRowOfMap(map, secondRow);
				// float r = calculateCorrCoeff(firstBlog, secondBlog);
				float r = 0.0F; // not needed here
				clusterElements.add(new ClusterElement(r, mergeBlogs(firstBlog, secondBlog)));
			}
		}
		long startTime = System.currentTimeMillis();
		doClustering(clusterElements);
		System.out.println("Time for builing cluster: " + ((System.currentTimeMillis() - startTime) / 1000) + " sec");
	}

	private void doClustering(List<ClusterElement> clusterElements) {
		recursiveIterationCount++;
		if (clusterElements.size() > 1) {
			List<ClusterElement> tempClusters = new ArrayList<ClusterElement>();
			for (int firstElem = 0; firstElem < clusterElements.size(); firstElem++) {
				ClusterElement firstCluster = clusterElements.get(firstElem);
				for (int secondElem = 0; secondElem < clusterElements.size(); secondElem++) {
					if (firstElem == secondElem) {
						continue; // don't compare with myself!
					}
					ClusterElement secondCluster = clusterElements.get(secondElem);
					float r = calculateCorrCoeff(firstCluster.getWordCounts(), secondCluster.getWordCounts());
					ClusterElement clusteredElement = new ClusterElement(r, mergeBlogs(firstCluster.getWordCounts(),
							secondCluster.getWordCounts()), firstCluster, secondCluster);
					firstCluster.setParent(clusteredElement);
					secondCluster.setParent(clusteredElement);

					tempClusters.add(clusteredElement);
					// alle berechnen und besten zwei kombinieren
				}
			}
			ClusterElement bestMatching = getBestMatchingClusterElement(tempClusters);
			clusterElements.removeAll(bestMatching.getChilds());
			clusterElements.add(bestMatching);
			doClustering(clusterElements);
		} else {
			// TODO print result
			// System.out.println(clusterElements);
			System.out.println("Iteration count: " + recursiveIterationCount);
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

	private float calculateCorrCoeff(Object[] firstBlog, Object[] secondBlog) {

		// sum of words for first blog
		float xAverage = getAverage(firstBlog);
		// sum of words for second blog
		float yAverage = getAverage(secondBlog);

		float sxy = calcSxy(firstBlog, secondBlog, xAverage, yAverage);
		float sxsy = calcSxSy(firstBlog, secondBlog, xAverage, yAverage);

		return sxy / sxsy; // correlation coefficient rxy
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

	private ClusterElement getBestMatchingClusterElement(List<ClusterElement> tempClusters) {
		ClusterElement bestMatching = tempClusters.get(0);

		for (ClusterElement clusterElement : tempClusters) {
			if (clusterElement.getR() > bestMatching.getR()) {
				bestMatching = clusterElement;
			}
		}

		return bestMatching;
	}

}
