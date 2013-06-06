package ac.at.fhsalzburg.flugrechnen.services;

import java.util.ArrayList;
import java.util.List;

import ac.at.fhsalzburg.flugrechnen.data.FlightData;
import ac.at.fhsalzburg.flugrechnen.data.UserData;

/**
 * Service to filter relevant data from the big list of flights
 * 
 * @author Stefan Huber
 */
public class FlightDataService {

	public static List<FlightData> getAwayFlightsForUser(List<FlightData> allFlights, UserData user) {
		return getRelevantFlights(allFlights, user.getSourceAirportCode(), user.getDestinationAirportCode());
	}

	public static List<FlightData> getHomeFlightsForUser(List<FlightData> allFlights, UserData user) {
		return getRelevantFlights(allFlights, user.getDestinationAirportCode(), user.getSourceAirportCode());
	}

	/**
	 * @param allFlights the unfiltered list of all flights
	 * @param startAirport the start airport code
	 * @param destAirport the destination airport code
	 * @returns a new List containing all flights with given start and end
	 *          airports
	 */
	public static List<FlightData> getRelevantFlights(List<FlightData> allFlights, String startAirport, String destAirport) {
		List<FlightData> result = new ArrayList<FlightData>();

		for (FlightData flight : allFlights) {
			if (flight.getStartAirport().trim().toUpperCase().equals(startAirport.trim().toUpperCase())
					&& flight.getDestAirport().trim().toUpperCase().equals(destAirport.trim().toUpperCase())) {
				result.add(flight);
			}
		}
		return result;
	}

}
