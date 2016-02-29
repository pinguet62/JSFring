package fr.pinguet62.jsfring.task;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import fr.pinguet62.jsfring.SpringBootConfig;

@Configuration
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootConfig.class, args);
    }

}