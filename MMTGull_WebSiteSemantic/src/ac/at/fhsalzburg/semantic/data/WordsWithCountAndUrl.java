package ac.at.fhsalzburg.semantic.data;

import java.util.Map;

public class WordsWithCountAndUrl {

	final String rssFeedUrl;
	final Map<String, Integer> wordsWithCount;

	public WordsWithCountAndUrl(String rssFeedUrl, Map<String, Integer> wordsWithCount) {
		this.rssFeedUrl = rssFeedUrl;
		this.wordsWithCount = wordsWithCount;
	}

	public String getRssFeedUrl() {
		return rssFeedUrl;
	}

	public Map<String, Integer> getWordsWithCount() {
		return wordsWithCount;
	}

	@Override
	public String toString() {
		return "RSS \"" + rssFeedUrl + "\" (contains " + wordsWithCount.size() + " different words)";
	}
}
