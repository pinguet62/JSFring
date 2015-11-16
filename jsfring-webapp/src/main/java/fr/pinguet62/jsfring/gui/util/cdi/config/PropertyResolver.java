package fr.pinguet62.jsfring.gui.util.cdi.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * Resolver used to get property value, into property files, from the property
 * key.
 */
@Named
@Singleton
public class PropertyResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyResolver.class);

    /** All {@link Properties} of classpath. */
    private final Map<String, String> properties = new HashMap<>();

    /**
     * Get the property value from its key.
     *
     * @param key The property key.
     * @return The property value.<br>
     *         {@code null} if the key doesn't exists.
     */
    public String getValue(String key) {
        return properties.get(key);
    }

    /**
     * Initialize the resolver by reading all property files.
     * <p>
     * <ul>
     * <li>Scan the classpath and search all {@link Resource} matching
     * {@code classpath*:**.properties}.</li>
     * <li>Read the {@link Resource}s to get all {@link Properties}.</li>
     * <li>Save 1 by 1 key/value and check if the key already exists or not.
     * </li>
     * </ul>
     *
     * @see PathMatchingResourcePatternResolver
     */
    @PostConstruct
    private void init() throws IOException {
        LOGGER.info("Loading property files...");

        // Scan classpath
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        Resource[] ressources = patternResolver.getResources("classpath*:**.properties");
        for (Resource resource : ressources) {
            LOGGER.debug("Reading property file: {}", resource);

            // Read properties
            Properties props = PropertiesLoaderUtils.loadProperties(resource);

            // Save 1 by 1
            for (Entry<Object, Object> entry : props.entrySet()) {
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();

                // Check if check already exists in another property file
                if (properties.containsKey(key)) {
                    if (properties.get(key).equals(value))
                        LOGGER.warn("Duplicate key with different value: {}", key);
                    else
                        LOGGER.info("Duplicate key with same value: {}", key);
                }

                properties.put(key, value);
            }
        }
    }

}