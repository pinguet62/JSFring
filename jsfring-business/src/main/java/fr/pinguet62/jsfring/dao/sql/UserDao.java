package fr.pinguet62.jsfring.dao.sql;

import static java.util.Calendar.DAY_OF_YEAR;
import static java.util.Calendar.getInstance;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAUpdateClause;

import fr.pinguet62.jsfring.dao.sql.common.CommonRepository;
import fr.pinguet62.jsfring.model.sql.QUser;
import fr.pinguet62.jsfring.model.sql.User;
import lombok.extern.slf4j.Slf4j;

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

@Slf4j
class UserDaoImpl implements UserDaoCustom {

    @Inject
    private UserDao dao;

    /** The {@link EntityManager}. */
    @PersistenceContext
    protected EntityManager em;

    /**
     * Disable all users who have not connected since {@code numberOfDays} days. <br>
     * Ignore never connected {@link User}s.
     * <p>
     * Reset the {@link EntityManager} before call, to impact calling methods.
     *
     * @param numberOfDays
     *            Number of days (positive).
     * @throws IllegalArgumentException
     *             Zero or negative number of days.
     */
    @Override
    public void disableInactiveUsers(int numberOfDays) {
        if (numberOfDays <= 0)
            throw new IllegalArgumentException("The number of days must be a positive value.");

        Calendar c = getInstance();
        c.add(DAY_OF_YEAR, -1 * numberOfDays);
        Date lastAccepted = c.getTime();

        em.clear(); // because first-level cache is not updated by this method

        QUser u = QUser.user;
        long nb = new JPAUpdateClause(em, u).where(u.lastConnection.before(lastAccepted)).set(u.active, false).execute();
        log.info("Number of users disabled: {}", nb);
    }

    /**
     * Reset the {@link User#lastConnection last connection date} to the current day.
     *
     * @param user
     *            The {@link User} to update.
     */
    @Override
    public void resetLastConnectionDate(User user) {
        log.debug("Last connection date reset for user: {}", user.getEmail());
        user.setLastConnection(new Date());
        dao.save(user);
    }

    /**
     * Update the {@link User#password password}.
     *
     * @param user
     *            The {@link User} to update.
     * @param password
     *            The new {@link User#password user's password}.
     */
    @Override
    public void updatePassword(User user, String password) {
        log.debug("Password updated for user: {}", user.getEmail());
        user.setPassword(password);
        dao.save(user);
    }

}