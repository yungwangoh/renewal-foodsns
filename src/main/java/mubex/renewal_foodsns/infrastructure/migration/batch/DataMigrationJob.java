package mubex.renewal_foodsns.infrastructure.migration.batch;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mubex.renewal_foodsns.domain.document.PostDocument;
import mubex.renewal_foodsns.infrastructure.migration.batch.item.writer.DataMigrationItemWriter;
import mubex.renewal_foodsns.infrastructure.migration.batch.mapper.DataMigrationRowMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataMigrationJob {

    private final ElasticsearchOperations elasticsearchOperations;
    private final PlatformTransactionManager tm;
    private final DataSource dataSource;

    private static final String JOB_NAME = "dataMigrationJob";
    private static final String STEP_NAME = "dataMigrationStep";

    private static final int CHUNK_SIZE = 1000;

    @Bean
    public Job migrationJob(final JobRepository jobRepository) {
        return new JobBuilder(JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(migrationStep(jobRepository))
                .build();
    }

    @Bean
    @JobScope
    public Step migrationStep(final JobRepository jobRepository) {
        return new StepBuilder(STEP_NAME, jobRepository)
                .<PostDocument, PostDocument>chunk(CHUNK_SIZE, tm)
                .reader(itemReader())
                .writer(itemWriter())
                .build();
    }

    @Bean
    @StepScope
    public JdbcPagingItemReader<PostDocument> itemReader() {
        Map<String, Order> map = new HashMap<>(1);
        map.put("id", Order.ASCENDING);

        return new JdbcPagingItemReaderBuilder<PostDocument>()
                .name("itemReader")
                .rowMapper(new DataMigrationRowMapper())
                .dataSource(dataSource)
                .selectClause("id, title, text, heart, views, in_deleted")
                .fromClause("post")
                .sortKeys(map)
                .pageSize(CHUNK_SIZE)
                .fetchSize(CHUNK_SIZE)
                .build();
    }

    @Bean
    @StepScope
    public DataMigrationItemWriter itemWriter() {
        return new DataMigrationItemWriter(elasticsearchOperations);
    }
}
