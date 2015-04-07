package fr.pinguet62.jsfring.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;

import fr.pinguet62.jsfring.dao.UserDao;
import fr.pinguet62.jsfring.model.QUser;
import fr.pinguet62.jsfring.model.User;

/** The service for {@link User}. */
@Service
public class UserService extends AbstractService<User, String> {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(UserService.class);

    private final UserDao dao;

    @Autowired
    private SimpleMailMessage forgottenPasswordMessage;

    @Autowired
    private MailSender mailSender;

    @Autowired
    protected UserService(UserDao dao) {
        super(dao);
        this.dao = dao;
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
        User user = dao.getByEmail(email);
        if (user == null)
            throw new IllegalArgumentException("Email unknown: " + email);

        // Reset password
        user.setPassword(UUID.randomUUID().toString().substring(0, 15));
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

    @Transactional(readOnly = true)
    public User login(String username, String password) {
        // Login
        List<User> users = findPanginated(
                new JPAQuery().from(QUser.user).where(
                        QUser.user.login.eq(username))).getResults();
        if (users.isEmpty())
            return null;
        User user = users.get(0);

        // Password
        return user.getPassword().equals(password) ? user : null;
    }

}
