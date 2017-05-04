package fr.pinguet62.jsfring.common.security.userdetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.model.sql.Right;
import fr.pinguet62.jsfring.model.sql.User;

/**
 * Simple implementation of {@link UserDetails} wrapping {@link User}.
 *
 * @see UserDetails
 */
public final class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1;

    private final User user;

    UserDetailsImpl(User user) {
        this.user = user;
    }

    /**
     * @return {@link Right#getTitle() Key right}s of each {@link User#getProfiles() user's profiles}.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuths = new HashSet<>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER")); // Required by OAuth2
        for (Profile profile : user.getProfiles())
            for (Right right : profile.getRights())
                grantedAuths.add(new SimpleGrantedAuthority(right.getCode()));
        return grantedAuths;
    }

    /** @return {@link User#getPassword()} */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /** @return {@link User#getEmail()} */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * Not supported.
     *
     * @return Always {@code true}.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Not supported.
     *
     * @return Always {@code true}.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Not supported.
     *
     * @return Always {@code true}.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /** @return {@link User#getActive()} */
    @Override
    public boolean isEnabled() {
        return user.getActive();
    }

}