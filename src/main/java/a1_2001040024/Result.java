package a1_2001040024;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The Result class represents a document found in the search engine. Each Result object stores information about a
 * related document, a list of matches found in that document, and also three derived properties: match count, total
 * frequency, and average first index. This class implements the Comparable interface to allow results to be sorted
 * based on match count, total frequency, and average first index.
 */
public class Result implements Comparable<Result> {
    private final List<Match> matches;
    private final int matchCount;
    private final int totalFrequency;
    private final double averageFirstIndex;
    private final Doc doc;

    /**
     * Constructor to initialize a Result object with the related document and the list of matches.
     *
     * @param d       The document to which the Result object is related.
     * @param matches The list of matches found in the document.
     */
    public Result(Doc d, List<Match> matches) {
        this.doc = d;
        this.matches = matches;
        this.matchCount = matches.size();
        this.totalFrequency = matches.stream().mapToInt(Match::getFreq).sum();
        this.averageFirstIndex = matches.stream().mapToInt(Match::getFirstIndex).average().orElse(0.0);
    }

    /**
     * Returns the number of matches.
     *
     * @return The number of matches.
     */
    public int getMatchCount() {
        return this.matchCount;
    }

    /**
     * Returns the related document.
     *
     * @return The related document.
     */
    public Doc getDoc() {
        return this.doc;
    }

    /**
     * Returns the list of matches found in the document.
     *
     * @return The list of matches found in the document.
     */
    public List<Match> getMatches() {
        return this.matches;
    }

    /**
     * Returns the sum of all frequencies of the matches.
     *
     * @return The sum of all frequencies of the matches.
     */
    public int getTotalFrequency() {
        return this.totalFrequency;
    }

    /**
     * Returns the average of the first indexes of the matches.
     *
     * @return The average of the first indexes of the matches.
     */
    public double getAverageFirstIndex() {
        return this.averageFirstIndex;
    }

    /**
     * Highlights the matched words in the document using HTML markups.
     *
     * @return The HTML-formatted document with matched words highlighted.
     */
    public String htmlHighlight() {
        Set<Word> matchedWords = this.matches.stream().map(Match::getWord).collect(Collectors.toSet());
        return "<h3>" +
               this.highlightWords(this.doc.getTitle(), matchedWords, "<u>") +
               "</h3>" +
               "<p>" +
               this.highlightWords(this.doc.getBody(), matchedWords, "<b>") +
               "</p>";
    }

    /**
     * Highlights words in a list based on a set of matched words and a tag.
     *
     * @param words        the list of words to highlight
     * @param matchedWords the set of words to match against
     * @param tag          the HTML tag to wrap the matched words with
     * @return a string with highlighted words
     */
    private String highlightWords(List<Word> words, Set<Word> matchedWords, String tag) {
        return words.stream().map(word -> {
            if (matchedWords.contains(word)) {
                return word.getPrefix() + tag + word.getText() + tag.replace("<", "</") + word.getSuffix();
            } else {
                return word.toString();
            }
        }).collect(Collectors.joining(" "));
    }

    /**
     * Compares this Result object with another Result object.
     *
     * @param o The other Result object.
     * @return A negative integer, zero, or a positive integer as this object is less than, equal to, or greater than
     * the specified object.
     */
    @Override
    public int compareTo(Result o) {
        if (this.matchCount != o.matchCount) {
            // Return the result of comparing the matchCount values in descending order
            return Integer.compare(o.matchCount, this.matchCount);
        }
        if (this.totalFrequency != o.totalFrequency) {
            // Return the result of comparing the totalFrequency values in descending order
            return Integer.compare(o.totalFrequency, this.totalFrequency);
        }
        // Return the result of comparing the averageFirstIndex values
        return Double.compare(this.averageFirstIndex, o.averageFirstIndex);
    }
}
