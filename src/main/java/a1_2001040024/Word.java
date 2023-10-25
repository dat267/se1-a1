package a1_2001040024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The Word class represents a word from a document. A Word object consists of a prefix, text part, a suffix, and a
 * boolean flag to indicate its validity. The class also includes a static set of stop words.
 */
public class Word {
    public static Set<String> stopWords;
    private final String prefix, text, suffix;
    private final boolean valid;

    /**
     * Constructs a new Word object with the given prefix, text part, suffix, and validity flag.
     *
     * @param prefix the prefix part of the word.
     * @param text   the text part of the word.
     * @param suffix the suffix part of the word.
     * @param valid  the validity flag of the word.
     */
    public Word(String prefix, String text, String suffix, boolean valid) {
        this.prefix = prefix;
        this.text = text;
        this.suffix = suffix;
        this.valid = valid;
    }

    /**
     * Constructs and returns a new Word object from the given raw text.
     *
     * @param rawText the raw text to create the Word object from.
     * @return the created Word object.
     */
    public static Word createWord(String rawText) {
        String prefix, text, suffix;
        boolean valid;
        // Let text part be all letters, hyphens, apostrophes in rawText
        text = rawText.replaceAll("[^\\p{L}'-]", "");
        // Split rawText into two parts using text in the middle
        String[] parts = rawText.split(text, 2);
        // In case split has no match, it returns a 1 element array with the original rawText, so
        // prefix become the same as rawText
        prefix = parts[0];
        // If parts has two elements, assign the second element to suffix, otherwise set empty
        // string as suffix
        suffix = parts.length == 2 ? parts[1] : "";
        // Word is valid if
        valid = text.matches(".*\\p{L}.*") // Text part has at least 1 letter
                && !prefix.matches(".*\\p{Alnum}.*") // Prefix doesn't contain alphanumeric
                && !suffix.matches(".*\\p{Alnum}.*"); // Suffix doesn't contain alphanumeric
        // Detect text ending with 's and set suffix
        if (text.endsWith("'s")) {
            text = text.substring(0, text.length() - 2);
            suffix = "'s" + suffix;
        }
        // If text part becomes empty, word is invalid
        if (text.isEmpty()) {
            valid = false;
        }
        // Set empty suffix and prefix, text as rawText when word is invalid
        if (!valid) {
            prefix = suffix = "";
            text = rawText;
        }
        return new Word(prefix, text, suffix, valid);
    }

    /**
     * Loads a set of stop words from the given file.
     *
     * @param fileName the name of the file that contains the stop words.
     * @return true if the stop words are successfully loaded, false otherwise.
     */
    public static boolean loadStopWords(String fileName) {
        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            Word.stopWords = lines.collect(Collectors.toSet());
            return true;
        } catch (IOException e) {
            // System.out.println("An error occurred while loading stop words: " + e.getMessage());
            return false;
        }
    }

    /**
     * Determines if the Word object is a keyword.
     *
     * @return true if the Word object is a keyword, false otherwise.
     */
    public boolean isKeyword() {
        if (this.valid) {
            return Word.stopWords == null || !Word.stopWords.contains(this.text.toLowerCase());
        } else {
            return false;
        }
    }

    /**
     * Returns the prefix part of the word.
     *
     * @return the prefix part of the word.
     */
    public String getPrefix() {
        return this.prefix;
    }

    /**
     * Returns the suffix part of the word.
     *
     * @return the suffix part of the word.
     */
    public String getSuffix() {
        return this.suffix;
    }

    /**
     * Returns the text part of the word.
     *
     * @return the text part of the word.
     */
    public String getText() {
        return this.text;
    }

    /**
     * Determines if the Word object is valid.
     *
     * @return true if the Word object is valid, false otherwise.
     */
    public boolean isValid() {
        return this.valid;
    }

    /**
     * Computes the hash code for this Word instance. The hash code is computed based on the text part of the Word,
     * case-insensitively. This means that two Word instances with the same text part but different cases will have the
     * same hash code.
     *
     * @return the computed hash code.
     */
    @Override
    public int hashCode() {
        return this.text.toLowerCase().hashCode();
    }

    /**
     * Compares the current Word object with the specified object for equality.
     *
     * @param o the object to be compared for equality with this Word.
     * @return true if the specified object is equal to this Word.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Word)) {
            return false;
        }
        Word word;
        word = (Word) o;
        return this.text.equalsIgnoreCase(word.text);
    }

    /**
     * Returns a string representation of the Word object.
     *
     * @return a string representation of the Word object.
     */
    @Override
    public String toString() {
        return this.prefix + this.text + this.suffix;
    }
}
