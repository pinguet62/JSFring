package fr.pinguet62.jsfring.gui.config;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import javax.faces.component.FacesComponent;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.util.ClassUtils;

import com.sun.faces.config.FacesInitializer;

/**
 * Use this {@link ServletContextInitializer} to scan this application and pass
 * all annotated classes to {@link FacesInitializer}.
 * <p>
 * {@link FacesComponent} (and other?) not working with embedded server.
 *
 * @see <a href="https://github.com/spring-projects/spring-boot/issues/3216">
 *      Spring-Boot issue #3216</a>
 */
// @Configuration
public class JSFConfig implements ServletContextInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JSFConfig.class);

    @SuppressWarnings("unchecked")
    private ClassPathScanningCandidateComponentProvider constructScannerForServletInitializer(
            Class<? extends ServletContainerInitializer> initializerClass) {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        for (Class<?> class1 : initializerClass.getAnnotation(HandlesTypes.class).value()) {
            if (class1.isAnnotation()) {
                LOGGER.debug("Filtering on annotation {}", class1);
                scanner.addIncludeFilter(new AnnotationTypeFilter((Class<? extends Annotation>) class1));
            } else {
                LOGGER.debug("Filtering on parent class or interface {}", class1);
                scanner.addIncludeFilter(new AssignableTypeFilter(class1));
            }
        }
        return scanner;
    }

    private Set<Class<?>> findAnnotatedClasses(ClassPathScanningCandidateComponentProvider scanner,
            String... basePackagesToScan) {
        Set<Class<?>> annotatedClasses = new HashSet<>();
        for (String basePackage : basePackagesToScan) {
            LOGGER.debug("Scanning under {}", basePackage);
            scanner.findCandidateComponents(basePackage).forEach(bd -> {
                annotatedClasses.add(ClassUtils.resolveClassName(bd.getBeanClassName(), getClass().getClassLoader()));
            });
        }
        return annotatedClasses;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // Register JSF stuff
        runServletInitializer(servletContext, new FacesInitializer(), "fr.pinguet62.jsfring");
    }

    /**
     * <p>
     * This fuss is needed because Spring Boot doesn't run
     * {@link ServletContainerInitializer} instances off the classpath, but JSF
     * relies on classpath scanning to get components and things registered. As
     * a result, we're forced to do the scanning here, because we can't rely on
     * the container to do it for us.
     * </p>
     *
     * @see <a href="https://github.com/spring-projects/spring-boot/issues/321">
     *      Embedded Tomcat does not honor ServletContainerInitializers</a>.
     * @see <a href=
     *      "https://github.com/spring-projects/spring-boot/issues/3216"> Spring
     *      Boot doesn't work with Mojarra 2.2.11</a>.
     */
    private void runServletInitializer(ServletContext servletContext, FacesInitializer facesInitializer,
            String... basePackagesToScan) throws ServletException {
        ClassPathScanningCandidateComponentProvider scanner = constructScannerForServletInitializer(
                facesInitializer.getClass());
        Set<Class<?>> annotatedClasses = findAnnotatedClasses(scanner, basePackagesToScan);
        facesInitializer.onStartup(annotatedClasses, servletContext);
    }

}