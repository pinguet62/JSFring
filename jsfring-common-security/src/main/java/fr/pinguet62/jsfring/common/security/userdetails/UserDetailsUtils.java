package fr.pinguet62.jsfring.common.security.userdetails;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsUtils {

    /**
     * Get the current logged {@link UserDetails}.
     *
     * @return The {@link UserDetails}.
     */
    public static UserDetails getCurrent() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext == null)
            return null;
        Authentication authentication = securityContext.getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken)
            return null;
        return (UserDetails) authentication.getPrincipal();
    }

}