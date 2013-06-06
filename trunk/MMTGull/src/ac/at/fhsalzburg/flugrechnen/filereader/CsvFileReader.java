package ac.at.fhsalzburg.flugrechnen.filereader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Does what the name says (reading a csv file)
 * 
 * @author Stefan Huber
 */
public class CsvFileReader {

	private final boolean hasHeader;
	private BufferedReader reader;
	private boolean hasNext = true;
	private String header = null;

	public CsvFileReader(String path, boolean hasHeader) {
		this.hasHeader = hasHeader;
		if (!path.toLowerCase().contains(".csv")) {
			System.err.println("DataType not supported! only .csv!");
		}

		try {
			reader = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String nextLine() {
		try {
			if (hasHeader && header == null) {
				header = reader.readLine();
			}
			String r = reader.readLine();
			if (r == null) {
				hasNext = false;
			}
			return r;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean hasNext() {
		return hasNext;
	}

	public String getHeader() {
		try {
			if (hasHeader && header == null) {
				header = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return header;
	}
}
