package ac.at.fhsalzburg.semantic.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

	// volatile ensures that all operations with this list are atomar
	private volatile List<WordsWithCountAndUrl> allAnalyzedFeeds = new ArrayList<WordsWithCountAndUrl>();

	FinishedCallback finishedCallback = new FinishedCallback() {
		@Override
		public void finished(WordsWithCountAndUrl result) {
			addFeed(result);
		}
	};

	public int parseAndCleanFeeds(List<String> feeds) {

		List<Thread> threads = new ArrayList<Thread>();
		for (final String feed : feeds) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					parseAndCleanSingleFeed(feed, finishedCallback);
				}
			});
			threads.add(t);
			t.start();
		}

		for (Thread thread : threads) {
			try {
				thread.join(); // wait for all threads to be finished
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("\nAll " + threads.size() + "threads finished!");
		threads.clear();

		AnalyzedWordMatcher analyzer = new AnalyzedWordMatcher();
		analyzer.matchWordLists(allAnalyzedFeeds);

		Object[][] map = buildArray(allAnalyzedFeeds, analyzer.getoverallWordsWithCount());

		// TODO analysieren mit den zwei Methoden

		return allAnalyzedFeeds.size();
	}

	private void addFeed(WordsWithCountAndUrl singleFeed) {
		if (singleFeed == null) { // if null: feed was not available!
			return;
		}
		allAnalyzedFeeds.add(singleFeed);
	}

	private static Object[][] buildArray(List<WordsWithCountAndUrl> allAnalyzedFeeds, Map<String, Integer> allWordsWithCount) {

		// columns:
		int columnCount = allWordsWithCount.size();
		columnCount += 1; // add the blogname column

		// rows:
		int rowCount = allAnalyzedFeeds.size();
		rowCount += 1; // to add a header line containing the words themselves
		rowCount += 1; // add the sums in the second line

		// 2d-array looks like this: first row are headers, first column blogs..
		// then all counts

		// columns, rows
		Object[][] map = new Object[columnCount][rowCount];
		map[0][0] = "/";
		map[0][1] = "[SUMS]";
		// build columns: add all words
		int currentColumn = 1; // column 0 is for blog names
		for (String word : allWordsWithCount.keySet()) {
			map[currentColumn][0] = word; // add word
			map[currentColumn][1] = allWordsWithCount.get(word); // add sum
			currentColumn++;
		}

		// build rows: go through all blogs, add their name and word counts
		int currentRow = 2; // row 0 is for word names, row 1 for sums
		for (WordsWithCountAndUrl blog : allAnalyzedFeeds) {

			map[0][currentRow] = blog.getRssFeedUrl(); // set blog name into
														// first column

			// go through all columns and search for it in the current blog
			for (int i = 1; i < columnCount; i++) {

				Object currentWord = map[i][0];
				if (blog.getWordsWithCount().containsKey(currentWord)) {
					Integer currentWordCount = blog.getWordsWithCount().get(currentWord);
					map[i][currentRow] = currentWordCount;
				} else {
					map[i][currentRow] = 0;
				}
			}
			currentRow++;
		}

		// TODO maybe add sums at the end or second line? array would need one
		// more row
		printMap(map, columnCount, rowCount);

		return map;
	}

	private static void parseAndCleanSingleFeed(String feed, FinishedCallback finishedCallback) {
		List<RssItemBean> items = RssFeedFetcher.parse(feed);

		if (items == null) {
			finishedCallback.finished(null);
			return;
		}

		List<Feed> cleanedFeeds = new ArrayList<Feed>();

		WordAnalyzer analyzer = new WordAnalyzer();
		for (int i = 0; i < items.size(); i++) {
			RssItemBean item = items.get(i);
			Feed cleaned = new Feed(clean(item.getTitle()), clean(item.getDescription()));
			cleanedFeeds.add(cleaned);

			analyzer.analyze(cleaned);
		}
		finishedCallback.finished(new WordsWithCountAndUrl(feed, analyzer.getWordsWithCount()));
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

	private static void printMap(Object[][] map, int columnCount, int rowCount) {
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < columnCount; col++) {
				System.out.print(map[col][row] + " | ");
			}
			System.out.print("\n");
		}
	}
}
