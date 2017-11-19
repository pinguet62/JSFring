package fr.pinguet62.jsfring.batch.admin.config;

import org.springframework.batch.core.configuration.annotation.AbstractBatchConfiguration;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.SimpleBatchConfiguration;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * {@link Override} 2 {@link Bean} who fail during initialization of Spring-Boot.
 * <p>
 * "<i>JobRepositoryFactoryBean cannot be cast to JobRepository</i>"
 *
 * @see SimpleBatchConfiguration
 */
@Configuration
public class BatchAdminConfigFix extends SimpleBatchConfiguration {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    /**
     * {@link Override} default Spring-Boot application who fail during initialization.
     *
     * @see AbstractBatchConfiguration#jobBuilders()
     */
    @Override
    @Bean
    @Primary
    public JobBuilderFactory jobBuilders() {
        return new JobBuilderFactory(jobRepository);
    }

    /**
     * {@link Override} default Spring-Boot application who fail during initialization.
     *
     * @see AbstractBatchConfiguration#stepBuilders()
     */
    @Override
    @Bean
    @Primary
    public StepBuilderFactory stepBuilders() {
        return new StepBuilderFactory(jobRepository, transactionManager);
    }

}