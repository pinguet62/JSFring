package fr.pinguet62.jsfring.ws.converter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

@Configuration
public class ConverterServiceConfig {

    @Bean
    public ConversionService converterService() {
        return new DefaultConversionService();
    }

}