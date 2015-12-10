package fr.pinguet62.jsfring.ws.converter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class TestConverter {

    @Bean
    public Converter<String, Integer> toIntegerConverter() {
        return new Converter<String, Integer>() {
            @Override
            public Integer convert(String source) {
                return Integer.parseInt(source);
            }
        };
    }

}