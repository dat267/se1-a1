package dummy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Result implements Comparable<Result> {
    private List<Match> matches;
    private int matchCount;
    private int totalFrequency;
    private double averageFirstIndex;
    private Doc doc;

    public Result(Doc d, List<Match> matches) {
        this.doc = d;
        this.matches = matches;
        this.matchCount = matches.size();
        int totalFreq = 0;
        double avgFirstIndex = 0.0;
        for (Match match : matches) {
            totalFreq += match.getFreq();
            avgFirstIndex += match.getFirstIndex();
        }
        this.totalFrequency = totalFreq;
        this.averageFirstIndex = avgFirstIndex / matches.size();
    }

    public int getMatchCount() {
        return this.matchCount;
    }

    public Doc getDoc() {
        return this.doc;
    }

    public List<Match> getMatches() {
        return this.matches;
    }

    public int getTotalFrequency() {
        return this.totalFrequency;
    }

    public double getAverageFirstIndex() {
        return this.averageFirstIndex;
    }

    public String htmlHighlight() {
        Set<Word> matchedWords = new HashSet<>();
        for (Match match : this.matches) {
            matchedWords.add(match.getWord());
        }
        StringBuilder highlightedTitle = new StringBuilder();
        highlightedTitle.append("<h3>");
        for (Word word : this.doc.getTitle()) {
            if (matchedWords.contains(word)) {
                highlightedTitle.append(word.getPrefix()).append("<u>").append(word.getText())
                        .append("</u>").append(word.getSuffix());
            } else {
                highlightedTitle.append(word.toString());
            }
            highlightedTitle.append(" ");
        }
        highlightedTitle.deleteCharAt(highlightedTitle.length() - 1);
        highlightedTitle.append("</h3>");

        StringBuilder highlightedBody = new StringBuilder();
        highlightedBody.append("<p>");
        for (Word word : this.doc.getBody()) {
            if (matchedWords.contains(word)) {
                highlightedBody.append(word.getPrefix()).append("<b>").append(word.getText())
                        .append("</b>").append(word.getSuffix());
            } else {
                highlightedBody.append(word.toString());
            }
            highlightedBody.append(" ");
        }
        highlightedBody.deleteCharAt(highlightedBody.length() - 1);
        highlightedBody.append("</p>");

        return highlightedTitle.toString() + highlightedBody.toString();
    }



    @Override
    public int compareTo(Result o) {
        if (this.matchCount < o.matchCount) {
            return 1;
        } else if (this.matchCount > o.matchCount) {
            return -1;
        } else {
            if (this.totalFrequency < o.totalFrequency) {
                return 1;
            } else if (this.totalFrequency > o.totalFrequency) {
                return -1;
            } else {
                return Double.compare(this.averageFirstIndex, o.averageFirstIndex);
            }
        }
    }

}
