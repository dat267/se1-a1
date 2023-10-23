package dummy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Query {
    private List<Word> keywords;

    public Query(String searchPhrase) {
        this.keywords = new ArrayList<>();
        String[] words = searchPhrase.split(" ");
        for (String word : words) {
            Word w = Word.createWord(word);
            if (w.isKeyword()) {
                this.keywords.add(w);
            }
        }
    }

    public List<Word> getKeywords() {
        return this.keywords;
    }

    public List<Match> matchAgainst(Doc d) {
        List<Match> matches = new ArrayList<>();
        List<Word> docWords = new ArrayList<>();
        docWords.addAll(d.getTitle());
        docWords.addAll(d.getBody());
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
