package fr.pinguet62.jsfring.util.cdi.config;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.Producer;
import javax.inject.Inject;

public class PropertyProducer {

    @Inject
    PropertyResolver propertyResolver;

    // TODO doesn't work
    /**
     * {@link Producer} to inject {@link Property} into {@link Inject}ed
     * attributes.
     *
     * @return The property value.<br/>
     *         The {@link Property#defaultValue() default value} if the key
     *         doesn't exists.
     * @throws IllegalStateException TODO
     */
    @Produces
    @Property
    public String getStringConfigValue(InjectionPoint ip) {
        Property param = ip.getAnnotated().getAnnotation(Property.class);
        String key = param.key();

        // No key set
        if (key == null || key.isEmpty()) {
            throw new IllegalStateException(
                    "The property key cannot be null or empty. Please check value of annotation of attribute "
                            + ip.getMember());
        }

        String value = propertyResolver.getValue(key);

        // Key not found: default value
        if (value == null)
            return param.defaultValue();

        return value;
    }

}