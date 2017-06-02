package fr.pinguet62.jsfring.service;

import static fr.pinguet62.jsfring.model.sql.User.PASSWORD_REGEX;
import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.util.MatcherUtils.matches;
import static java.util.stream.Stream.generate;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.inject.Inject;
import javax.validation.constraints.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.common.PasswordGenerator;
import fr.pinguet62.jsfring.mock.MailSenderThrowableMock;
import fr.pinguet62.jsfring.model.sql.User;

/** @see UserService */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootConfig.class)
@Transactional
//DbUnit
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup(DATASET)
public class UserServiceTest {

    @Inject
    private MailSenderThrowableMock mailSender;

    @Inject
    private UserService service;

    /** @see UserService#forgottenPassword(String) */
    @Test
    public void test_forgottenPassword() {
        final String email = service.getAll().get(0).getEmail();

        User user = service.get(email);
        final String initialPassword = user.getPassword();

        service.forgottenPassword(user.getEmail());

        User user2 = service.get(email);
        assertThat(user2.getPassword(), is(not(equalTo(initialPassword))));
    }

    /**
     * If an error occurs during {@link MailSender#send(org.springframework.mail.SimpleMailMessage...) mail sending},
     * the password must not be updated.
     *
     * @see UserService#forgottenPassword(String)
     */
    @Test
    public void test_forgottenPassword_smtpError() {
        final String email = service.getAll().get(0).getEmail();

        // Before
        User user = service.get(email);
        final String initialPassword = user.getPassword();

        mailSender.setMustThrow(true);
        try {
            // Case
            service.forgottenPassword(user.getEmail());
            fail();
        } catch (RuntimeException e) {} finally {
            mailSender.setMustThrow(false);
        }

        // After
        assertThat(service.get(email).getPassword(), is(equalTo(initialPassword)));
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
     * The generated {@link User#password password} must match to {@link User#PASSWORD_REGEX password regex}.
     *
     * @see UserService#randomPassword()
     * @see User#PASSWORD_REGEX
     */
    @Test
    public void test_randomPassword() {
        assertTrue(generate(UserService::randomPassword).limit(100).allMatch(pwd -> pwd.matches(PASSWORD_REGEX)));
    }

    /** @see UserService#updatePassword(String, String) */
    @Test
    public void test_updatePassword() {
        String email = "root@admin.fr";
        String newPassword = new PasswordGenerator().get();
        assertThat(service.get(email).getPassword(), is(not(equalTo(newPassword))));

        service.updatePassword(email, newPassword);
        assertThat(service.get(email).getPassword(), is(equalTo(newPassword)));
    }

    /**
     * When the new {@link User#password} doesn't valid the {@link Pattern regex validation}, an error occurs.
     *
     * @see UserService#updatePassword(String, String)
     */
    @Test(expected = RuntimeException.class)
    public void test_updatePassword_invalidNewPassword() {
        String email = "root@admin.fr";
        String newPassword = "bad";
        assertThat(service.get(email).getPassword(), is(not(equalTo(newPassword))));
        assertThat(newPassword, not(matches(PASSWORD_REGEX)));

        service.updatePassword(email, newPassword);
    }

    /**
     * When the new {@link User#email} is unknown, an error occurs.
     *
     * @see UserService#updatePassword(String, String)
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updatePassword_unknownEmail() {
        String email = "unknown";
        assertThat(service.get(email), is(nullValue()));

        service.updatePassword(email, new PasswordGenerator().get());
    }

}