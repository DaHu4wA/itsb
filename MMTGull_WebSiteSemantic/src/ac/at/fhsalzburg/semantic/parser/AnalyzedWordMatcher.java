package ac.at.fhsalzburg.semantic.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import ac.at.fhsalzburg.semantic.data.WordsWithCountAndUrl;

public class AnalyzedWordMatcher {

	private static final double LOWER_OCCUR_LIMIT = 0.05;
	private static final double UPPER_OCCUR_LIMIT = 0.5;

	Map<String, Integer> overallWordsWithCount = new HashMap<String, Integer>();

	public void matchWordLists(List<WordsWithCountAndUrl> allAnalyzed) {

		for (WordsWithCountAndUrl analyzed : allAnalyzed) {
			System.out.println(analyzed);

			Set<Entry<String, Integer>> entrySet = analyzed.getWordsWithCount().entrySet();
			for (Entry<String, Integer> entry : entrySet) {
				addWord(entry.getKey(), entry.getValue());
			}
		}
		cutIrrelevantWords(allAnalyzed);

		// printWords();
	}

	private void cutIrrelevantWords(List<WordsWithCountAndUrl> allAnalyzed) {
		//cut irrelevant from the big list
		Set<String> removedWords = cutUpperAndLowerResult(allAnalyzed); 
		System.out.println("\n"+removedWords.size()+" words will be filtered out.");

		// then delete them from the single lists
		for (WordsWithCountAndUrl analyzed : allAnalyzed) {
			for (String removed : removedWords) {
				if (analyzed.getWordsWithCount().containsKey(removed)) {
					analyzed.getWordsWithCount().remove(removed);
				}
			}
		}
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

	/**
	 * Filter out all lower and upper defined values
	 * 
	 * @return the removed words
	 */
	// private Set<String> cutUpperAndLowerResult() {
	// Set<String> removedWords = new HashSet<String>();
	// double maxCount = 0;
	// for (Integer i : overallWordsWithCount.values()) {
	// if (i > maxCount) {
	// maxCount = i;
	// }
	// }
	//
	// double lowestAllowed = maxCount * LOWER_OCCUR_LIMIT;
	// double highestAllowed = maxCount * UPPER_OCCUR_LIMIT;
	//
	// Map<String, Integer> copy = new HashMap<String,
	// Integer>(overallWordsWithCount);
	// for (String i : overallWordsWithCount.keySet()) {
	// int current = overallWordsWithCount.get(i);
	// if (current > highestAllowed || current < lowestAllowed) {
	// copy.remove(i);
	// removedWords.add(i);
	// }
	// }
	// overallWordsWithCount = copy;
	// System.out.println("\n-------------------------------");
	// System.out.println("Words that occur less then " + (int) lowestAllowed +
	// " times or more than " + (int) highestAllowed
	// + " times were filtered out.");
	// return removedWords;
	// }

	private Set<String> cutUpperAndLowerResult(List<WordsWithCountAndUrl> allAnalyzed) {
		Set<String> removedWords = new HashSet<String>();

		// count in how many blogs which words occur
		Map<String, Integer> occurences = new HashMap<String, Integer>();

		for (String i : overallWordsWithCount.keySet()) {
			for (WordsWithCountAndUrl feed : allAnalyzed) {
				// look if the blogs contian this word
				if (feed.getWordsWithCount().containsKey(i)) {
					if (occurences.containsKey(i)) {
						Integer count = occurences.get(i);
						occurences.remove(i);
						occurences.put(i, count + 1);
					} else {
						occurences.put(i, 1);
					}
				}
			}
		}

		double lowestAllowedCount = allAnalyzed.size() * LOWER_OCCUR_LIMIT;
		double highestAllowedCount = allAnalyzed.size() * UPPER_OCCUR_LIMIT;

		for (String i : occurences.keySet()) {
			int current = occurences.get(i);
			if (current > highestAllowedCount || current < lowestAllowedCount) {
				overallWordsWithCount.remove(i);
				removedWords.add(i);
			}
		}
		System.out.println("\n-------------------------------");
		System.out.println("Words that occur in less then " + (int) lowestAllowedCount + " blogs or in more than "
				+ (int) highestAllowedCount + " blogs were filtered out.");
		return removedWords;
	}

	// private void printWords() {
	// System.out.println("-------------------------------");
	// System.out.println("\nHere are the most relevant, filtered words:");
	// System.out.println("__________________________________");
	// System.out.println("|     WORD\t|     COUNT \t |");
	// System.out.println("----------------------------------");
	// for (String s : overallWordsWithCount.keySet()) {
	// System.out.println("| " + s + "  \t|\t" + overallWordsWithCount.get(s) +
	// "\t |");
	// }
	// System.out.println("----------------------------------");
	// System.out.println("| " + overallWordsWithCount.size() +
	// " words\t|\t\t |\n");
	// }
}
