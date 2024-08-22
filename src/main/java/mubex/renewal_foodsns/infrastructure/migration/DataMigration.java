package mubex.renewal_foodsns.infrastructure.migration;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataMigration {

    private final Job dataMigrationJob;
    private final JobLauncher jobLauncher;

    @Scheduled(cron = "0 0 * * * ?")
    public void doScheduledMigration() {
        try {
            jobLauncher.run(dataMigrationJob, new JobParameters());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
