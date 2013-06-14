package ac.at.fhsalzburg.semantic.parser;

import ac.at.fhsalzburg.semantic.data.WordsWithCountAndUrl;

public interface FinishedCallback {

	public void finished(WordsWithCountAndUrl result);
	
}
