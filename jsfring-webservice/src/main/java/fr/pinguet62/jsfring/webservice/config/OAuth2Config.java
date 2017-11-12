package fr.pinguet62.jsfring.webservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import static java.util.Objects.requireNonNull;

@Configuration
@EnableAuthorizationServer
@EnableResourceServer
@Order(-20) // before basic authentication filter
public class OAuth2Config {

    @Configuration
    public static class AuthenticationManagerConfiguration extends WebSecurityConfigurerAdapter {

        private final UserDetailsService userDetailsService;

        public AuthenticationManagerConfiguration(UserDetailsService userDetailsService) {
            this.userDetailsService = requireNonNull(userDetailsService);
        }

        /**
         * Use custom {@link UserDetailsService}.
         */
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService);
        }

        /**
         * Fix (temporary?) removed OAuth2 auto-configuration.
         */
        @Bean
        @Override
        protected AuthenticationManager authenticationManager() throws Exception {
            return super.authenticationManager();
        }

    }

}