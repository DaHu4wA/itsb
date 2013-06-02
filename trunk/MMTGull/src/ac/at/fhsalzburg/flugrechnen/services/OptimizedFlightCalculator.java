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

	public OptimizedFlightCalculator(List<FlightData> flights, List<UserData> users) {
		this.allFlights = flights;
		this.users = users;
	}

	public void calculate() {
		List<UserWithFlightCandidates> usersWithFlights = determineFlightsForAllUsers();

		List<FlightData> flightsToDestination = calcAwayFlights(usersWithFlights);
		List<FlightData> flightsFromDestination = calcHomeFlights(usersWithFlights);

		System.out.println("________________________________________");
		System.out.println("\nOPTIMIZED RESULTS");
		System.out.println("\n=== Flights to destination ===");
		for (FlightData f : flightsToDestination) {
			System.out.println(f);
		}
		System.out.println("\n=== Flights back home ===");
		for (FlightData f : flightsFromDestination) {
			System.out.println(f);
		}
	}

	private List<FlightData> calcAwayFlights(List<UserWithFlightCandidates> usersWithFlights) {
		System.out.print("\nCalculating flights to destination... ");
		// FIXME all users hopefully have the same amount of flights ;)
		// FIXME improve this !
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
		System.out.print("\nCalculating flights back home... ");
		// FIXME all users hopefully have the same amount of flights ;)
		// FIXME improve this !
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

}
