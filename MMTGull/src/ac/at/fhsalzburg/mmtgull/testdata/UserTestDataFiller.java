package ac.at.fhsalzburg.mmtgull.testdata;

import java.util.ArrayList;
import java.util.List;

import ac.at.fhsalzburg.mmtgull.data.RatedImage;
import ac.at.fhsalzburg.mmtgull.data.User;

/**
 * Fills the users list with some cool test data. This data is equal to the data
 * seen in the PDF
 * 
 * @author Stefan Huber
 */
public class UserTestDataFiller {

	public static final String SYLVESTER_1 = "Sylvester1";
	public static final String SYLVESTER_2 = "Sylvester2";
	public static final String FLEURS_1 = "Fleurs1";
	public static final String FLEURS_2 = "Fleurs2";
	public static final String AUSBLICK = "Ausblick";
	public static final String MEMENTO_MORIS = "MementoMoris";
	public static final String IMPRESSION_IN_ROM_1 = "ImpressionInRom1";
	public static final String IMPRESSION_IN_ROM_2 = "ImpressionInRom2";

	public static List<User> fillUserData() {
		List<User> users = new ArrayList<User>();

		User florence = new User("Florence");
		florence.addRatedImage(new RatedImage(SYLVESTER_1, 6));
		florence.addRatedImage(new RatedImage(SYLVESTER_2, 7));
		florence.addRatedImage(new RatedImage(FLEURS_1, 9));
		florence.addRatedImage(new RatedImage(FLEURS_2, 10));
		florence.addRatedImage(new RatedImage(AUSBLICK, 3));
		florence.addRatedImage(new RatedImage(MEMENTO_MORIS, 3));
		florence.addRatedImage(new RatedImage(IMPRESSION_IN_ROM_1, 6));
		florence.addRatedImage(new RatedImage(IMPRESSION_IN_ROM_2, 7));
		users.add(florence);

		User raphael = new User("Raphael");
		raphael.addRatedImage(new RatedImage(SYLVESTER_1, 5));
		raphael.addRatedImage(new RatedImage(SYLVESTER_2, 4));
		raphael.addRatedImage(new RatedImage(FLEURS_1, 4));
		raphael.addRatedImage(new RatedImage(FLEURS_2, 6));
		raphael.addRatedImage(new RatedImage(AUSBLICK, 4));
		raphael.addRatedImage(new RatedImage(MEMENTO_MORIS, 2));
		raphael.addRatedImage(new RatedImage(IMPRESSION_IN_ROM_1, 5));
		raphael.addRatedImage(new RatedImage(IMPRESSION_IN_ROM_2, 6));
		users.add(raphael);

		User elisa = new User("Elisa");
		elisa.addRatedImage(new RatedImage(SYLVESTER_1, 1));
		elisa.addRatedImage(new RatedImage(SYLVESTER_2, 3));
		elisa.addRatedImage(new RatedImage(FLEURS_1, 3));
		elisa.addRatedImage(new RatedImage(FLEURS_2, 7));
		elisa.addRatedImage(new RatedImage(AUSBLICK, 5));
		elisa.addRatedImage(new RatedImage(MEMENTO_MORIS, -1));
		elisa.addRatedImage(new RatedImage(IMPRESSION_IN_ROM_1, 6));
		elisa.addRatedImage(new RatedImage(IMPRESSION_IN_ROM_2, 9));
		users.add(elisa);

		User justine = new User("Justine");
		justine.addRatedImage(new RatedImage(SYLVESTER_1, 4));
		justine.addRatedImage(new RatedImage(SYLVESTER_2, 5));
		justine.addRatedImage(new RatedImage(FLEURS_1, 5));
		justine.addRatedImage(new RatedImage(FLEURS_2, -1));
		justine.addRatedImage(new RatedImage(AUSBLICK, 1));
		justine.addRatedImage(new RatedImage(MEMENTO_MORIS, 9));
		justine.addRatedImage(new RatedImage(IMPRESSION_IN_ROM_1, -1));
		justine.addRatedImage(new RatedImage(IMPRESSION_IN_ROM_2, -1));
		users.add(justine);

		User annaelle = new User("Annaelle");
		annaelle.addRatedImage(new RatedImage(SYLVESTER_1, 7));
		annaelle.addRatedImage(new RatedImage(SYLVESTER_2, 6));
		annaelle.addRatedImage(new RatedImage(FLEURS_1, 3));
		annaelle.addRatedImage(new RatedImage(FLEURS_2, 10));
		annaelle.addRatedImage(new RatedImage(AUSBLICK, 3));
		annaelle.addRatedImage(new RatedImage(MEMENTO_MORIS, 8));
		annaelle.addRatedImage(new RatedImage(IMPRESSION_IN_ROM_1, -1));
		annaelle.addRatedImage(new RatedImage(IMPRESSION_IN_ROM_2, 4));
		users.add(annaelle);

		User markus = new User("Markus");
		markus.addRatedImage(new RatedImage(SYLVESTER_1, 5));
		markus.addRatedImage(new RatedImage(SYLVESTER_2, 6));
		markus.addRatedImage(new RatedImage(FLEURS_1, -1));
		markus.addRatedImage(new RatedImage(FLEURS_2, 8));
		markus.addRatedImage(new RatedImage(AUSBLICK, 4));
		markus.addRatedImage(new RatedImage(MEMENTO_MORIS, -1));
		markus.addRatedImage(new RatedImage(IMPRESSION_IN_ROM_1, 3));
		markus.addRatedImage(new RatedImage(IMPRESSION_IN_ROM_2, -1));
		users.add(markus);

		return users;
	}

}
