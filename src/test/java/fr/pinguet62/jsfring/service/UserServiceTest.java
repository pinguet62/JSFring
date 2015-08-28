package fr.pinguet62.jsfring.service;

import static fr.pinguet62.jsfring.model.User.PASSWORD_REGEX;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.Config;
import fr.pinguet62.jsfring.model.User;
import fr.pinguet62.jsfring.service.UserService.PasswordGenerator;

/** @see UserService */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = Config.SPRING)
@DatabaseSetup(Config.DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class UserServiceTest {

    @Inject
    private UserService service;

    /**
     * @todo Test that email was sent
     * @see UserService#forgottenPassword(String)
     */
    @Test
    public void test_forgottenPassword() {
        final String login = "super admin";

        User user1 = service.get(login);
        final String initialPassword = user1.getPassword();

        service.forgottenPassword(user1.getEmail());

        User user2 = service.get(login);
        assertNotEquals(initialPassword, user2.getPassword());
    }

    /**
     * When the email is unknown, an {@link Exception} must be thrown.
     *
     * @see UserService#forgottenPassword(String)
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_forgottenPassword_unknownEmail() {
        service.forgottenPassword("toto");
    }

    /**
     * Check that {@link User#lastConnection last connection date} was updated
     * to current day.
     *
     * @see UserService#login(String, String)
     */
    @Test
    public void test_login() {
        Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        String login = "super admin";

        // Before
        assertTrue(service.get(login).getLastConnection().before(today));

        // login
        assertNotNull(service.login(login, "password"));

        // last connection
        Date lastConnection = service.get(login).getLastConnection();
        assertEquals(today, lastConnection);
    }

    /**
     * When the {@link User#password} is invalid, the login fails.
     *
     * @see UserService#login(String, String)
     */
    @Test
    public void test_login_badPassword() {
        String login = "super admin";
        assertNotNull(service.get(login));

        assertNull(service.login(login, "bad password"));
    }

    /**
     * When the {@link User#login} is unknown, the login fails.
     *
     * @see UserService#login(String, String)
     */
    @Test
    public void test_login_unknownLogin() {
        String login = "unknown login";
        assertNull(service.get(login));

        assertNull(service.login(login, new PasswordGenerator().get()));
    }

    /**
     * The generated {@link User#password password} must match to
     * {@link User#PASSWORD_REGEX password regex}.
     *
     * @see UserService#randomPassword()
     * @see User#PASSWORD_REGEX
     */
    @Test
    public void test_randomPassword() {
        Stream.generate(UserService::randomPassword).limit(100)
                .allMatch(pwd -> pwd.matches(PASSWORD_REGEX));
    }

    /** @see UserService#updatePassword(String, String) */
    @Test
    public void test_updatePassword() {
        String login = "super admin";
        String newPassword = new PasswordGenerator().get();

        assertNotEquals(newPassword, service.get(login).getPassword());

        service.updatePassword(login, newPassword);

        assertEquals(newPassword, service.get(login).getPassword());
    }

    /**
     * When the new {@link User#password} doesn't valid the {@link Pattern regex
     * validation}, an error occurs.
     *
     * @see UserService#updatePassword(String, String)
     */
    @Test(expected = RuntimeException.class)
    public void test_updatePassword_invalidNewPassword() {
        String login = "super admin";
        String newPassword = "bad";

        assertNotEquals(newPassword, service.get(login).getPassword());

        service.updatePassword(login, newPassword);
    }

    /**
     * When the new {@link User#login} is unknown, an error occurs.
     *
     * @see UserService#updatePassword(String, String)
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updatePassword_unknownLogin() {
        String login = "unknown";
        assertNull(service.get(login));

        service.updatePassword(login, new PasswordGenerator().get());
    }

}