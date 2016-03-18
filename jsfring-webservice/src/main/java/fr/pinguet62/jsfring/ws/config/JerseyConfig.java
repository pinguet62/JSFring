package fr.pinguet62.jsfring.ws.config;

import static fr.pinguet62.jsfring.ws.config.JerseyConfig.CONTEXT_PATH;
import static java.util.stream.Collectors.joining;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.boot.autoconfigure.AutoConfigurationPackages.get;
import static org.springframework.util.ClassUtils.resolveClassName;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

/** Register for Jersey. */
@Component
@ApplicationPath(CONTEXT_PATH)
public class JerseyConfig extends ResourceConfig {

    /**
     * The path of JAX-RS context.<br>
     * Relative to application context.
     * <p>
     * Not root context {@code "/"}, because Spring-MVC is also mapping to this context.
     */
    public static final String CONTEXT_PATH = "/rest";

    private static final Logger LOGGER = getLogger(JerseyConfig.class);

    @Autowired
    private BeanFactory beanFactory;

    /**
     * Define the package to scan, who contains JAX-RS annotated classes.
     * <p>
     * Use {@link PostConstruct} because {@link #beanFactory} is injected after
     * {@link PackageResourceConfig#PackageResourceConfig() default constructor}.
     */
    @PostConstruct
    private void init() {
        List<String> packages = get(beanFactory);
        LOGGER.debug("Registrer packages: {}", packages.stream().collect(joining(", ", "[", "]")));
        registerScannedPackages(packages); // packages(packages.stream().toArray(String[]::new));
    }

    /**
     * Fix for Jersey package scanning.
     * <p>
     * Use Spring to scan packages, finding {@link Path} and setting directly these classes to Jersey.
     *
     * @see <a href="https://java.net/jira/browse/JERSEY-2175">Jersey issue JERSEY-2175</a>
     */
    private void registerScannedPackages(Collection<String> packages) {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        // TODO Scan all @nnotations of JAX-RS
        scanner.addIncludeFilter(new AnnotationTypeFilter(Path.class)); // JAX-RS
        scanner.addIncludeFilter(new AnnotationTypeFilter(Provider.class));
        for (String basePackage : packages)
            for (BeanDefinition bd : scanner.findCandidateComponents(basePackage))
                register(resolveClassName(bd.getBeanClassName(), getClass().getClassLoader()));
    }

}