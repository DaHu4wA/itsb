package ac.at.fhsalzburg.mmtgull.data;

/**
 * This class represents a image with its rating and a name
 * 
 * @author Stefan Huber
 */
public class RatedImage {

	private String imageName;
	private float rating; // from 1 to 10 ... -1 means no rating!

	public RatedImage(String imageName, float rating) {
		this.imageName = imageName;
		this.rating = rating;
	}

	public String getImageName() {
		return imageName;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

}
