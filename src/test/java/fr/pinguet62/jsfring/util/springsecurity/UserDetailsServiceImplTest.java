package fr.pinguet62.jsfring.util.springsecurity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.Config;
import fr.pinguet62.jsfring.model.User;
import fr.pinguet62.jsfring.service.UserService;

/** @see UserDetailsServiceImpl */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = Config.SPRING)
@DatabaseSetup(Config.DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class UserDetailsServiceImplTest {

    @Inject
    private UserService service;

    @Inject
    private UserDetailsService userDetailsService;

    /**
     * Check that {@link User#lastConnection last connection date} was updated
     * to current day.
     *
     * @see UserDetailsServiceImpl#loadUserByUsername(String)
     */
    @Test
    public void test_login() {
        Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        String login = "super admin";

        // before
        assertTrue(service.get(login).getLastConnection().before(today));

        // login
        assertNotNull(userDetailsService.loadUserByUsername(login));

        // last connection
        Date lastConnection = service.get(login).getLastConnection();
        assertEquals(today, lastConnection);
    }

    /**
     * When the {@link User#login} is unknown, the login fails.
     *
     * @see UserDetailsServiceImpl#loadUserByUsername(String)
     */
    @Test(expected = UsernameNotFoundException.class)
    public void test_login_unknownLogin() {
        String login = "unknown login";
        assertNull(service.get(login));

        userDetailsService.loadUserByUsername(login);
    }

}