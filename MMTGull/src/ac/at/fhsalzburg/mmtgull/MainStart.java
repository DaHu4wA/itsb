package ac.at.fhsalzburg.mmtgull;

import java.util.List;

import ac.at.fhsalzburg.mmtgull.calculation.RatingCalculator;
import ac.at.fhsalzburg.mmtgull.data.User;
import ac.at.fhsalzburg.mmtgull.testdata.UserTestDataFiller;

/**
 * Usage: Enter the name of a user and run the application
 * 
 * @author Stefan Huber
 */
public class MainStart {

	public static void main(String[] args) {

		// Fill user Data
		List<User> users = UserTestDataFiller.fillUserData();
		RatingCalculator calc = new RatingCalculator(users);

		calc.calculate("Markus");

		// These users can also be tested:
		// calc.calculate("Annaelle");
		// calc.calculate("Justine");
		// calc.calculate("Elisa");
		// calc.calculate("Florence"); //has already seen everything :)
		// calc.calculate("Raphael");// has already seen everything :)
	}

}
