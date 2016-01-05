package fr.pinguet62.jsfring.gui.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "fr.pinguet62.jsfring")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}