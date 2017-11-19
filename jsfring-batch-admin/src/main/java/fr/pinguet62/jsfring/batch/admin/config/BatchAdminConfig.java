package fr.pinguet62.jsfring.batch.admin.config;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class BatchAdminConfig {

    // TODO javadoc
    @Bean
    public BatchConfigurer configurer(@Qualifier("dataSource") DataSource dataSource) {
        return new DefaultBatchConfigurer(dataSource);
    }

}