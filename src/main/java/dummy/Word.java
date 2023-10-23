package dummy;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/**
 * The Word class represents a word from a document. A Word object consists of a prefix, text part,
 * a suffix, and a boolean flag to indicate its validity. The class also includes a static set of
 * stop words.
 */
public class Word {
    public static Set<String> stopWords;
    private String prefix, text, suffix;
    private boolean valid;


    public Word(String prefix, String text, String suffix, boolean valid) {
        this.prefix = prefix;
        this.text = text;
        this.suffix = suffix;
        this.valid = valid;
    }

    public static Word createWord(String rawText) {
        String text = rawText.replaceAll("[^a-zA-Z'-]", "");
        String[] parts = rawText.split(text, 2);
        String prefix = parts[0];
        String suffix = parts.length == 2 ? parts[1] : "";
        boolean valid = !text.isEmpty() && text.chars().anyMatch(Character::isLetter)
                && prefix.chars().noneMatch(Character::isLetterOrDigit)
                && suffix.chars().noneMatch(Character::isLetterOrDigit);
        if (!valid) {
            prefix = "";
            text = rawText;
            suffix = "";
        }
        if (text.endsWith("'s")) {
            text = text.substring(0, text.length() - 2);
            suffix = "'s" + suffix;
        }
        return new Word(prefix, text, suffix, valid);
    }


    public static boolean loadStopWords(String fileName) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))) {
            String line;
            Set<String> stopWords = new HashSet<>();
            while ((line = reader.readLine()) != null) {
                stopWords.add(line);
            }
            Word.stopWords = stopWords;
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean isKeyword() {
        return this.valid
                && (Word.stopWords == null || !Word.stopWords.contains(this.text.toLowerCase()));
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.text.toLowerCase().hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Word))
            return false;
        Word word;
        word = (Word) o;
        return this.text.toLowerCase().equals(word.text.toLowerCase());
    }

    @Override
    public String toString() {
        return this.prefix + this.text + this.suffix;
    }
}
