package fr.pinguet62.jsfring;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Common configuration for Spring Boot.
 * <p>
 * {@link SpringBootApplication} on root package of application, used to define
 * the base package to scan.
 * <ul>
 * <li>All class of each modules of this application must be defined into
 * sub-package thereof.</li>
 * <li>All {@code main} method must define this class as source:<br>
 * {@code SpringApplication.run(SpringBootConfig.class, args);}.</li>
 * </ul>
 */
@SpringBootApplication
public class SpringBootConfig {}