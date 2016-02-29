package fr.pinguet62.jsfring.gui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

import fr.pinguet62.jsfring.SpringBootConfig;

@Configuration
@ServletComponentScan
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootConfig.class, args);
    }

}