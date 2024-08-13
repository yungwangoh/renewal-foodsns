package mubex.renewal_foodsns.infrastructure.persistance.generator;

import static net.datafaker.transformations.Field.field;

import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;
import net.datafaker.Faker;
import net.datafaker.transformations.CsvTransformer;
import net.datafaker.transformations.Schema;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@Nested
@SpringBootTest
@DisplayNameGeneration(ReplaceUnderscores.class)
class CsvGeneratorTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final int CHUNK = 50_000;
    private static final int END = 200;

    @Test
    void csv_파일을_생성한다() {

        List<CompletableFuture<Integer>> futures = IntStream.range(0, END)
                .mapToObj(idx -> CompletableFuture.supplyAsync(() -> {
                    List<String> strings = getCsv(idx);

                    CsvGenerator.builder(strings)
                            .path("/Users/yungwang-o/Documents/data/posts.csv")
                            .openOptions(StandardOpenOption.CREATE, StandardOpenOption.APPEND)
                            .build();

                    System.out.println(idx + "/" + END);
                    return idx;
                })).toList();

        futures.forEach(CompletableFuture::join);
    }

    private List<String> getCsv(final int start) {
        Faker faker = new Faker(Locale.KOREA);
        return IntStream.range(start * CHUNK + 1, start * CHUNK + CHUNK + 1)
                .mapToObj(idx -> {
                    Schema<Object, Object> schema = Schema.of(
                            field("id", () -> idx),
                            field("created_at", () -> LocalDateTime.now()),
                            field("updated_at", () -> LocalDateTime.now()),
                            field("title", () -> faker.lorem().sentence(10, 20)),
                            field("text", () -> faker.lorem().sentence(10, 20)),
                            field("thumbnail", () -> faker.lorem().sentence(1, 5)),
                            field("heart", () -> faker.random().nextLong(0, 10000)),
                            field("report", () -> faker.random().nextInt(0, 10000)),
                            field("view", () -> faker.random().nextLong(0, 100000)),
                            field("in_deleted", () -> faker.random().nextBoolean()),
                            field("member_id", () -> faker.random().nextLong(1, 10)),
                            field("version", () -> faker.random().nextLong(0, 0))
                    );

                    CsvTransformer<Object> transformer = CsvTransformer.builder()
                            .header(false)
                            .separator(",")
                            .build();

                    return transformer.generate(schema, 1);
                }).toList();
    }

    @Test
    void csv_파일을_DB에_Insert한다() {
        insert();
    }

    private void insert() {
        String sql = "LOAD DATA LOCAL INFILE ? " +
                "INTO TABLE post " +
                "FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\"' " +
                "LINES TERMINATED BY '\n'";

        String filePath = "/Users/yungwang-o/Documents/data/posts.csv";

        jdbcTemplate.update(sql, filePath);
    }
}