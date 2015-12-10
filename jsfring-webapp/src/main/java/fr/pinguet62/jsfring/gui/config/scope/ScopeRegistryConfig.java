package fr.pinguet62.jsfring.gui.config.scope;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/** {@link Scope} configuration. */
@Configuration
public class ScopeRegistryConfig {

    @Bean
    public CustomScopeConfigurer customScopeConfigurer() {
        CustomScopeConfigurer customScopeConfigurer = new CustomScopeConfigurer();
        customScopeConfigurer.addScope(SpringViewScope.NAME, new SpringViewScope());
        return customScopeConfigurer;
    }

}