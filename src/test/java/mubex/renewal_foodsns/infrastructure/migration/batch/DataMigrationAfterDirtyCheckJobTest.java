package mubex.renewal_foodsns.infrastructure.migration.batch;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.benmanes.caffeine.cache.Cache;
import java.util.List;
import mubex.renewal_foodsns.common.config.cache.CacheConfig;
import mubex.renewal_foodsns.domain.dto.response.PostResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {
        TestBatchConfig.class,
        DataMigrationAfterDirtyCheckJob.class,
        Cache.class,
        CacheConfig.class,
})
@SpringBatchTest
@DisplayNameGeneration(ReplaceUnderscores.class)
class DataMigrationAfterDirtyCheckJobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private Cache<Object, Object> cache;

    @BeforeEach
    void setUp() {
        PostResponse postResponse = PostResponse.builder()
                .memberResponse(null)
                .postImageResponses(List.of())
                .heart(1)
                .id(1)
                .report(1)
                .text("gdgdgdgd")
                .title("Gdgdgdgdgdgdgd")
                .views(1)
                .visible(true)
                .build();

        cache.put(1L, postResponse);
    }

    @AfterEach
    void clear() {
        cache.cleanUp();
    }

    @Test
    void 더티_체크_캐싱된_데이터를_배치를_돌려_엘라스틱에_저장한다() throws Exception {
        // given

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        // then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
    }
}