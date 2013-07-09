package ac.at.fhsalzburg.semantic.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClusterElement {

	float r;
	Object[] wordCounts;
	List<ClusterElement> childs = new ArrayList<>();
	boolean isSeedCluster = false;

	public ClusterElement(float r, Object[] mergedBlogs, ClusterElement... childs) {
		this.r = r;
		this.wordCounts = mergedBlogs;
		this.childs.addAll(Arrays.asList(childs));
	}

	public ClusterElement(boolean isSeedCluster) {
		this.isSeedCluster = isSeedCluster;
	}

	public ClusterElement(boolean isSeedCluster, ClusterElement clusterElement) {
		this.isSeedCluster = isSeedCluster;
		this.r = clusterElement.getR();
		this.wordCounts = clusterElement.getWordCounts();
		this.childs = clusterElement.getChilds();
	}

	public float getR() {
		return r;
	}

	public void setR(float r) {
		this.r = r;
	}

	public Object[] getWordCounts() {
		return wordCounts;
	}

	public void setWordCounts(Object[] wordCounts) {
		this.wordCounts = wordCounts;
	}

	public List<ClusterElement> getChilds() {
		return childs;
	}

	public void setChilds(List<ClusterElement> childs) {
		this.childs = childs;
	}

	public String getString(int in) {
		return "ClusterElem " + printChilds(in);// + " ... ";
	}

	private String printChilds(int in) {

		if (childs == null || childs.isEmpty()) {
			return "\t " + (String) wordCounts[0];
			// return "\n";
		}

		StringBuilder sb = new StringBuilder();

		for (ClusterElement e : childs) {

			sb.append("\n");
			for (int i = 0; i <= in; i++) {
				sb.append(" ");
			}

			sb.append("> ");
			sb.append(e.getString(in++));
		}

		return sb.toString();
	}

	public boolean isSeedCluster() {
		return isSeedCluster;
	}

	public void setSeedCluster(boolean isSeedCluster) {
		this.isSeedCluster = isSeedCluster;
	}
}
