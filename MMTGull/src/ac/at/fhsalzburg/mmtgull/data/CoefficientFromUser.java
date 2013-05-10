package ac.at.fhsalzburg.mmtgull.data;


/**
 * This is used to store the CorrCoeff calculated for a user
 * 
 * @author Stefan Huber
 */
public class CoefficientFromUser {

	private float coefficient = 0.0F;
	private User user;

	public CoefficientFromUser(User user, float coefficient) {
		this.user = user;
		this.coefficient = coefficient;
	}

	public float getCoefficient() {
		return coefficient;
	}

	public User getUser() {
		return user;
	}

}
