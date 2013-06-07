package ac.at.fhsalzburg.semantic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import ac.at.fhsalzburg.semantic.parser.RssListParser;

public class WebSiteSemanticMain {

	public static void main(String[] args) throws Exception {

		List<String> urls = readUrlsFromFile(WebSiteSemanticMain.class.getResource("bloglist2.txt").toURI().getPath());

		RssListParser.parseAndCleanFeeds(urls);

	}

	private static List<String> readUrlsFromFile(String file) throws FileNotFoundException, URISyntaxException, IOException {
		List<String> urls = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(file));

		String r = reader.readLine();
		while (r != null) {
			urls.add(r);
			r = reader.readLine();
		}
		reader.close();
		return urls;
	}

}
