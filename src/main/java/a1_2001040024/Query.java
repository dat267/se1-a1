package a1_2001040024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class represents a user's search query. A Query object stores a list of keywords internally.
 */
public class Query {
	private final List<Word> keywords;

	/**
	 * Constructs a Query object with the given search phrase.
	 * The search phrase is split into words, and only those that are not stop words are added to the keywords list.
	 *
	 * @param searchPhrase The raw search phrase from the user.
	 */
	public Query(String searchPhrase) {
		this.keywords = Arrays.stream(searchPhrase.split(" ")).map(Word::createWord)
				.filter(Word::isKeyword).collect(Collectors.toList());
	}

	/**
	 * Returns a list of the query's keywords in the same order as they appear in the raw search phrase.
	 *
	 * @return A list of Word objects representing the keywords of the query.
	 */
	public List<Word> getKeywords() {
		return this.keywords;
	}

	/**
	 * Returns a list of matches against the given document.
	 * The matches are sorted by the position where the keyword first appears in the document.
	 *
	 * @param d The document to match against.
	 * @return A list of Match objects representing the matches of the query against the document.
	 */
	public List<Match> matchAgainst(Doc d) {
		List<Match> matches = new ArrayList<>();
		List<Word> docWords;
		docWords = Stream.concat(d.getTitle().stream(), d.getBody().stream())
				.collect(Collectors.toList());
		for (Word keyword : this.keywords) {
			int freq = Collections.frequency(docWords, keyword);
			if (freq > 0) {
				matches.add(new Match(d, keyword, freq, docWords.indexOf(keyword)));
			}
		}
		matches.sort(Comparator.naturalOrder());
		return matches;
	}
}
