package fr.pinguet62.jsfring.indexer.nosql;

import org.springframework.boot.SpringApplication;

import fr.pinguet62.jsfring.SpringBootConfig;

public final class Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootConfig.class, args);
        System.out.println("ok");
    }

}