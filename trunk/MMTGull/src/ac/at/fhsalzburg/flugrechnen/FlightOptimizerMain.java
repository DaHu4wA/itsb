package ac.at.fhsalzburg.flugrechnen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import ac.at.fhsalzburg.flugrechnen.data.FlightData;
import ac.at.fhsalzburg.flugrechnen.data.UserData;
import ac.at.fhsalzburg.flugrechnen.filereader.FlightDataReader;
import ac.at.fhsalzburg.flugrechnen.services.FullFlightCalculator;
import ac.at.fhsalzburg.flugrechnen.services.QuickRandomFlightCalculator;

/**
 * Main start of the flight time analysis
 * 
 * @author Stefan Huber
 */
public class FlightOptimizerMain {

	private static final String DESTINATION_AIRPORT = "MUC";

	public static void main(String[] args) throws URISyntaxException, IOException {

		List<FlightData> imported = FlightDataReader.importFlightData(FlightOptimizerMain.class.getResource("flugplan.csv").toURI()
				.getPath());
		for (FlightData data : imported) {
			System.out.println(data);
		}

		List<UserData> users = initUserData();
		for (UserData user : users) {
			System.out.println(user);
		}

		System.out.println("____________________________________________________________________");
		System.out.println("Imported all users and flights.");
		System.err.print("\nEnter \"Q\" for Quick random calculation, or \"F\" for Full calculation: ");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = br.readLine();

		if ("F".equals(input.trim().toUpperCase())) {
			FullFlightCalculator fullCalc = new FullFlightCalculator(imported, users);
			fullCalc.calculate();
		} else if ("Q".equals(input.trim().toUpperCase())) {
			QuickRandomFlightCalculator quickCalc = new QuickRandomFlightCalculator(imported, users);
			quickCalc.calculate();
		} else {
			System.err.println("\nWrong input: " + input+". ABORTING");
			return;
		}
	}

	private static List<UserData> initUserData() {
		List<UserData> result = new ArrayList<UserData>();

		result.add(new UserData(0, "Raphael", "Gautier", "Paris", "Orly", "ORY", DESTINATION_AIRPORT));
		result.add(new UserData(1, "Elisa", "Schmidt", "London", "Stansted", "STN", DESTINATION_AIRPORT));
		result.add(new UserData(2, "Konstanze", "Eder", "Rom", "Fiuminico", "FCO", DESTINATION_AIRPORT));
		result.add(new UserData(3, "Pauline", "Huber", "Berlin", "Tegel", "TXL", DESTINATION_AIRPORT));
		result.add(new UserData(4, "Florence", "Maier", "Kopenhagen", "Kastrup", "CPH", DESTINATION_AIRPORT));
		result.add(new UserData(5, "Viviane", "Dubois", "Madrid", "Barajas", "MAD", DESTINATION_AIRPORT));

		return result;
	}

}
