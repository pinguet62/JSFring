package fr.pinguet62.jsfring.webservice.converter.config;

import static java.util.Objects.requireNonNull;

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

    private final GenericConversionService conversionService;

    /** The {@link Converter}s of classpath. */
    private Set<Converter<?, ?>> converters;

    /** The {@link GenericConverter}s of classpath. */
    private Set<GenericConverter> genericConverters;

    public ConverterRegistryContextListener(GenericConversionService conversionService) {
        this.conversionService = requireNonNull(conversionService);
    }

    /**
     * Store {@link Converter} into {@link GenericConversionService}.
     *
     * @see GenericConversionService#addConverter(Converter)
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (converters != null)
            for (Converter<?, ?> converter : converters)
                conversionService.addConverter(converter);
        if (genericConverters != null)
            for (GenericConverter converter : genericConverters)
                conversionService.addConverter(converter);
    }

    @Inject
    public void setConverters(Set<Converter<?, ?>> converters) {
        this.converters = converters;
    }

    @Inject
    public void setGenericConverters(Set<GenericConverter> genericConverters) {
        this.genericConverters = genericConverters;
    }

}