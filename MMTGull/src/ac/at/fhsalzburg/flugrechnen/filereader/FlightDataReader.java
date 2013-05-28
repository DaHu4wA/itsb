package ac.at.fhsalzburg.flugrechnen.filereader;

import java.util.ArrayList;
import java.util.List;

import ac.at.fhsalzburg.flugrechnen.data.FlightData;

/**
 * Parses a given .csv file to a list of {@link FlightData}
 * 
 * @author Stefan Huber
 */
public class FlightDataReader {

	/**
	 * usage: FlightDataReader.importFlightData("D:\\flugplan.csv");
	 * */
	public static List<FlightData> importFlightData(String pathToFile) {
		List<FlightData> result = new ArrayList<FlightData>();

		CsvFileReader reader = new CsvFileReader(pathToFile, true);

		long count = 0;
		while (reader.hasNext()) {
			String data = reader.nextLine();
			if (data != null) {
				result.add(convertData(data, count));
				count++;
			}
		}
		return result;
	}

	private static FlightData convertData(String data, long oid) {
		String[] split = data.split(";");
		return new FlightData(oid, split[0], split[1], split[2], split[3], split[4]);
	}

}
