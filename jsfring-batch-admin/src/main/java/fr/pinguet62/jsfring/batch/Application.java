package fr.pinguet62.jsfring.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import fr.pinguet62.jsfring.SpringBootConfig;

@Configuration
@PropertySource("classpath:/application-batch-admin.properties")
@EnableBatchProcessing
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootConfig.class, args);
    }

}