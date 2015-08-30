package fr.pinguet62.jsfring.util.springsecurity;

import java.util.Set;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import fr.pinguet62.jsfring.model.User;

/**
 * Initialize {@link AbstractAuthenticationToken#details details} with the
 * {@link User} object.<br>
 * So {@link #getDetails()} will return this object.
 */
public class UsernamePasswordDetailsAuthenticationToken extends
        UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = 1;

    public UsernamePasswordDetailsAuthenticationToken(String username,
            String password, User user, Set<GrantedAuthority> grantedAuths) {
        super(username, password, grantedAuths);
        setDetails(user);
    }

}
