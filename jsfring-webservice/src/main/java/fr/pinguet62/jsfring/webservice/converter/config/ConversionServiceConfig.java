package fr.pinguet62.jsfring.webservice.converter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

/**
 * Configuration for Spring converters.
 */
@Configuration
public class ConversionServiceConfig {

    /**
     * @see ConversionService
     */
    @Bean
    public ConversionService conversionService() {
        return new DefaultConversionService();
    }

}