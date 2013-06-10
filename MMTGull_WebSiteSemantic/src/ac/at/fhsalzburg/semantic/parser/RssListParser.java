package ac.at.fhsalzburg.semantic.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.horrabin.horrorss.RssItemBean;

import ac.at.fhsalzburg.semantic.data.Feed;
import ac.at.fhsalzburg.semantic.data.WordsWithCountAndUrl;

/**
 * Parses a list of RSS feeds
 * 
 * @author Stefan Huber
 */
public class RssListParser {

	private static final Pattern PATTERN_HTML_TAGS = Pattern.compile("<.+?>");
	private static final Pattern PATTERN_HTML_REPLACES = Pattern.compile("\\&.+?\\;");
	private static final Pattern PATTERN_ONLY_CHARACTERS_AND_SPACES = Pattern.compile("[^\\p{L}\\p{N}]");
	private static final Pattern PATTERN_SHORT_WORDS = Pattern.compile("\\b\\w{0,4}\\b");
	private static final Pattern PATTERN_LONG_WORDS = Pattern.compile("\\b\\w{14,}\\b");

	public static int parseAndCleanFeeds(List<String> feeds) {

		List<WordsWithCountAndUrl> allAnalyzedFeeds = new ArrayList<WordsWithCountAndUrl>();

		for (String feed : feeds) {
			WordsWithCountAndUrl singleFeed = parseAndCleanSingleFeed(feed);
			if (singleFeed != null) { // if null: feed was not available!
				allAnalyzedFeeds.add(singleFeed);
			}
		}
		System.out.println("\n");

		new AnalyzedWordMatcher().matchWordLists(allAnalyzedFeeds);

		return allAnalyzedFeeds.size();
	}

	private static WordsWithCountAndUrl parseAndCleanSingleFeed(String feed) {
		List<RssItemBean> items = RssFeedFetcher.parse(feed);

		if (items == null) {
			return null; // feed not available
		}

		List<Feed> cleanedFeeds = new ArrayList<Feed>();

		WordAnalyzer analyzer = new WordAnalyzer();
		for (int i = 0; i < items.size(); i++) {
			RssItemBean item = items.get(i);
			Feed cleaned = new Feed(clean(item.getTitle()), clean(item.getDescription()));
			cleanedFeeds.add(cleaned);

			analyzer.analyze(cleaned);
		}
		return new WordsWithCountAndUrl(feed, analyzer.getWordsWithCount());
	}

	private static String clean(String toClean) {

		if (toClean == null || toClean.trim().isEmpty()) {
			return "";
		}

		Matcher m = PATTERN_HTML_TAGS.matcher(toClean);
		String clean = m.replaceAll(" ");

		m = PATTERN_HTML_REPLACES.matcher(clean);
		clean = m.replaceAll(" ");

		m = PATTERN_ONLY_CHARACTERS_AND_SPACES.matcher(clean);
		clean = m.replaceAll(" ");

		m = PATTERN_SHORT_WORDS.matcher(clean);
		clean = m.replaceAll(" ");

		m = PATTERN_LONG_WORDS.matcher(clean);
		clean = m.replaceAll(" ");

		while (clean.contains("  ")) {
			clean = clean.replace("  ", " ");
		}

		return clean;
	}
}
