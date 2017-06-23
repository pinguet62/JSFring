package fr.pinguet62.jsfring.service;

import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static java.util.Objects.requireNonNull;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.pinguet62.jsfring.common.PasswordGenerator;
import fr.pinguet62.jsfring.dao.sql.UserDao;
import fr.pinguet62.jsfring.model.sql.User;
import lombok.extern.slf4j.Slf4j;

/** The service for {@link User}. */
@Slf4j
@Service
public class UserService extends AbstractService<User, String> {

    /**
     * Generate random {@link User#password}.
     * <p>
     * The password will match {@link User#PASSWORD_REGEX}.
     *
     * @return A new {@link User#password}.
     */
    public static String randomPassword() {
        return new PasswordGenerator().get();
    }

    private final UserDao dao;

    private final SimpleMailMessage forgottenPasswordMessage;

    private final MailSender mailSender;

    public UserService(UserDao dao, MailSender mailSender, SimpleMailMessage forgottenPasswordMessage) {
        super(dao);
        this.dao = requireNonNull(dao);
        this.mailSender = requireNonNull(mailSender);
        this.forgottenPasswordMessage = requireNonNull(forgottenPasswordMessage);
    }

    public long count() {
        return dao.count();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Initialize the {@link User#password} before insert.
     */
    @Override
    @Transactional
    public User create(User object) {
        // Generate password
        object.setPassword(randomPassword());
        object.setLastConnection(now());

        return super.create(object);
    }

    /**
     * Reset the password of {@code User} and sent these informations to email.
     *
     * @param email
     *            The user's email.
     * @throws IllegalArgumentException
     *             Email unknown.
     */
    @Transactional
    public void forgottenPassword(String email) {
        // Get user
        User user = dao.findByEmail(email);
        if (user == null)
            throw new IllegalArgumentException("Email unknown: " + email);

        // Reset password
        user.setPassword(randomPassword());
        dao.save(user);

        // Send email
        SimpleMailMessage message = new SimpleMailMessage(forgottenPasswordMessage);
        message.setTo(user.getEmail());
        message.setText(format(forgottenPasswordMessage.getText(), user.getEmail(), user.getPassword()));
        mailSender.send(message);
        log.info("New password sent to {} user's email", user.getEmail());
    }

    /**
     * Update the {@link User#password user's password}.
     *
     * @param email
     *            The {@link User#email user's email}.
     * @param password
     *            The new {@link User#password user's password}.
     * @throws IllegalArgumentException
     *             User not found.
     */
    @Transactional
    public void updatePassword(String email, String password) {
        User user = dao.findByEmail(email);
        if (user == null)
            throw new IllegalArgumentException("User not found");

        log.debug("Password updated for user: {}", email);
        user.setPassword(password);
        dao.save(user);
    }

}