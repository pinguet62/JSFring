package fr.pinguet62.jsfring.ws.config;

import static org.glassfish.jersey.servlet.ServletProperties.JAXRS_APPLICATION_CLASS;

import java.util.List;

import javax.inject.Inject;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig {

    @Configuration
    public static class PackageResourceConfig extends ResourceConfig {
        @Inject
        private BeanFactory beanFactory;

        public PackageResourceConfig() {
            List<String> packagesList = AutoConfigurationPackages.get(beanFactory);
            String[] packagesArray = packagesList.toArray(new String[packagesList.size()]);
            packages(packagesArray);
        }
    }

    @Bean
    public ServletRegistrationBean jerseyServlet() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/*");
        registration.addInitParameter(JAXRS_APPLICATION_CLASS, JerseyConfig.class.getName());
        return registration;
    }

}