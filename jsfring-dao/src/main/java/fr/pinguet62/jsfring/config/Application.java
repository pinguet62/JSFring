package fr.pinguet62.jsfring.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan("fr.pinguet62.jsfring")
@EntityScan("fr.pinguet62.jsfring.model")
public class Application {}