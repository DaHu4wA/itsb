package ac.at.fhsalzburg.flugrechnen.file;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses a given .csv file to a list of {@link FlightData}
 * 
 * @author Stefan Huber
 */
public class FlightDataReader {

	public static List<FlightData> importFlightData(String pathToFile) {
		List<FlightData> result = new ArrayList<FlightData>();

		CsvFileReader reader = new CsvFileReader(pathToFile, true);

		while (reader.hasNext()) {
			String data = reader.nextLine();
			if (data != null) {
				result.add(convertData(data));
			}
		}
		return result;
	}

	private static FlightData convertData(String data) {
		String[] split = data.split(";");
		return new FlightData(split[0], split[1], split[2], split[3], split[4]);
	}

}
