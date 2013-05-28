package ac.at.fhsalzburg.flugrechnen.main;

import java.net.URISyntaxException;
import java.util.List;

import ac.at.fhsalzburg.flugrechnen.file.FlightData;
import ac.at.fhsalzburg.flugrechnen.file.FlightDataReader;

/**
 * Main start of the flight time analysis
 * 
 * @author Stefan Huber
 */
public class FlightOptimizerMain {

	public static void main(String[] args) throws URISyntaxException {

		// FlightDataReader.importFlightData("D:\\flugplan.csv");
		List<FlightData> imported = FlightDataReader.importFlightData(FlightOptimizerMain.class.getResource("flugplan.csv").toURI().getPath());

		for (FlightData data : imported) {
			System.out.println(data.toString());
		}

		// FIXME TODO algorithmus von S.47 seiner DiplArbeit implementieren

		/**
		 * 6 Personen sollen möglichst Zeitlgeich in MUC (München) ankommen.
		 * 
		 * 
		 * ein flugplan mit allen personen (hin und rückflug), dafür die
		 * kosten.. das mit allen möglichen flügen.
		 * 
		 * Regel überlegen, wie man Flugpläne zusammenstellt, weil sonst die
		 * Kosten immer anders sind. zb bitweise (immer 1. und 1. flug, dann 1.
		 * und 2. flug)
		 * 
		 * 	Pläne zB so kombinieren:
		 * 	1	1	1
		 * 	2	1	1
		 * 	3	1	1
		 * 
		 * 	1	2	1
		 * 	2	2	1
		 * 	3	2	1
		 * 
		 * so kann man 1. oder 2. flugplan ausgeben
		 * 	...
		 * 	...
		 */

	}

}
