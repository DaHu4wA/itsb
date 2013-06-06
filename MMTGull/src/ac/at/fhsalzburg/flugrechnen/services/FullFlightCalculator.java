package ac.at.fhsalzburg.flugrechnen.services;

import java.util.ArrayList;
import java.util.List;

import ac.at.fhsalzburg.flugrechnen.data.FlightData;
import ac.at.fhsalzburg.flugrechnen.data.UserData;
import ac.at.fhsalzburg.flugrechnen.data.UserWithFlightCandidates;

/**
 * Full calculation for optimized flights. This calculates the real best flight
 * by using all combinations!
 * 
 * @author Stefan Huber
 */
public class FullFlightCalculator {

	private final List<FlightData> allFlights;
	private final List<UserData> users;

	// volatile ensures that the variable is syncronized over all threads
	private volatile boolean destinationCalcFinished = false;

	public FullFlightCalculator(List<FlightData> flights, List<UserData> users) {
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

				System.out.println("\n=== Flights back home ===");
				for (FlightData f : flightsFromDestination) {
					System.out.println(f);
				}
			}
		});
		homecalc.start();
	}

	private List<FlightData> calcAwayFlights(List<UserWithFlightCandidates> usersWithFlights) {

		UserDataCheckHelper.checkData(usersWithFlights);
		long startTime = System.currentTimeMillis();
		System.out.println("\nCalculating flights to destination... ");
		int count = usersWithFlights.get(0).getAwayFlights().size();
		long totalCount = 0;

		double lowestCosts = 999999999;
		List<FlightData> cheapestFlights = null;

		// TODO a for-iteration for every user.. how to improve this?
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
		System.out.println("\n(Did " + totalCount + " calculations to destination in " + (System.currentTimeMillis() - startTime) + "ms)");
		System.out.println("Lowest costs to destination: " + lowestCosts);

		return cheapestFlights;
	}

	private List<FlightData> calcHomeFlights(List<UserWithFlightCandidates> usersWithFlights) {

		UserDataCheckHelper.checkData(usersWithFlights);
		long startTime = System.currentTimeMillis();
		System.out.println("\nCalculating flights back home... ");
		int count = usersWithFlights.get(0).getHomeFlights().size();
		long totalCount = 0;

		double lowestCosts = 999999999;
		List<FlightData> cheapestFlights = null;

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
		long endTime = (System.currentTimeMillis() - startTime);
		// wait for the thread that calculates the destination to be finished
		while (!destinationCalcFinished) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("\n(Did " + totalCount + " calculations back home in " + endTime + "ms)");
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
}
