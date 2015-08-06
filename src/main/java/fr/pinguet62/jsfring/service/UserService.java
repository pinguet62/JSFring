package fr.pinguet62.jsfring.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;

import fr.pinguet62.jsfring.dao.UserDao;
import fr.pinguet62.jsfring.model.QUser;
import fr.pinguet62.jsfring.model.User;

/** The service for {@link User}. */
@Named
public class UserService extends AbstractService<User, String> {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(UserService.class);

    /**
     * Generate random {@link User#password}.
     *
     * @return A {@link User#password}.
     */
    private static String randomPassword() {
        return UUID.randomUUID().toString().substring(0, 15);
    }

    private final UserDao dao;

    @Inject
    private SimpleMailMessage forgottenPasswordMessage;

    @Inject
    private MailSender mailSender;

    @Inject
    protected UserService(UserDao dao) {
        super(dao);
        this.dao = dao;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Initialize the {@link User#password} before insert.
     */
    @Override
    public User create(User object) {
        // Generate password
        object.setPassword(randomPassword());
        object.setLastConnection(new Date());

        return super.create(object);
    }

    /**
     * Disable all users who have not connected since a delay.
     * <p>
     * Scheduled method as batch.
     *
     * @todo Config param for number of days.
     */
    @Scheduled(fixedRate = 1_000/* ms */* 60/* sec */* 60/* min */* 1/* h */)
    @Transactional
    public void disableInactiveUsers() {
        LOGGER.info("Scheduling...");
        dao.disableInactiveUsers(7);
    }

    /**
     * Reset the password of {@code User} and sent these informations to email.
     *
     * @param email The user's email.
     * @throws IllegalArgumentException Email unknown.
     */
    @Transactional
    public void forgottenPassword(String email) {
        // Get user
        User user = dao.getByEmail(email);
        if (user == null)
            throw new IllegalArgumentException("Email unknown: " + email);

        // Reset password
        user.setPassword(randomPassword());
        dao.update(user);

        // Send email
        SimpleMailMessage message = new SimpleMailMessage(
                forgottenPasswordMessage);
        message.setTo(user.getEmail());
        message.setText(String.format(forgottenPasswordMessage.getText(),
                user.getLogin(), user.getPassword()));
        mailSender.send(message);
        LOGGER.info("New password sent to " + user.getLogin() + "'s email");
    }

    @Transactional
    public User login(String username, String password) {
        // Find by login
        List<User> users = findPanginated(
                new JPAQuery().from(QUser.user).where(
                        QUser.user.login.eq(username))).getResults();
        if (users.isEmpty())
            return null;
        User user = users.get(0);

        // Check password
        if (!user.getPassword().equals(password))
            return null;

        // Reset last connection date
        dao.resetLastConnectionDate(user);

        return user;
    }

}
