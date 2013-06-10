package ac.at.fhsalzburg.semantic.parser;

import java.util.HashMap;
import java.util.Map;

import ac.at.fhsalzburg.semantic.data.Feed;

public class WordAnalyzer {

	private Map<String, Integer> wordsWithCount = new HashMap<String, Integer>();

	public void analyze(Feed cleanFeed) {

		String title = cleanFeed.getTitle().trim().toLowerCase();
		String desc = cleanFeed.getDescription().trim().toLowerCase();

		String[] titleWords = title.split("\\s");
		String[] descWords = desc.split("\\s");

		for (int i = 0; i < titleWords.length; i++) {
			addWord(titleWords[i]);
		}
		for (int i = 0; i < descWords.length; i++) {
			addWord(descWords[i]);
		}
	}

	private void addWord(String word) {

		if (word.trim().isEmpty()) {
			return;
		}

		if (wordsWithCount.containsKey(word)) {
			Integer oldCount = wordsWithCount.get(word);
			wordsWithCount.remove(oldCount);
			wordsWithCount.put(word, oldCount + 1);
		} else {
			wordsWithCount.put(word, 1);
		}
	}

	public Map<String, Integer> getWordsWithCount() {
		return wordsWithCount;
	}

}
