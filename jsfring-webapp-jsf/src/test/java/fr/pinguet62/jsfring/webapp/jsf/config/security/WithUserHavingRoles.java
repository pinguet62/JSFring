package fr.pinguet62.jsfring.webapp.jsf.config.security;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

import java.lang.annotation.Retention;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.stereotype.Component;

import fr.pinguet62.jsfring.dao.sql.UserDao;
import fr.pinguet62.jsfring.model.sql.QUser;
import fr.pinguet62.jsfring.model.sql.User;
import fr.pinguet62.jsfring.test.TestRuntimeException;

@Retention(RUNTIME)
@WithSecurityContext(factory = WithUserHavingRolesSecurityContextFactory.class)
public @interface WithUserHavingRoles {

    /** The required {@link GrantedAuthority#getAuthority() authorities}. */
    String[] value();

}

@Component
class WithUserHavingRolesSecurityContextFactory implements WithSecurityContextFactory<WithUserHavingRoles> {

    @Inject
    private UserDao userDao;

    @Inject
    private UserDetailsService userDetailsService;

    @Override
    public SecurityContext createSecurityContext(WithUserHavingRoles annotation) {
        Optional<User> userOp = userDao.findOne(QUser.user.email.eq("root@admin.fr"));
        User user = userOp.orElseThrow(() -> new TestRuntimeException(
                "No suitable user found having these rights: " + stream(annotation.value()).collect(joining(", "))));

        UserDetails principal = userDetailsService.loadUserByUsername(user.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(),
                principal.getAuthorities());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        return context;
    }

}