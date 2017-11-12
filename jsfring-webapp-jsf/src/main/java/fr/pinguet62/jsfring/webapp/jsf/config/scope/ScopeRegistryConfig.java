package fr.pinguet62.jsfring.webapp.jsf.config.scope;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static fr.pinguet62.jsfring.webapp.jsf.config.scope.SpringViewScope.NAME;

/**
 * {@link Scope} configuration.
 */
@Configuration
public class ScopeRegistryConfig {

    @Bean
    public CustomScopeConfigurer customScopeConfigurer() {
        CustomScopeConfigurer customScopeConfigurer = new CustomScopeConfigurer();
        customScopeConfigurer.addScope(NAME, new SpringViewScope());
        return customScopeConfigurer;
    }

}