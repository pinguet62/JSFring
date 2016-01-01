package fr.pinguet62.jsfring.dao.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;

@SpringBootApplication(scanBasePackages = "fr.pinguet62.jsfring")
@EntityScan("fr.pinguet62.jsfring")
public class Application {}