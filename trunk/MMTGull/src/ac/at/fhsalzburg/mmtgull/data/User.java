package ac.at.fhsalzburg.mmtgull.data;

import java.util.HashMap;
import java.util.Map;


/**
 * The user containing its username and a list of rated images
 * 
 * @author Stefan Huber
 */
public class User {

	private String userName;// name of user
	private Map<String, RatedImage> ratedImages = new HashMap<String, RatedImage>();

	public User(String userName) {
		this.userName = userName;
	}

	public Map<String, RatedImage> getRatings() {
		return ratedImages;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void addRatedImage(RatedImage ratedImage) {
		ratedImages.put(ratedImage.getImageName(), ratedImage);
	}

}
