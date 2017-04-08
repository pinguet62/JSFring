package fr.pinguet62.jsfring.gui.config.security;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

import javax.inject.Inject;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.stereotype.Component;

import fr.pinguet62.jsfring.dao.sql.UserDao;
import fr.pinguet62.jsfring.model.sql.User;

@Retention(RUNTIME)
@WithSecurityContext(factory = RequiresAnyUserSecurityContextFactory.class)
public @interface RequiresAnyUser {
}

@Component
class RequiresAnyUserSecurityContextFactory implements WithSecurityContextFactory<RequiresAnyUser> {

    @Inject
    private UserDao userDao;

    @Inject
    private UserDetailsService userDetailsService;

    @Override
    public SecurityContext createSecurityContext(RequiresAnyUser annotation) {
        User user = userDao.findAll().get(0);

        UserDetails principal = userDetailsService.loadUserByUsername(user.getLogin());
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(),
                principal.getAuthorities());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        return context;
    }

}