package a1_2001040024;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class represents a user's search query. A Query object stores a list of keywords internally.
 */
public class Query {
    private final List<Word> keywords;

    /**
     * Constructs a Query object with the given search phrase. The search phrase is split into words, and only those
     * that are not stop words are added to the keywords list.
     *
     * @param searchPhrase The raw search phrase from the user.
     */
    public Query(String searchPhrase) {
        this.keywords = Arrays.stream(searchPhrase.split(" "))
                              .map(Word::createWord)
                              .filter(Word::isKeyword)
                              .collect(Collectors.toList());
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
     * Returns a list of matches against the given document. The matches are sorted by the position where the keyword
     * first appears in the document.
     *
     * @param d The document to match against.
     * @return A list of Match objects representing the matches of the query against the document.
     */
    public List<Match> matchAgainst(Doc d) {
        List<Word> docWords = Stream.concat(d.getTitle().stream(), d.getBody().stream()).collect(Collectors.toList());
        return keywords.stream().map(keyword -> {
            int freq = Collections.frequency(docWords, keyword);
            return freq > 0 ? new Match(d, keyword, freq, docWords.indexOf(keyword)) : null;
        }).filter(Objects::nonNull).sorted().collect(Collectors.toList());
    }
}
