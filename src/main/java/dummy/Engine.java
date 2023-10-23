package dummy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class Engine {
    private List<Doc> docs;

    public Engine() {
        this.docs = new ArrayList<>();
    }

    public int loadDocs(String dirname) {
        if (Objects.equals(dirname, "")) {
            return 0;
        }
        File directory = new File(dirname);
        if (!directory.exists() || !directory.isDirectory()) {
            return 0;
        }
        File[] files = directory.listFiles();
        Arrays.sort(files, Comparator.comparing(File::getName));
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                String content;
                try {
                    content = new String(Files.readAllBytes(file.toPath()));
                } catch (IOException e) {
                    continue;
                }
                this.docs.add(new Doc(content));
            }
        }
        return this.docs.size();
    }


    public Doc[] getDocs() {
        return this.docs.toArray(new Doc[0]);
    }

    public List<Result> search(Query q) {
        List<Result> results = new ArrayList<>();
        int size = this.docs.size();
        for (int i = 0; i < size; i++) {
            Doc doc = this.docs.get(i);
            List<Match> matches = q.matchAgainst(doc);
            if (!matches.isEmpty()) {
                results.add(new Result(doc, matches));
            }
        }
        Collections.sort(results);
        return results;
    }

    public String htmlResult(List<Result> results) {
        StringJoiner sj = new StringJoiner("");
        for (Result result : results) {
            sj.add(result.htmlHighlight());
        }
        return sj.toString();
    }

}
