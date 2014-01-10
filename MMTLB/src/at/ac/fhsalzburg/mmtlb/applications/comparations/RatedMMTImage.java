package at.ac.fhsalzburg.mmtlb.applications.comparations;

import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

public class RatedMMTImage {

	private MMTImage image;
	private int score;

	public RatedMMTImage(MMTImage image, int score) {
		this.image = image;
		this.score = score;
	}

	public MMTImage getImage() {
		return image;
	}

	public void setImage(MMTImage image) {
		this.image = image;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
