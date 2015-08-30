package fr.pinguet62.jsfring.util.springsecurity;

import javax.inject.Inject;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import fr.pinguet62.jsfring.model.User;
import fr.pinguet62.jsfring.service.UserService;

/**
 * Implementation of {@link UserDetailsService}.
 * <p>
 * <ol>
 * <li>User fill <i>username</i> and <i>password</i></li>
 * <li>{@link DaoAuthenticationProvider} request {@link UserDetails} to
 * {@link UserDetailsService}</li>
 * <li>{@link UserDetailsService} retrieve user's informations in database, from
 * its <i>username</i></li>
 * <li>{@link DaoAuthenticationProvider} check user's <i>password</i> from the
 * {@link UserDetailsImpl#getPassword() database password} and other
 * informations</li>
 * </ol>
 *
 * @see AuthenticationProvider
 * @see UserDetailsServicee
 * @see UserService
 */
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