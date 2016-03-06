package fr.pinguet62.jsfring.gui.config;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.boot.autoconfigure.AutoConfigurationPackages.get;
import static org.springframework.util.ClassUtils.resolveClassName;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;

import org.slf4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;

import com.sun.faces.config.FacesInitializer;

/**
 * Fix for JSF with Spring Boot.
 * <p>
 * JSF relies on classpath scanning to get components and things registered. But
 * embedded Tomcat of Spring Boot does not run
 * {@link ServletContainerInitializer} instances off the classpath, and these
 * components are not registered and JSF doesn't work with Spring Boot. So this
 * {@link ServletContextInitializer} forces the scanning.
 * <p>
 * This {@link ServletContextInitializer} is to, first, get all annotations that
 * {@link ServletContextInitializer} can handle. Theses annotations are values
 * of {@link HandlesTypes} of {@link FacesInitializer}. Then Spring scans all
 * annotated classes of classpath. Finally, these classes are given to
 * {@link FacesInitializer} instance to continue initialization.
 * <p>
 * The scanned package is defined by
 * {@link AutoConfigurationPackages#get(BeanFactory)}, so it is the sub-packages
 * of {@link SpringBootApplication} location.
 *
 * @see <a href="https://github.com/spring-projects/spring-boot/issues/3216">
 *      Spring-Boot issue #3216</a>
 * @see FacesInitializer
 */
@Configuration
public class JSFInitializerConfig implements ServletContextInitializer {

    private static final Logger LOGGER = getLogger(JSFInitializerConfig.class);

    @Inject
    private BeanFactory beanFactory;

    /**
     * Build the {@link ClassPathScanningCandidateComponentProvider scanner} to
     * filter classes of classpath.
     * <p>
     * Get the {@link HandlesTypes} used into
     * {@link ServletContainerInitializer} class, and list all declared
     * annotation of {@link HandlesTypes#value()}.
     *
     * @param initializerClass The {@link ServletContainerInitializer} on witch
     *            find {@link HandlesTypes}.
     * @return The built {@link ClassPathScanningCandidateComponentProvider
     *         scanner}.
     */
    @SuppressWarnings("unchecked")
    private ClassPathScanningCandidateComponentProvider constructScannerForServletInitializer(
            Class<? extends ServletContainerInitializer> initializerClass) {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        for (Class<?> class1 : initializerClass.getAnnotation(HandlesTypes.class).value())
            if (class1.isAnnotation()) {
                LOGGER.debug("Filtering on annotation {}", class1);
                scanner.addIncludeFilter(new AnnotationTypeFilter((Class<? extends Annotation>) class1));
            } else {
                LOGGER.debug("Filtering on parent class or interface {}", class1);
                scanner.addIncludeFilter(new AssignableTypeFilter(class1));
            }
        return scanner;
    }

    /**
     * Use the scanner to find all classes of each package.
     *
     * @param scanner The {@link ClassPathScanningCandidateComponentProvider
     *            scanner} to find classes of classpath.
     * @param basePackagesToScan The packages to scan.
     */
    private Set<Class<?>> findAnnotatedClasses(ClassPathScanningCandidateComponentProvider scanner,
            List<String> basePackagesToScan) {
        Set<Class<?>> annotatedClasses = new HashSet<>();
        for (String basePackage : basePackagesToScan) {
            LOGGER.debug("Scanning under {}", basePackage);
            for (BeanDefinition bd : scanner.findCandidateComponents(basePackage))
                annotatedClasses.add(resolveClassName(bd.getBeanClassName(), getClass().getClassLoader()));
        }
        return annotatedClasses;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        List<String> basePackagesToScan = get(beanFactory);
        runServletInitializer(servletContext, new FacesInitializer(), basePackagesToScan);
    }

    private void runServletInitializer(ServletContext servletContext, FacesInitializer facesInitializer,
            List<String> basePackagesToScan) throws ServletException {
        ClassPathScanningCandidateComponentProvider scanner = constructScannerForServletInitializer(
                facesInitializer.getClass());
        Set<Class<?>> annotatedClasses = findAnnotatedClasses(scanner, basePackagesToScan);
        facesInitializer.onStartup(annotatedClasses, servletContext);
    }

}