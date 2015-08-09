package fr.pinguet62.jsfring.gui.login.springsecurity;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

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

/**
 * Custom {@link AuthenticationProvider} because the
 * {@link UserService#login(String, String) login service} has custom
 * functionalities.
 */
@Component
public final class CustomAuthenticationProvider implements
AuthenticationProvider {

    @Inject
    private UserService userService;

    /**
     * Perform the authentication.<br>
     * Initialize the user's roles.
     *
     * @param authentication The login informations:<br>
     *            <ul>
     *            <li>{@link Authentication#getName()} contains the username;</li>
     *            <li>{@link Authentication#getCredentials()} contains the
     *            password.</li>
     *            </ul>
     * @return UsernamePasswordDetailsAuthenticationToken The initialized
     *         {@link Authentication}.
     * @throws BadCredentialsException Username or password invalid.
     */
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        User user = userService.login(username, password);
        if (user == null)
            throw new BadCredentialsException("Username or password invalid");

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