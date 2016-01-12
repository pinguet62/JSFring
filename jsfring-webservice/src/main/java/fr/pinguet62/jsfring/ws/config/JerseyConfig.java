package fr.pinguet62.jsfring.ws.config;

import static org.glassfish.jersey.servlet.ServletProperties.JAXRS_APPLICATION_CLASS;

import javax.annotation.PostConstruct;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig {

    public static class PackageResourceConfig extends ResourceConfig {
        @Autowired
        private BeanFactory beanFactory;

        /**
         * Define the package to scan, who contains JAX-RS annotated classes.
         * <p>
         * Use {@link PostConstruct} because {@link #beanFactory} is injected
         * after {@link PackageResourceConfig#PackageResourceConfig() default
         * constructor}.
         */
        @PostConstruct
        private void init() {
            String[] packages = AutoConfigurationPackages.get(beanFactory).stream().toArray(String[]::new);
            packages(packages);
        }
    }

    @Bean
    public ServletRegistrationBean jerseyServlet() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/*");
        registration.addInitParameter(JAXRS_APPLICATION_CLASS, PackageResourceConfig.class.getName());
        return registration;
    }

}