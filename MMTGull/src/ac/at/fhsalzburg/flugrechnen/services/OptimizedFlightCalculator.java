package ac.at.fhsalzburg.flugrechnen.services;

import java.util.ArrayList;
import java.util.List;

import ac.at.fhsalzburg.flugrechnen.data.FlightData;
import ac.at.fhsalzburg.flugrechnen.data.UserData;
import ac.at.fhsalzburg.flugrechnen.data.UserWithFlightCandidates;

/**
 * First version of calculation for optimized flights
 * 
 * @author Stefan Huber
 */
public class OptimizedFlightCalculator {

	private final List<FlightData> allFlights;
	private final List<UserData> users;

	private volatile boolean destinationCalcFinished = false;

	public OptimizedFlightCalculator(List<FlightData> flights, List<UserData> users) {
		this.allFlights = flights;
		this.users = users;
	}

	public void calculate() {
		final List<UserWithFlightCandidates> usersWithFlights = determineFlightsForAllUsers();

		Thread awaycalc = new Thread(new Runnable() {
			@Override
			public void run() {
				List<FlightData> flightsToDestination = calcAwayFlights(usersWithFlights);
				System.out.println("\n=== Flights to destination ===");
				for (FlightData f : flightsToDestination) {
					System.out.println(f);
				}
				destinationCalcFinished = true;
			}
		});
		awaycalc.start();

		Thread homecalc = new Thread(new Runnable() {
			@Override
			public void run() {
				List<FlightData> flightsFromDestination = calcHomeFlights(usersWithFlights);

				while (!destinationCalcFinished) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				System.out.println("\n=== Flights back home ===");
				for (FlightData f : flightsFromDestination) {
					System.out.println(f);
				}
			}
		});
		homecalc.start();
	}

	private List<FlightData> calcAwayFlights(List<UserWithFlightCandidates> usersWithFlights) {

		checkData(usersWithFlights);

		System.out.println("\nCalculating flights to destination... ");
		int count = usersWithFlights.get(0).getAwayFlights().size();
		long totalCount = 0;

		double lowestCosts = 999999999;
		List<FlightData> cheapestFlights = null;

		// FIXME a for-iteration for every user.. how to improve this?
		for (int a = 0; a < count; a++) {
			for (int b = 0; b < count; b++) {
				for (int c = 0; c < count; c++) {
					for (int d = 0; d < count; d++) {
						for (int e = 0; e < count; e++) {
							for (int f = 0; f < count; f++) {
								List<FlightData> currentFlights = new ArrayList<FlightData>();
								currentFlights.add(usersWithFlights.get(0).getAwayFlights().get(a));
								currentFlights.add(usersWithFlights.get(1).getAwayFlights().get(b));
								currentFlights.add(usersWithFlights.get(2).getAwayFlights().get(c));
								currentFlights.add(usersWithFlights.get(3).getAwayFlights().get(d));
								currentFlights.add(usersWithFlights.get(4).getAwayFlights().get(e));
								currentFlights.add(usersWithFlights.get(5).getAwayFlights().get(f));

								double costs = CostService.getCosts(currentFlights, false);

								if (costs < lowestCosts) {
									cheapestFlights = currentFlights;
									lowestCosts = costs;
								}
								totalCount++;
							}
						}
					}
				}
			}
		}
		System.out.println("(Finished doing " + totalCount + " calculations to destination)");
		System.out.println("Lowest costs to destination: " + lowestCosts);

		return cheapestFlights;
	}

	private List<FlightData> calcHomeFlights(List<UserWithFlightCandidates> usersWithFlights) {

		checkData(usersWithFlights);

		System.out.println("\nCalculating flights back home... ");
		int count = usersWithFlights.get(0).getHomeFlights().size();
		long totalCount = 0;

		double lowestCosts = 999999999;
		List<FlightData> cheapestFlights = null;

		// FIXME a for-iteration for every user.. how to improve this?
		for (int a = 0; a < count; a++) {
			for (int b = 0; b < count; b++) {
				for (int c = 0; c < count; c++) {
					for (int d = 0; d < count; d++) {
						for (int e = 0; e < count; e++) {
							for (int f = 0; f < count; f++) {
								List<FlightData> currentFlights = new ArrayList<FlightData>();
								currentFlights.add(usersWithFlights.get(0).getHomeFlights().get(a));
								currentFlights.add(usersWithFlights.get(1).getHomeFlights().get(b));
								currentFlights.add(usersWithFlights.get(2).getHomeFlights().get(c));
								currentFlights.add(usersWithFlights.get(3).getHomeFlights().get(d));
								currentFlights.add(usersWithFlights.get(4).getHomeFlights().get(e));
								currentFlights.add(usersWithFlights.get(5).getHomeFlights().get(f));

								double costs = CostService.getCosts(currentFlights, true);

								if (costs < lowestCosts) {
									cheapestFlights = currentFlights;
									lowestCosts = costs;
								}
								totalCount++;
							}
						}
					}
				}
			}
		}


		System.out.println("(Finished doing " + totalCount + " calculations back home)");
		System.out.println("Lowest costs back home: " + lowestCosts);

		return cheapestFlights;
	}

	public List<UserWithFlightCandidates> determineFlightsForAllUsers() {
		List<UserWithFlightCandidates> usersWithCandidates = new ArrayList<UserWithFlightCandidates>();
		for (UserData user : users) {
			UserWithFlightCandidates candidate = new UserWithFlightCandidates(user);
			candidate.getAwayFlights().addAll(FlightDataService.getAwayFlightsForUser(allFlights, user));
			candidate.getHomeFlights().addAll(FlightDataService.getHomeFlightsForUser(allFlights, user));
			usersWithCandidates.add(candidate);
		}
		return usersWithCandidates;
	}

	private void checkData(List<UserWithFlightCandidates> usersWithFlights) {
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
