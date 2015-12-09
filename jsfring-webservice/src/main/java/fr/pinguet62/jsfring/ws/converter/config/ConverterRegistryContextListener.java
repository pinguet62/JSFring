package fr.pinguet62.jsfring.ws.converter.config;

import java.util.Set;

import javax.inject.Inject;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;

/** Auto-registry of {@link Converter}. */
@Configuration
public class ConverterRegistryContextListener implements ApplicationListener<ContextRefreshedEvent> {

    @Inject
    private GenericConversionService conversionService;

    /** The {@link Converter}s of classpath. */
    @Inject
    private Set<Converter<?, ?>> converters;

    /**
     * Store {@link Converter} into {@link GenericConversionService}.
     * 
     * @see GenericConversionService#addConverter(Converter)
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        for (Converter<?, ?> converter : converters)
            conversionService.addConverter(converter);
    }

}