package ac.at.fhsalzburg.semantic.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClusterElement {

	float r;
	Object[] wordCounts;
	ClusterElement parent;
	List<ClusterElement> childs = new ArrayList<>();

	public ClusterElement(float r, Object[] mergedBlogs, ClusterElement... childs) {
		this.r = r;
		this.wordCounts = mergedBlogs;
		this.childs.addAll(Arrays.asList(childs));
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

	public ClusterElement getParent() {
		return parent;
	}

	public void setParent(ClusterElement parent) {
		this.parent = parent;
	}

	public List<ClusterElement> getChilds() {
		return childs;
	}

	public void setChilds(List<ClusterElement> childs) {
		this.childs = childs;
	}

	@Override
	public String toString() {
		return "ClusterElement [r=" + r + ", childs=" + childs + "]";
	}
}
