package a1_2001040024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * This class represents the search engine.
 */
public class Engine {
    private final List<Doc> docs;

    /**
     * Constructs an Engine with an empty list of documents.
     */
    public Engine() {
        this.docs = new ArrayList<>();
    }

    /**
     * Loads the documents from the specified directory and returns the number of documents loaded.
     *
     * @param dirname The name of the directory.
     * @return The number of documents loaded.
     */
    public int loadDocs(String dirname) {
        if (dirname.isEmpty()) {
            return 0;
        }
        try (Stream<Path> paths = Files.list(Paths.get(dirname))) {
            paths.filter(Files::isRegularFile)
                 .sorted()
                 .filter(path -> path.toString().endsWith(".txt"))
                 .forEach(this::addDoc);
            return this.docs.size();
        } catch (IOException e) {
            // System.out.println("An error occurred while loading documents: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Adds a document to the list of documents.
     *
     * @param path The path of the document.
     */
    private void addDoc(Path path) {
        String content;
        try {
            content = new String(Files.readAllBytes(path));
        } catch (IOException e) {
            // System.out.println("An error occurred while adding document: " + e.getMessage());
            return;
        }
        this.docs.add(new Doc(content));
    }

    /**
     * Returns an array of documents in the original order.
     *
     * @return An array of documents.
     */
    public Doc[] getDocs() {
        return this.docs.toArray(new Doc[0]);
    }

    /**
     * Performs the search function of the engine. Returns a list of sorted search results.
     *
     * @param q The query.
     * @return A list of sorted search results.
     */
    public List<Result> search(Query q) {
        List<Result> results = new ArrayList<>();
        for (Doc doc : this.docs) {
            List<Match> matches = q.matchAgainst(doc);
            if (!matches.isEmpty()) {
                results.add(new Result(doc, matches));
            }
        }
        Collections.sort(results);
        return results;
    }

    /**
     * Converts a list of search results into HTML format.
     *
     * @param results The list of search results.
     * @return The search results in HTML format.
     */
    public String htmlResult(List<Result> results) {
        StringBuilder sb = new StringBuilder();
        for (Result result : results) {
            sb.append(result.htmlHighlight());
        }
        return sb.toString();
    }
}
