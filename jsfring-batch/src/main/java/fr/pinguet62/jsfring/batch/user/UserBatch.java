package fr.pinguet62.jsfring.batch.user;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.CompositeJobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fr.pinguet62.jsfring.model.sql.User;

/** Batch who import {@link User} from CSV file. */
@Configuration
public class UserBatch {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private UserProcessor processor;

    @Autowired
    private UserReader reader;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private UserWriter writer;

    @Bean
    public Job importUserJob() {
        // @formatter:off
        return jobBuilderFactory
                .get("importUserJob")
                .incrementer(new RunIdIncrementer())
//                .listener(listener)
//                .flow(step())
//                .end()
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