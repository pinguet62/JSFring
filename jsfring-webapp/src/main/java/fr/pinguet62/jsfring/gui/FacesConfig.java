package fr.pinguet62.jsfring.gui;

import javax.el.ELResolver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.jsf.el.SpringBeanFacesELResolver;

/** Java configuration for {@code web.xml} file. */
@Configuration
public class FacesConfig {

    @Bean
    public ELResolver elResolver() {
        return new SpringBeanFacesELResolver();
    }

}