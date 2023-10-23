package dummy;

public class Match implements Comparable<Match> {
    private int freq;
    private int firstIndex;
    private Doc doc;
    private Word word;

    public Match(Doc d, Word w, int freq, int firstIndex) {
        this.doc = d;
        this.word = w;
        this.freq = freq;
        this.firstIndex = firstIndex;
    }

    public Doc getDoc() {
        return this.doc;
    }

    public Word getWord() {
        return this.word;
    }

    public int getFreq() {
        return this.freq;
    }

    public int getFirstIndex() {
        return this.firstIndex;
    }

    @Override
    public int compareTo(Match o) {
        if (this.firstIndex < o.firstIndex) {
            return -1;
        } else if (this.firstIndex > o.firstIndex) {
            return 1;
        } else {
            return 0;
        }
    }

}
