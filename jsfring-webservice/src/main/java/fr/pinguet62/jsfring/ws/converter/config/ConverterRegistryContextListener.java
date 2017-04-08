package fr.pinguet62.jsfring.ws.converter.config;

import java.util.Set;

import javax.inject.Inject;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.convert.support.GenericConversionService;

/**
 * Auto-registry of:
 * <ul>
 * <li>{@link Converter}</li>
 * <li>{@link GenericConverter}</li>
 * </ul>
 */
@Configuration
public class ConverterRegistryContextListener implements ApplicationListener<ContextRefreshedEvent> {

    @Inject
    private GenericConversionService conversionService;

    /** The {@link Converter}s of classpath. */
    @Inject
    private Set<Converter<?, ?>> converters;

    /** The {@link GenericConverter}s of classpath. */
    @Inject
    private Set<GenericConverter> genericConverters;

    /**
     * Store {@link Converter} into {@link GenericConversionService}.
     *
     * @see GenericConversionService#addConverter(Converter)
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        for (Converter<?, ?> converter : converters)
            conversionService.addConverter(converter);
        for (GenericConverter converter : genericConverters)
            conversionService.addConverter(converter);
    }

}