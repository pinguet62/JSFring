package fr.pinguet62.jsfring.gui.util.cdi.config;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.Producer;
import javax.inject.Inject;

public final class PropertyProducer {

    @Inject
    private PropertyResolver propertyResolver;

    /**
     * {@link Producer} used to inject a {@link Property} into {@link Inject}ed
     * attributes.<br>
     * The property key is set on the {@link Property#key() property annotation
     * field}.
     *
     * @return The property value.<br>
     *         The {@link Property#defaultValue() default value} if the key
     *         doesn't exists.
     * @throws IllegalArgumentException Invalid key.
     * @throws IllegalStateException Property key not found and required.
     */
    @Produces
    @Property
    public String getStringConfigValue(InjectionPoint ip) {
        Property param = ip.getAnnotated().getAnnotation(Property.class);
        String key = param.key();

        // Key validation
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException(
                    "The property key cannot be null or empty. Please check value of annotation of attribute "
                            + ip.getMember());
        }

        String value = propertyResolver.getValue(key);

        // Key not found
        if (value == null) {
            // Requiered
            if (param.requiered())
                throw new IllegalStateException("Key not found: " + key);

            // Default value
            return param.defaultValue();
        }

        return value;
    }
}