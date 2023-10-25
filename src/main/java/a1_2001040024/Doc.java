package a1_2001040024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a document which has a title and a body. The title and body of a document are lists of Word objects.
 */
public class Doc {
    private final List<Word> title;
    private final List<Word> body;

    /**
     * A constructor which receives the raw text of a document and extracts the title and body parts from that.
     * Documents are provided as text files (.txt) in the docs directory under the project’s root directory. Each text
     * file contains two lines. The first line is the title and the second line is the body.
     *
     * @param content the raw text of a document
     */
    public Doc(String content) {
        String[] lines = content.split("\\R");
        this.title = this.generateWordList(lines, 0);
        this.body = this.generateWordList(lines, 1);
    }

    /**
     * Generates a list of Word objects from the lines array at the specified index.
     *
     * @param lines the array of lines
     * @param index the index of the line to process
     * @return a list of Word objects extracted from the line at the specified index
     */
    private List<Word> generateWordList(String[] lines, int index) {
        // If the line to read exist (the index is less than the number of lines)
        if (lines.length > index) {
            // Create and return a List of Word objects by splitting the line
            return Arrays.stream(lines[index].split(" ")).map(Word::createWord).collect(Collectors.toList());
        }
        // Return an empty ArrayList
        return new ArrayList<>();
    }

    /**
     * Returns the document’s title as a list of Word objects.
     *
     * @return the document’s title as a list of Word objects
     */
    public List<Word> getTitle() {
        return this.title;
    }

    /**
     * Returns the document’s body as a list of Word objects.
     *
     * @return the document’s body as a list of Word objects
     */
    public List<Word> getBody() {
        return this.body;
    }

    /**
     * Computes the hash code of this Doc instance based on its title and body.
     *
     * @return the computed hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.title, this.body);
    }

    /**
     * Checks the equality of two Doc objects. Two Doc objects are equal if their titles and bodies contain the same
     * words in the same order. To determine if two words are equal, we use the equals() method from the Word class.
     *
     * @param o the object to compare this Doc with
     * @return true if the given object is a Doc and has the same title and body as this Doc, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Doc)) {
            return false;
        }
        Doc doc;
        doc = (Doc) o;
        return this.title.equals(doc.title) && this.body.equals(doc.body);
    }
}
