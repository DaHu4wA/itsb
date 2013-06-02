package ac.at.fhsalzburg.flugrechnen.data;

import java.util.ArrayList;
import java.util.List;

public class UserWithFlightCandidates {

	private UserData user;
	private List<FlightData> awayFlights;
	private List<FlightData> homeFlights;

	public UserWithFlightCandidates(UserData user) {
		this.user = user;
		this.awayFlights = new ArrayList<FlightData>();
		this.homeFlights = new ArrayList<FlightData>();
	}

	public UserData getUser() {
		return user;
	}

	public void setUser(UserData user) {
		this.user = user;
	}

	public List<FlightData> getAwayFlights() {
		return awayFlights;
	}

	public void setAwayFlights(List<FlightData> awayFlights) {
		this.awayFlights = awayFlights;
	}

	public List<FlightData> getHomeFlights() {
		return homeFlights;
	}

	public void setHomeFlights(List<FlightData> homeFlights) {
		this.homeFlights = homeFlights;
	}

}
