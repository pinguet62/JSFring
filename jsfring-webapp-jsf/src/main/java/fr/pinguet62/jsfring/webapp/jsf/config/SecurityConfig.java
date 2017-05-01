package fr.pinguet62.jsfring.webapp.jsf.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String LOGIN_PROCESSING_URL = "/j_spring_security_check";

    @Inject
    private UserDetailsService userDetailsService;

    /** Use custom {@link UserDetailsService}. */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                // Login
                .formLogin()
                    .loginPage("/login.xhtml")
                    .loginProcessingUrl(LOGIN_PROCESSING_URL)
                    .failureUrl("/login.xhtml?error")

            .and()
                // remember-me
                .rememberMe()
                    .rememberMeParameter("remember-me_input")
                    .tokenRepository(tokenRepository())
                    .tokenValiditySeconds(5555)
            .and()
                // intercept-url
                .authorizeRequests()
                    .anyRequest().permitAll()
                    .antMatchers("/login.xhtml").permitAll()
                    .antMatchers("/right.xhtml").hasRole("RIGHT_RO")
                    .antMatchers("/profiles.xhtml").hasRole("PROFILE_RO")
                    .antMatchers("/user.xhtml").hasRole("USER_RO")
                    .antMatchers("/index.xhtml").permitAll()
            .and()
                // Cross-Site Request Forgery
                .csrf()
                    .disable() // otherwise: error
        // @formatter:on
        ;
    }

    /**
     * <b>Remember me</b> implementation.
     *
     * @return The {@link PersistentTokenRepository}.
     */
    @Bean
    public PersistentTokenRepository tokenRepository() {
        return new InMemoryTokenRepositoryImpl();
    };

}