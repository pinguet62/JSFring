package fr.pinguet62.jsfring.ws.config;

import static java.util.Objects.requireNonNull;
import static org.springframework.boot.autoconfigure.security.SecurityProperties.ACCESS_OVERRIDE_ORDER;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.stereotype.Component;

@Configuration
@EnableAuthorizationServer
@EnableResourceServer
@EnableGlobalMethodSecurity(jsr250Enabled = true)
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

    /**
     * Explicit declaration of {@link WebSecurityConfigurerAdapter} with specific {@link Order}, to declare OAuth security
     * before other components.
     *
     * @see <a href=
     *      "https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-1.5-Release-Notes#oauth-2-resource-filter"> Spring-
     *      Boot 1.5 Release notes</a>
     */
    @Component
    @Order(ACCESS_OVERRIDE_ORDER)
    public static class MyWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    }

}