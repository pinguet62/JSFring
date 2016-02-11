package fr.pinguet62.jsfring.gui.util.springsecurity;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.gui.config.UserDetailsServiceImpl;
import fr.pinguet62.jsfring.model.sql.User;
import fr.pinguet62.jsfring.service.UserService;

/** @see UserDetailsServiceImpl */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringBootConfig.class)
@DatabaseSetup(DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class UserDetailsServiceImplTest {

    @Inject
    private UserDetailsService userDetailsService;

    @Inject
    private UserService userService;

    /**
     * Check that {@link User#lastConnection last connection date} was updated
     * to current time.
     *
     * @see UserDetailsServiceImpl#loadUserByUsername(String)
     */
    @Test
    public void test_login() {
        String login = "super admin";

        // login
        assertNotNull(userDetailsService.loadUserByUsername(login));

        // Test: < 2sec
        Date lastConnection = userService.get(login).getLastConnection();
        assertTrue((lastConnection.getTime() - new Date().getTime()) < 2_000);
    }

    /**
     * When the {@link User#login} is unknown, the login fails.
     *
     * @see UserDetailsServiceImpl#loadUserByUsername(String)
     */
    @Test(expected = UsernameNotFoundException.class)
    public void test_login_unknownLogin() {
        String login = "unknown login";
        assertNull(userService.get(login));

        userDetailsService.loadUserByUsername(login);
    }

}