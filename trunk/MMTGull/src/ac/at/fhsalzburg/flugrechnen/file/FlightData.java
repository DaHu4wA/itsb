package ac.at.fhsalzburg.flugrechnen.file;

import java.sql.Time;

/**
 * Container for a flight data set
 * 
 * @author Stefan Huber
 */
public class FlightData {
	private static final String SECONDS_TIMESTAMP = ":00";

	private String startAirport;
	private String destAirport;
	private Time startTime;
	private Time destTime;
	private double price;

	public FlightData(String startAirport, String destAirport, String startTime, String destTime, String price) {
		this.startAirport = startAirport;
		this.destAirport = destAirport;
		this.price = Double.valueOf(price);

		this.startTime = Time.valueOf(startTime + SECONDS_TIMESTAMP);
		this.destTime = Time.valueOf(destTime + SECONDS_TIMESTAMP);
	}

	public FlightData(String startAirport, String destAirport, Time startTime, Time destTime, double price) {
		this.startAirport = startAirport;
		this.destAirport = destAirport;
		this.startTime = startTime;
		this.destTime = destTime;
		this.price = price;
	}

	public String getStartAirport() {
		return startAirport;
	}

	public String getDestAirport() {
		return destAirport;
	}

	public Time getStartTime() {
		return startTime;
	}

	public Time getDestTime() {
		return destTime;
	}

	public double getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "FlightData [startAirport=" + startAirport + ", destAirport=" + destAirport + ", startTime=" + startTime + ", destTime="
				+ destTime + ", price=" + price + "]";
	}

}
