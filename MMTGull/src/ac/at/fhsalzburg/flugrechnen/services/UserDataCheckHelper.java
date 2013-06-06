package ac.at.fhsalzburg.flugrechnen.services;

import java.util.List;

import ac.at.fhsalzburg.flugrechnen.data.UserWithFlightCandidates;

public class UserDataCheckHelper {

	/**
	 * Checks if the users count is 6, because this is just a simple coded
	 * version
	 */
	public static void checkData(List<UserWithFlightCandidates> usersWithFlights) {
		if (usersWithFlights.size() != 6) {
			throw new RuntimeException("Exactly 6 users are needed!");
		}

		int awaySize = 0;
		int homeSize = 0;
		for (UserWithFlightCandidates us : usersWithFlights) {

			if (awaySize == 0) {
				awaySize = us.getAwayFlights().size();
			}
			if (homeSize == 0) {
				homeSize = us.getHomeFlights().size();
			}

			if (awaySize != us.getAwayFlights().size() || homeSize != us.getHomeFlights().size()) {
				throw new RuntimeException("All users must have the same amounts of flights!");
			}

		}
	}

}
