package fr.pinguet62.jsfring.gui.config.security;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import fr.pinguet62.jsfring.gui.config.scope.SpringRequestScoped;

@Configuration
public class CurrentUserDetailsConfig {

    /**
     * Access to connected {@link UserDetails}.
     *
     * @return The {@link UserDetails}.
     */
    @Bean
    @SpringRequestScoped
    public UserDetails currentUserDetails() {
        Authentication authentication = getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken)
            return null;
        else
            return (UserDetails) authentication.getPrincipal();
    }

}