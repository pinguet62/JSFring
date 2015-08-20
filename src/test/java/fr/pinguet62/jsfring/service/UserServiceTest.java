package fr.pinguet62.jsfring.service;

import static fr.pinguet62.jsfring.model.User.PASSWORD_REGEX;
import static org.junit.Assert.assertNotEquals;

import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.Config;
import fr.pinguet62.jsfring.model.User;

/** @see UserService */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = Config.SPRING)
@DatabaseSetup(Config.DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class UserServiceTest {

    @Autowired
    private UserService service;

    /**
     * <ul>
     * <li>Password changed</li>
     * <li>Email sent without error</li>
     * </ul>
     *
     * @see UserService#forgottenPassword(String)
     */
    @Test
    public void test_forgottenPassword() {
        final String login = "admin";
        User user1 = service.get(login);
        final String initialPassword = user1.getPassword();

        service.forgottenPassword(user1.getEmail());

        User user2 = service.get(login);
        assertNotEquals(initialPassword, user2.getPassword());
    }

    /**
     * Email unknown.
     *
     * @see UserService#forgottenPassword(String)
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_forgottenPassword_unknown() {
        service.forgottenPassword("toto");
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
                .allMatch(pwd -> !pwd.matches(PASSWORD_REGEX));
    }

}