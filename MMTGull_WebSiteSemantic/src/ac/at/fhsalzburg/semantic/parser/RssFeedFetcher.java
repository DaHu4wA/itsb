package ac.at.fhsalzburg.semantic.parser;

import java.util.List;

import org.horrabin.horrorss.RssFeed;
import org.horrabin.horrorss.RssItemBean;
import org.horrabin.horrorss.RssParser;

public class RssFeedFetcher {

	public static List<RssItemBean> parse(String url) {
		RssParser rss = new RssParser();

		try {
			System.out.print("\nFetching RSS feed \"" + url + "\"...");
			RssFeed feed = rss.load(url);
			System.out.print(" OK");
			return feed.getItems();
		} catch (Exception e) {
			System.out.print(" FAILED!");
			return null;
		}
	}
}
