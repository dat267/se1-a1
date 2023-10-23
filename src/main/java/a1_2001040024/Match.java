package a1_2001040024;

/**
 * Represents a match between a document and a word in a search query. Each Match object stores information about the
 * document, word, frequency, and first index of the match. Implements the Comparable interface to enable sorting
 * matches based on the first index.
 */
public class Match implements Comparable<Match> {
    private final int freq;
    private final int firstIndex;
    private final Doc doc;
    private final Word word;

    /**
     * Constructs a Match object with the provided document, word, frequency of the word in the document, and the first
     * position of the word in the document.
     *
     * @param d          the document in which the match was found
     * @param w          the word that was matched
     * @param freq       the frequency of the match
     * @param firstIndex the first index of the match
     */
    public Match(Doc d, Word w, int freq, int firstIndex) {
        this.doc = d;
        this.word = w;
        this.freq = freq;
        this.firstIndex = firstIndex;
    }

    /**
     * Returns the document in which the match was found.
     *
     * @return the document
     */
    public Doc getDoc() {
        return this.doc;
    }

    /**
     * Returns the word that was matched.
     *
     * @return the word
     */
    public Word getWord() {
        return this.word;
    }

    /**
     * Returns the frequency of the match.
     *
     * @return the frequency
     */
    public int getFreq() {
        return this.freq;
    }

    /**
     * Returns the first index of the match.
     *
     * @return the first index
     */
    public int getFirstIndex() {
        return this.firstIndex;
    }

    /**
     * Compares this Match with another Match object by the first index. This method obeys the standard behavior
     * specified by Java. Match object A is greater than Match object B if the first index of A is greater than the
     * first index of B.
     *
     * @param o the other Match object
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than
     * the specified object
     */
    @Override
    public int compareTo(Match o) {
        return Integer.compare(this.firstIndex, o.firstIndex);
    }
}
