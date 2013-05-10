package ac.at.fhsalzburg.mmtgull.calculation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ac.at.fhsalzburg.mmtgull.data.CoefficientFromUser;
import ac.at.fhsalzburg.mmtgull.data.RatedImage;
import ac.at.fhsalzburg.mmtgull.data.User;

/**
 * This class does the calculation of the Rating
 * 
 * @author Stefan Huber
 */
public class RatingCalculator {

	private List<User> users;

	public RatingCalculator(List<User> users) {
		this.users = new ArrayList<User>(users);
	}

	/** A Rating value of -1 means no rating **/
	public void calculate(String userName) {
		System.out.println("\n<< RatingCalculator will now calculate which image " + userName + " should see next >>\n");
		// get the user and compare it with all others
		User compareUser = null;
		for (User u : users) {
			if (u.getUserName().toUpperCase().trim().equals(userName.toUpperCase().trim())) {
				compareUser = u;
				users.remove(u);
				break;
			}
		}

		if (compareUser == null) {
			// handle if user cannot be found in existing user list
			System.err.println("User \"" + userName + "\" could not be found!\nGoodbye :-)");
			return;
		}

		// do the real work in here and print out the result
		getRatingSuggestion(compareUser);
	}

	private void getRatingSuggestion(User compareUser) {
		List<CoefficientFromUser> coefficients = new ArrayList<CoefficientFromUser>();

		// Calculate the CorrCoeff for every user
		for (User u : users) {
			float coeff = calculateCorrCoeff(compareUser, u);
			coefficients.add(new CoefficientFromUser(u, coeff));

			System.out.println("Coefficient from " + compareUser.getUserName() + " to " + u.getUserName() + ": \t" + coeff);
		}

		// Multiply the ratings with the CorrCoeffs (for every user)
		calculateRelativeCoefficients(coefficients);

		// Get a image suggestion
		RatedImage resultImage = getSuggestion(compareUser, coefficients);

		System.out.println("\n\n----\n\n>> " + compareUser.getUserName() + " should see \"" + resultImage.getImageName() + "\". (Rating: "
				+ resultImage.getRating() + ")");
	}

	/**
	 * Multiplies the ratings with the CorrCoeffs (for every user)
	 */
	private void calculateRelativeCoefficients(List<CoefficientFromUser> coefficients) {
		for (CoefficientFromUser coeff : coefficients) {
			for (RatedImage image : coeff.getUser().getRatings().values()) {
				if (image.getRating() <= 0 || coeff.getCoefficient() <= 0) {
					image.setRating(-1);
					continue;
				}
				image.setRating(image.getRating() * coeff.getCoefficient());
			}
		}
	}

	private RatedImage getSuggestion(User user, List<CoefficientFromUser> coefficients) {

		Map<String, RatedImage> sumOfImages = new HashMap<String, RatedImage>();
		Map<String, Float> summSimmCoeffs = new HashMap<String, Float>();
		for (CoefficientFromUser coeff : coefficients) {

			if (coeff.getCoefficient() <= 0) {
				continue;
			}

			for (RatedImage img : coeff.getUser().getRatings().values()) {
				boolean noRating = false;

				if (img.getRating() <= 0) {
					noRating = true;
					continue;
				}

				if (!sumOfImages.containsKey(img.getImageName())) {
					sumOfImages.put(img.getImageName(), new RatedImage(img.getImageName(), img.getRating()));
				} else {
					sumOfImages.get(img.getImageName()).setRating(sumOfImages.get(img.getImageName()).getRating() + img.getRating());
				}

				if (!noRating) {
					if (summSimmCoeffs.containsKey(img.getImageName())) {
						Float val = summSimmCoeffs.get(img.getImageName());
						summSimmCoeffs.remove(img.getImageName());
						summSimmCoeffs.put(img.getImageName(), val + coeff.getCoefficient());
					} else {
						summSimmCoeffs.put(img.getImageName(), coeff.getCoefficient());
					}
				}
			}
		}
		// System.out.println("\n----\n");
		for (RatedImage image : sumOfImages.values()) {
			// System.out.print("Division " + image.getImageName() + ": " +
			// image.getRating() + "/" +
			// summSimmCoeffs.get(image.getImageName())
			// + "=");
			float rating = image.getRating() / (summSimmCoeffs.get(image.getImageName()));
			// System.out.println(rating);
			image.setRating(rating);
		}
		System.out.println("\n----");

		RatedImage highest = new RatedImage("USER HAS ALREADY SEEN EVERY IMAGE!", -1);
		for (RatedImage image : sumOfImages.values()) {

			printRating(image);
			if (user.getRatings().containsKey(image.getImageName()) && user.getRatings().get(image.getImageName()).getRating() <= 0) {
				System.out.print("   \t(" + image.getRating() + ")");
				if (image.getRating() >= highest.getRating()) {
					highest = image;
				}
			} else {
				System.out.print("   \t(Excluded, has alreade been rated)");
			}
		}

		return highest;
	}

	private void printRating(RatedImage image) {
		if (image.getImageName().length() < 12) {
			System.out.print("\nRating for " + image.getImageName() + ": \t\t" + Math.round(image.getRating()));

		} else {
			System.out.print("\nRating for " + image.getImageName() + ": \t" + Math.round(image.getRating()));
		}
	}

	/**
	 * Calculating the CorrCoeffs from user1 in relation to user2 A Rating
	 * negative value means no rating
	 * 
	 * @param fixCompareUser the reference user (x) that always keep the same
	 * @param currCompareUser the user currently compared to
	 * 
	 * */
	private float calculateCorrCoeff(User fixCompareUser, User currCompareUser) {

		float xAverage = getAverageOfRatings(fixCompareUser);
		float yAverage = getAverageOfRatings(currCompareUser);

		float upperVal = calcUpperVal(fixCompareUser, currCompareUser, xAverage, yAverage);
		float lowerVal = calcLowerVal(fixCompareUser, currCompareUser, xAverage, yAverage);

		return upperVal / lowerVal; // correlation coefficient rxy
	}

	/**
	 * As the name says, we calculate the value upper fraction line
	 */
	private float calcUpperVal(User user1, User user2, float xAverage, float yAverage) {
		float upperSum = 0.0F;
		for (String key : user1.getRatings().keySet()) {
			RatedImage imageX = user1.getRatings().get(key);
			RatedImage imageY = user2.getRatings().get(key);
			checkMaps(imageX, imageY);

			float xi = imageX.getRating();
			float yi = imageY.getRating();

			if (xi <= 0 || yi <= 0) {
				continue; // ignore if one value is not set
			}

			upperSum += (xi - xAverage) * (yi - yAverage);
		}
		return upperSum;
	}

	/**
	 * Calculate the value lower the fraction line
	 */
	private float calcLowerVal(User user1, User user2, float xAverage, float yAverage) {
		float temp1 = 0.0F;
		float temp2 = 0.0F;

		for (String key : user1.getRatings().keySet()) {

			RatedImage imageX = user1.getRatings().get(key);
			RatedImage imageY = user2.getRatings().get(key);
			checkMaps(imageX, imageY);

			float xi = imageX.getRating();
			float yi = imageY.getRating();

			if (xi <= 0 || yi <= 0) {
				continue; // ignore if one value is not set
			}

			temp1 += getLeastSquare(xi, xAverage);
			temp2 += getLeastSquare(yi, yAverage);

		}
		return (float) Math.sqrt(temp1 * temp2);
	}

	/**
	 * Checks if both maps are the same size (so if both users have rated this
	 * image)
	 */
	private void checkMaps(RatedImage imageX, RatedImage imageY) {
		if (imageX == null || imageY == null) {
			throw new UnsupportedOperationException("User lists have to contain same images!");
		}
	}

	/**
	 * Calcs the two values within the sqrt in the lower fraction
	 */
	private float getLeastSquare(float i, float iAverage) {
		return (float) Math.pow(i - iAverage, 2);
	}

	/**
	 * Average all ratings the given user made, ignoring unrated images
	 */
	private float getAverageOfRatings(User user) {
		float sum = 0.0F;
		int count = 0;
		for (RatedImage image : user.getRatings().values()) {
			if (image.getRating() <= 0) {
				// not rated, so don't add this one to calculation
				continue;
			}
			sum += image.getRating();
			count++;
		}
		return sum / count;
	}

}
