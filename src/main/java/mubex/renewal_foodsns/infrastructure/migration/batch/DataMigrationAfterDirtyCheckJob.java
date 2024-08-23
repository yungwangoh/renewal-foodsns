package mubex.renewal_foodsns.infrastructure.migration.batch;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mubex.renewal_foodsns.domain.dto.response.PostResponse;
import mubex.renewal_foodsns.infrastructure.migration.batch.item.reader.DirtyCheckItemReader;
import mubex.renewal_foodsns.infrastructure.migration.batch.item.writer.DirtyCheckItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataMigrationAfterDirtyCheckJob {

    private final PlatformTransactionManager tm;
    private final Cache<Object, Object> cache;
    private final ElasticsearchOperations elasticsearchOperations;

    private static final String JOB_NAME = "dataMigrationAfterDirtyCheckJob";
    private static final String STEP_NAME = "dataMigrationAfterDirtyCheckStep";

    private static final int CHUNK_SIZE = 10;

    @Bean
    public Job dirtyCheckJob(final JobRepository jobRepository) {
        return new JobBuilder(JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(dirtyCheckStep(jobRepository))
                .build();
    }

    @Bean
    @JobScope
    public Step dirtyCheckStep(final JobRepository jobRepository) {
        return new StepBuilder(STEP_NAME, jobRepository)
                .<PostResponse, PostResponse>chunk(CHUNK_SIZE, tm)
                .reader(dirtyCheckItemReader())
                .writer(dirtyCheckItemWriter())
                .build();
    }

    @Bean
    @StepScope
    public DirtyCheckItemReader dirtyCheckItemReader() {
        return new DirtyCheckItemReader(cache);
    }

    @Bean
    @StepScope
    public DirtyCheckItemWriter dirtyCheckItemWriter() {
        return new DirtyCheckItemWriter(elasticsearchOperations);
    }
}
