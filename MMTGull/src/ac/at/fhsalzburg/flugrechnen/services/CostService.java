package ac.at.fhsalzburg.flugrechnen.services;

import java.sql.Time;
import java.util.List;

import ac.at.fhsalzburg.flugrechnen.data.FlightData;

/**
 * Calculates the costs for a flight.
 * Every waiting minute costs "1"
 * 
 * @author Stefan Huber
 */
public class CostService {

	private static final double FLIGHT_TIME_COST_FACTOR = 1;
	private static final double WAITING_TIME_COST_FACTOR = 1;

	public static double getCosts(List<FlightData> flightData, boolean travelHome) {
		double costs = 0;
		Time referenceTime = Time.valueOf("00:00:00");

		for (FlightData data : flightData) {
			// sum up flight prices
			costs += data.getPrice();

			// get latest time as reference to calc how long the others have to
			// wait
			if (travelHome) {
				if (referenceTime.before(data.getStartTime())) {
					referenceTime = data.getStartTime();
				}
			} else {
				if (referenceTime.before(data.getDestTime())) {
					referenceTime = data.getDestTime();
				}
			}

			costs += getCostsForFlightTime(data);
		}
		costs += addCostsForWaitingTimes(flightData, referenceTime, travelHome);
		return costs;
	}

	private static double getCostsForFlightTime(FlightData data) {
		if (data.getDestTime().before(data.getStartTime())) {
			// this time goes over midnight
			long zero = Time.valueOf("00:00:00").getTime();
			long twentyfour = Time.valueOf("24:00:00").getTime();

			long toMidnight = twentyfour - data.getStartTime().getTime();
			toMidnight = (toMidnight / 1000) / 60;
			long fromMidnight = data.getDestTime().getTime() - zero;
			fromMidnight = (fromMidnight / 1000) / 60;

			return (toMidnight + fromMidnight) * FLIGHT_TIME_COST_FACTOR;

		} else {
			long millisDiff = data.getDestTime().getTime() - data.getStartTime().getTime();
			long minutesDiff = (millisDiff / 1000) / 60;
			return minutesDiff * FLIGHT_TIME_COST_FACTOR;
		}
	}

	private static double addCostsForWaitingTimes(List<FlightData> flightData, Time referenceTime, boolean travelHome) {
		double waitingCosts = 0;
		for (FlightData data : flightData) {
			if (travelHome) {
				waitingCosts += getDifferenceAsCostHome(data, referenceTime);
			} else {
				waitingCosts += getDifferenceAsCostAway(data, referenceTime);
			}
		}
		return waitingCosts;
	}

	private static double getDifferenceAsCostAway(FlightData data, Time referenceTime) {
		if (referenceTime.before(data.getDestTime())) {
			// this time goes over midnight
			long zero = Time.valueOf("00:00:00").getTime();
			long twentyfour = Time.valueOf("24:00:00").getTime();

			long toMidnight = twentyfour - data.getDestTime().getTime();
			toMidnight = (toMidnight / 1000) / 60;
			long fromMidnight = referenceTime.getTime() - zero;
			fromMidnight = (fromMidnight / 1000) / 60;

			return (toMidnight + fromMidnight) * WAITING_TIME_COST_FACTOR;

		} else {
			long millisDiff = referenceTime.getTime() - data.getDestTime().getTime();
			long minutesDiff = (millisDiff / 1000) / 60;
			return minutesDiff * WAITING_TIME_COST_FACTOR;
		}
	}

	private static double getDifferenceAsCostHome(FlightData data, Time referenceTime) {
		if (referenceTime.before(data.getStartTime())) {
			// this time goes over midnight
			long zero = Time.valueOf("00:00:00").getTime();
			long twentyfour = Time.valueOf("24:00:00").getTime();

			long toMidnight = twentyfour - data.getStartTime().getTime();
			toMidnight = (toMidnight / 1000) / 60;
			long fromMidnight = referenceTime.getTime() - zero;
			fromMidnight = (fromMidnight / 1000) / 60;

			return (toMidnight + fromMidnight) * WAITING_TIME_COST_FACTOR;

		} else {
			long millisDiff = referenceTime.getTime() - data.getStartTime().getTime();
			long minutesDiff = (millisDiff / 1000) / 60;
			return minutesDiff * WAITING_TIME_COST_FACTOR;
		}
	}

}
