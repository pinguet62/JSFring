package fr.pinguet62.jsfring.dao;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAUpdateClause;

import fr.pinguet62.jsfring.dao.common.CommonRepository;
import fr.pinguet62.jsfring.model.QUser;
import fr.pinguet62.jsfring.model.User;

/** @see User */
@Repository
public interface UserDao extends CommonRepository<User, String>, UserDaoCustom {
    User findByEmail(String email);
}

interface UserDaoCustom {
    void disableInactiveUsers(int numberOfDays);

    void resetLastConnectionDate(User user);

    void updatePassword(User user, String password);
}

class UserDaoImpl implements UserDaoCustom {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    @Inject
    private UserDao dao;

    /** The {@link EntityManager}. */
    @PersistenceContext
    protected EntityManager em;

    /**
     * Disable all users who have not connected since {@code numberOfDays} days.
     * <br>
     * Ignore {@link User}s who never be connected.
     *
     * @param numberOfDays Number of days.
     * @throws IllegalArgumentException Zero or negative number of days.
     */
    @Override
    public void disableInactiveUsers(int numberOfDays) {
        if (numberOfDays <= 0)
            throw new IllegalArgumentException("The number of days must be a positive value.");

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, numberOfDays);
        Date lastAccepted = c.getTime();

        QUser u = QUser.user;
        long nb = new JPAUpdateClause(em, u).where(u.active.eq(true).and(u.lastConnection.before(lastAccepted)))
                .set(u.active, false).execute();
        LOGGER.info("Number of users disabled: {}", nb);
    }

    /**
     * Reset the {@link User#lastConnection last connection date} to the current
     * day.
     *
     * @param user The {@link User} to update.
     */
    @Override
    public void resetLastConnectionDate(User user) {
        LOGGER.debug("Last connection date reset for user: {}", user.getLogin());
        user.setLastConnection(new Date());
        dao.save(user);
    }

    /**
     * Update the {@link User#password password}.
     *
     * @param user The {@link User} to update.
     * @param password The new {@link User#password user's password}.
     */
    @Override
    public void updatePassword(User user, String password) {
        LOGGER.debug("Password updated for user: {}", user.getLogin());
        user.setPassword(password);
        dao.save(user);
    }

}