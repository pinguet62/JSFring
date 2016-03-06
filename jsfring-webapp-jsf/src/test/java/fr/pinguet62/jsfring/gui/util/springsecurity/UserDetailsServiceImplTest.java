package fr.pinguet62.jsfring.gui.util.springsecurity;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static java.time.LocalDate.now;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.exparity.hamcrest.date.DateMatchers.within;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.mysema.query.BooleanBuilder;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.gui.config.security.UserDetailsServiceImpl;
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
        String login = userService.findAll(new BooleanBuilder()).get(0).getLogin();

        // login
        UserDetails userDetails = userDetailsService.loadUserByUsername(login);
        assertThat(userDetails, is(not(nullValue())));

        // Test: last connection date
        Date lastConnection = userService.get(login).getLastConnection();
        assertThat(lastConnection, within(30, SECONDS, now()));
    }

    /**
     * When the {@link User#login} is unknown, the login fails.
     *
     * @see UserDetailsServiceImpl#loadUserByUsername(String)
     */
    @Test(expected = UsernameNotFoundException.class)
    public void test_login_unknownLogin() {
        String login = "unknown login";
        assertThat(userService.get(login), is(nullValue()));

        userDetailsService.loadUserByUsername(login);
    }

}