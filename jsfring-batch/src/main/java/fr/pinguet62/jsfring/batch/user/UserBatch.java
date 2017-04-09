package fr.pinguet62.jsfring.batch.user;

import static java.util.Objects.requireNonNull;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.CompositeJobExecutionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fr.pinguet62.jsfring.model.sql.User;

/** Batch who import {@link User} from CSV file. */
@Configuration
public class UserBatch {

    private final JobBuilderFactory jobBuilderFactory;

    private final UserProcessor processor;

    private final UserReader reader;

    public final StepBuilderFactory stepBuilderFactory;

    private final UserWriter writer;

    public UserBatch(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, UserReader reader,
            UserProcessor processor, UserWriter writer) {
        this.jobBuilderFactory = requireNonNull(jobBuilderFactory);
        this.stepBuilderFactory = requireNonNull(stepBuilderFactory);
        this.reader = requireNonNull(reader);
        this.processor = requireNonNull(processor);
        this.writer = requireNonNull(writer);
    }

    @Bean
    public Job importUserJob() {
        // @formatter:off
        return jobBuilderFactory
                .get("importUserJob")
                .incrementer(new RunIdIncrementer())
                // .listener(listener)
                // .flow(step())
                // .end()
                .start(step())
                .build();
        // @formatter:on
    }

    @Bean
    public JobExecutionListener listener() {
        return new CompositeJobExecutionListener();
    }

    @Bean
    public Step step() {
        // @formatter:off
        return stepBuilderFactory
                .get("step1")
                .<UserRow, User> chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
        // @formatter:on
    }

}