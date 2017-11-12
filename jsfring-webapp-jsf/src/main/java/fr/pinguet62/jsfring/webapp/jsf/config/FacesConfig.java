package fr.pinguet62.jsfring.webapp.jsf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.jsf.el.SpringBeanFacesELResolver;

import javax.el.ELResolver;

/**
 * Java configuration for {@code faces-config.xml} file.
 */
@Configuration
public class FacesConfig {

    @Bean
    public ELResolver elResolver() {
        return new SpringBeanFacesELResolver();
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

}