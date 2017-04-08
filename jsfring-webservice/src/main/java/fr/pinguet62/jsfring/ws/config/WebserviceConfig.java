package fr.pinguet62.jsfring.ws.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/application-webservice.properties")
public class WebserviceConfig {
}