package fr.pinguet62.jsfring.gui.login.springsecurity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import fr.pinguet62.jsfring.model.Profile;
import fr.pinguet62.jsfring.model.Right;
import fr.pinguet62.jsfring.model.User;
import fr.pinguet62.jsfring.service.UserService;

@Component
public final class CustomAuthenticationProvider implements
AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userService.login(username, password);
        if (user == null)
            throw new BadCredentialsException(
                    "Username or password invalid");

        Set<GrantedAuthority> grantedAuths = new HashSet<>();
        for (Profile profile : user.getProfiles())
            for (Right right : profile.getRights())
                grantedAuths.add(new SimpleGrantedAuthority(right.getCode()));
        Authentication auth = new UsernamePasswordDetailsAuthenticationToken(
                username, password, user, grantedAuths);

        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
