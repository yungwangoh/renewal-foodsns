package mubex.renewal_foodsns.application.job;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.job.item.writer.DataMigrationItemWriter;
import mubex.renewal_foodsns.domain.document.PostDocument;
import mubex.renewal_foodsns.domain.entity.Post;
import mubex.renewal_foodsns.infrastructure.persistance.generator.SqlDSL;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class DataMigrationJob {

    private final ElasticsearchOperations elasticsearchOperations;
    private final PlatformTransactionManager tm;
    private final DataSource dataSource;

    private static final String JOB_NAME = "dataMigrationJob";
    private static final String STEP_NAME = "dataMigrationStep";

    private static final int CHUNK_SIZE = 1000;
    private static final int FETCH_SIZE = 10000;

    @Bean
    public Job migrationJob(JobRepository jobRepository) {
        return new JobBuilder(JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(migrationStep(jobRepository))
                .build();
    }

    @Bean
    public Step migrationStep(JobRepository jobRepository) {
        return new StepBuilder(STEP_NAME, jobRepository)
                .<PostDocument, PostDocument>chunk(CHUNK_SIZE, tm)
                .reader(this.itemReader())
                .writer(this.itemWriter())
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<PostDocument> itemReader() {

        String query = SqlDSL.generator(Post.class)
                .selectFrom("id", "title", "text", "heart", "views", "in_deleted")
                .getSql();

        return new JdbcCursorItemReaderBuilder<PostDocument>()
                .name("itemReader")
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(PostDocument.class))
                .sql(query)
                .fetchSize(FETCH_SIZE)
                .build();
    }

    @Bean
    @StepScope
    public ItemWriter<PostDocument> itemWriter() {
        return new DataMigrationItemWriter(elasticsearchOperations, "post");
    }
}
