package mubex.renewal_foodsns.infrastructure.persistance.generator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class CsvGenerator {

    private final List<String> csv;
    private Path path;
    private StandardOpenOption[] openOptions;

    private CsvGenerator(final List<String> csv) {
        this.csv = csv;
    }

    public static CsvGenerator builder(final List<String> csv) {
        return new CsvGenerator(csv);
    }

    public CsvGenerator path(final String path) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("path cannot be null or empty");
        }

        this.path = Paths.get(path);
        return this;
    }

    public CsvGenerator openOptions(final StandardOpenOption... openOptions) {
        this.openOptions = openOptions;
        return this;
    }

    public void build() {
        csv.forEach(this::write);

        System.out.println("list size = " + csv.size() + " complete.");
    }

    private void write(final String csv) {

        try {
            Files.write(path, (csv + System.lineSeparator()).getBytes(), openOptions);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
