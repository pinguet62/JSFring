package fr.pinguet62.jsfring.webservice.config;

import static java.util.Objects.requireNonNull;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
@EnableAuthorizationServer
@EnableResourceServer
public class OAuth2Config {

    /** Use custom {@link UserDetailsService}. */
    @Configuration
    public static class AuthenticationManagerConfiguration extends GlobalAuthenticationConfigurerAdapter {

        private final UserDetailsService userDetailsService;

        public AuthenticationManagerConfiguration(UserDetailsService userDetailsService) {
            this.userDetailsService = requireNonNull(userDetailsService);
        }

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService);
        }

    }

}