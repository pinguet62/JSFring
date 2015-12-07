package fr.pinguet62.jsfring.gui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // intercept-url
                .authorizeRequests()//
                .antMatchers("/test.xhtml").permitAll()//
                .antMatchers("/login.xhtml").permitAll()//
                .antMatchers("/right.xhtml").hasRole("RIGHT_RO")//
                .antMatchers("/profiles.xhtml").hasRole("PROFILE_RO")//
                .antMatchers("/user.xhtml").hasRole("USER_RO")//
                .antMatchers("/index.xhtml").permitAll()//
                // form-login
                .and().formLogin()//
                .loginPage("/login.xhtml")//
                .loginProcessingUrl("/j_spring_security_check")//
                .failureUrl("/login.xhtml?error")//
                // remember-me
                .and().rememberMe()//
                .rememberMeParameter("remember-me_input")//
                .tokenRepository(tokenRepository())//
                .tokenValiditySeconds(5555)//
                // csrf
                .and().csrf()//
                .disable()// otherwise error
                ;
    }

    /** Remember me. */
    @Bean
    public PersistentTokenRepository tokenRepository() {
        return new InMemoryTokenRepositoryImpl();
    }

}