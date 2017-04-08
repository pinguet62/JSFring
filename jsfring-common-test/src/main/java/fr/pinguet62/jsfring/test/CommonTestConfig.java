package fr.pinguet62.jsfring.test;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/application-common-test.properties")
public class CommonTestConfig {
}