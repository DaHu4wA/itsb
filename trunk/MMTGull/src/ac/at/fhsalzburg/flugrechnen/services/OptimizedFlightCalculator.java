package ac.at.fhsalzburg.flugrechnen.services;

import java.util.ArrayList;
import java.util.List;

import ac.at.fhsalzburg.flugrechnen.data.FlightData;
import ac.at.fhsalzburg.flugrechnen.data.UserData;
import ac.at.fhsalzburg.flugrechnen.data.UserWithFlightCandidates;

public class OptimizedFlightCalculator {

	private final List<FlightData> allFlights;
	private final List<UserData> users;

	public OptimizedFlightCalculator(List<FlightData> flights, List<UserData> users) {
		this.allFlights = flights;
		this.users = users;
	}

	
	public void calculate(){
		System.out.println("Calculating...");
		
		
		// jetzt muessen wir die fluege kombinieren und die Kosten pro Kombi ausrechnen
		
		
		
		
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
