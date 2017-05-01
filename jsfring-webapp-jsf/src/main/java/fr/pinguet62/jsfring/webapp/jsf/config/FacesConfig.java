package fr.pinguet62.jsfring.webapp.jsf.config;

import javax.el.ELResolver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.jsf.el.SpringBeanFacesELResolver;

/** Java configuration for {@code faces-config.xml} file. */
@Configuration
public class FacesConfig {

    @Bean
    public ELResolver elResolver() {
        return new SpringBeanFacesELResolver();
    }

}