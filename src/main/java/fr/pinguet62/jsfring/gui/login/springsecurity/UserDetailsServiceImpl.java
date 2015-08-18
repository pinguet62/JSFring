package fr.pinguet62.jsfring.gui.login.springsecurity;

import javax.inject.Inject;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import fr.pinguet62.jsfring.model.User;
import fr.pinguet62.jsfring.service.UserService;

/**
 * Implementation of {@link UserDetailsService}.
 *
 * @see UserService
 * @see UserDetailsServicee
 */
@Component(value = "userDetailsService")
public final class UserDetailsServiceImpl implements UserDetailsService {

    @Inject
    private UserService userService;

    /**
     * @throws UsernameNotFoundException If {@link User#getLogin()} is not
     *             found.
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userService.get(username);

        if (user == null)
            throw new UsernameNotFoundException(null);

        return new UserDetailsImpl(user);
    }

}