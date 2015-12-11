package fr.pinguet62.jsfring.ws.converter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.convert.support.GenericConversionService;

@Configuration
public class ConversionServiceConfig {

    @Bean
    public GenericConversionService conversionService() {
        return new DefaultConversionService();
    }

}