package ac.at.fhsalzburg.semantic.clusteranalyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ac.at.fhsalzburg.semantic.data.ClusterElement;

public class HierarchicalClusterAnalyzer {

	public long recursiveIterationCount = 0;

	// columns, rows
	public void analyze(Object[][] map) {

		List<ClusterElement> clusterElements = new ArrayList<ClusterElement>();

		// iterate over all rows except header and sum row and convert to a
		// cluster element
		for (int row = 2; row < map[0].length; row++) {
			clusterElements.add(new ClusterElement(0.0F, getRowOfMap(map, row)));
		}
		long startTime = System.currentTimeMillis();
		System.out.println("\n\n - - DOING HIERARCHICAL CLUSTER ANALYZATION - - \n");

		doClustering(clusterElements);
		System.out.println("Time for builing cluster: " + ((System.currentTimeMillis() - startTime) / 1000) + " sec");
	}

	private void doClustering(List<ClusterElement> clusterElements) {
		recursiveIterationCount++;
		System.out.print("\n" + recursiveIterationCount + "(" + clusterElements.size() + " elements)\t| ");

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
					// firstCluster.setParent(clusteredElement);
					// secondCluster.setParent(clusteredElement);

					tempClusters.add(clusteredElement);

				}
				filterIrrelevantClusterElements(tempClusters); // keep temp list
																// small
				System.out.print(".");
			}
			ClusterElement bestMatching = getBestMatchingClusterElement(tempClusters);
			clusterElements.removeAll(bestMatching.getChilds());
			clusterElements.add(bestMatching);

			doClustering(clusterElements);
		} else {
			// TODO print result
//			System.out.println(clusterElements);

			System.out.println("Iteration count: " + recursiveIterationCount);
		}

	}

	/**
	 * To keep the temp list small we only keep elements that might be relevant
	 * (highest 2 per iteration)
	 */
	private void filterIrrelevantClusterElements(List<ClusterElement> tempClusters) {
		ClusterElement relevant1 = tempClusters.get(0);
		ClusterElement relevant2 = null;

		Collections.sort(tempClusters, new Comparator<ClusterElement>() {
			@Override
			public int compare(ClusterElement o1, ClusterElement o2) {
				// sort by r ascending
				return (int) (o2.getR() - o1.getR());
			}
		});

		for (ClusterElement clusterElement : tempClusters) {
			// if a new one is higher ranked
			if ((clusterElement.getR() > relevant1.getR())) {
				// rank the old one down
				relevant2 = relevant1;
				// and put the new one to the top
				relevant1 = clusterElement;
			}
		}

		tempClusters.clear();
		tempClusters.add(relevant1);
		if (relevant2 != null) {
			tempClusters.add(relevant2);
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
