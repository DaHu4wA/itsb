package ac.at.fhsalzburg.semantic.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import ac.at.fhsalzburg.semantic.data.WordsWithCountAndUrl;

public class AnalyzedWordMatcher {

	Map<String, Integer> overallWordsWithCount = new HashMap<String, Integer>();

	public void matchWordLists(List<WordsWithCountAndUrl> allAnalyzed) {

		for (WordsWithCountAndUrl analyzed : allAnalyzed) {

			System.out.println(analyzed);

			Set<Entry<String, Integer>> entrySet = analyzed.getWordsWithCount().entrySet();

			for (Entry<String, Integer> entry : entrySet) {
				addWord(entry.getKey(), entry.getValue());
			}
		}
		printResult();
	}

	private void addWord(String word, int count) {

		if (overallWordsWithCount.containsKey(word)) {
			Integer oldCount = overallWordsWithCount.get(word);
			overallWordsWithCount.remove(oldCount);
			overallWordsWithCount.put(word, oldCount + count);
		} else {
			overallWordsWithCount.put(word, count);
		}
	}

	public Map<String, Integer> getoverallWordsWithCount() {
		return overallWordsWithCount;
	}

	private void printResult() {
		System.out.println("-------------------------------");
		System.out.println("\nAnalyzed words:");
		for (String s : overallWordsWithCount.keySet()) {
			if (s.length() < 6) {
				System.out.println(s + ": \t\t" + overallWordsWithCount.get(s));
			} else {
				System.out.println(s + ": \t" + overallWordsWithCount.get(s));
			}
		}
		System.out.println("-------------------------------");
		System.out.println(overallWordsWithCount.size() + " different words analyzed.");
	}

}
