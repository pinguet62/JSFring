package fr.pinguet62.jsfring.batch.admin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.admin.web.JobInfo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.DuplicateJobException;
import org.springframework.batch.core.configuration.JobFactory;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.AutomaticJobRegistrar;
import org.springframework.batch.core.configuration.support.ReferenceJobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * {@link JobRegistry#register(JobFactory) Registar} all {@link Job}s.
 * <p>
 * By default, only {@link Job}s declared by XML files on {@code /META-INF/spring/batch/jobs/} folder are automatically
 * registered with {@link AutomaticJobRegistrar}. Java configured {@link Job}s are not automatically registered.<br>
 * <p>
 * Permit to activate {@link JobInfo#isLaunchable()} for <b>all</b> {@link Job}s of classpath.<br>
 * To activate {@link JobInfo#isLaunchable() launchable} jobs case by case, it's necessary to use other solution: activate
 * {@link EnableBatchProcessing#modular()} and define {@link ApplicationContextFactory} for each {@link JobInfo#isLaunchable()
 * launchable job}.
 *
 * @see JobRegistry#register(JobFactory)
 * @see AutomaticJobRegistrar
 */
@Slf4j
@Configuration
public class AutoJobRegistry {

    @Autowired
    private JobRegistry jobRegistry;

    @Autowired
    private List<Job> jobs;

    /**
     * Execute initializer.
     */
    @PostConstruct
    private void init() {
        for (Job job : jobs) {
            ReferenceJobFactory jobFactory = new ReferenceJobFactory(job);
            try {
                jobRegistry.register(jobFactory);
            } catch (DuplicateJobException e) {
                log.error("Error registering job: " + job.getName(), e);
            }
        }
    }

}