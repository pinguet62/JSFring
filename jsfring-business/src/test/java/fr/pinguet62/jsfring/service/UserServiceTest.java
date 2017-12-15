package fr.pinguet62.jsfring.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.dao.sql.UserDao;
import fr.pinguet62.jsfring.model.sql.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.TransactionSystemException;

import javax.validation.constraints.Pattern;

import static fr.pinguet62.jsfring.model.sql.User.PASSWORD_REGEX;
import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.util.MatcherUtils.matches;
import static java.util.stream.Stream.generate;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

/**
 * @see UserService
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringBootConfig.class)
// DbUnit
@TestExecutionListeners(mergeMode = MERGE_WITH_DEFAULTS, listeners = DbUnitTestExecutionListener.class)
@DatabaseSetup(DATASET)
public class UserServiceTest {

    @Autowired
    private UserService service;

    /**
     * @see UserService#forgottenPassword(String)
     */
    @Test
    public void test_forgottenPassword_ok() {
        String email = "sample@domain.org";
        String initialPassword = "initial";

        UserDao dao = mock(UserDao.class);
        MailSender mailSender = mock(MailSender.class);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        UserService service = new UserService(dao, mailSender, mailMessage);

        when(dao.findByEmail(email)).thenReturn(User.builder().email(email).password(initialPassword).build());
        mailMessage.setText("%s, %s");

        service.forgottenPassword(email);

        verify(dao).save(argThat(u -> !u.getPassword().equals(initialPassword))); // saved with new password
        verify(mailSender).send(argThat((SimpleMailMessage m) -> m.getTo()[0].equals(email))); // email sent
    }

    /**
     * When the email is unknown, an {@link Exception} must be thrown.
     *
     * @see UserService#forgottenPassword(String)
     */
    @Test
    public void test_forgottenPassword_unknownEmail() {
        String email = "sample@domain.org";

        UserDao dao = mock(UserDao.class);
        UserService service = new UserService(dao, mock(MailSender.class), mock(SimpleMailMessage.class));

        when(dao.findByEmail(email)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> service.forgottenPassword(email));
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

    /**
     * @see UserService#updatePassword(String, String)
     */
    @Test
    public void test_updatePassword_ok() {
        String email = "sample@domain.org";
        String newPassword = "newPassword";

        UserDao dao = mock(UserDao.class);
        UserService service = new UserService(dao, mock(MailSender.class), mock(SimpleMailMessage.class));

        when(dao.findByEmail(email)).thenReturn(User.builder().email(email).build());

        service.updatePassword(email, newPassword);

        verify(dao).save(argThat(u -> u.getPassword().equals(newPassword)));
    }

    /**
     * When the new {@link User#password} doesn't valid the {@link Pattern regex validation}, an error occurs.
     *
     * @see UserService#updatePassword(String, String)
     */
    @Test
    public void test_updatePassword_invalidNewPassword() {
        User user = service.getAll().blockFirst();
        String email = user.getEmail();

        String initialPassword = user.getPassword();
        assertThat(initialPassword, matches(PASSWORD_REGEX));

        String newPassword = "bad";
        assertThat(service.get(email).block().getPassword(), is(not(equalTo(newPassword))));
        assertThat(newPassword, not(matches(PASSWORD_REGEX)));

        assertThrows(TransactionSystemException.class, () -> service.updatePassword(email, newPassword));
    }

    /**
     * When the new {@link User#email} is unknown, an error occurs.
     *
     * @see UserService#updatePassword(String, String)
     */
    @Test
    public void test_updatePassword_unknownEmail() {
        String email = "sample@domain.org";

        UserDao dao = mock(UserDao.class);
        UserService service = new UserService(dao, mock(MailSender.class), mock(SimpleMailMessage.class));

        when(dao.findByEmail(email)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> service.updatePassword(email, "newPassword"));
    }

}