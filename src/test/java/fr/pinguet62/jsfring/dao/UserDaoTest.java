package fr.pinguet62.jsfring.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.mysema.query.jpa.impl.JPAQuery;

import fr.pinguet62.Config;
import fr.pinguet62.jsfring.model.QUser;
import fr.pinguet62.jsfring.model.User;

/** @see UserDao */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = Config.SPRING)
@DatabaseSetup(Config.DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class,
        TransactionalTestExecutionListener.class })
@Transactional
public class UserDaoTest {

    @Inject
    private UserDao userDao;

    /** @see UserDao#disableInactiveUsers(int) */
    @Test
    public void test_disableInactiveUsers_illegalArgument() {
        for (Integer arg : Arrays.asList(-1, 0))
            try {
                userDao.disableInactiveUsers(arg);
                fail();
            } catch (IllegalArgumentException e) {}
    }

    /** @see UserDao#disableInactiveUsers(int) */
    @Test
    public void test_disableInactiveUsers_stayInactive() {
        final int nbOfDays = 1;

        // connection limit
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1 * nbOfDays);
        Date yesterday = calendar.getTime();

        // users who will be deactivate
        QUser u = QUser.user;
        List<User> usersToDeactivate = userDao.find(new JPAQuery().from(u).where(u.lastConnection.before(yesterday))
                .where(u.active.isTrue()));

        userDao.disableInactiveUsers(nbOfDays);
        // because first-level cache is not updated by this method
        userDao.em.clear();

        // check
        for (User user : usersToDeactivate)
            assertFalse(userDao.get(user.getLogin()).isActive());
    }

    /**
     * Check that {@link User#lastConnection last connection date} was updated
     * to current day.
     *
     * @see UserDao#resetLastConnectionDate(User)
     */
    @Test
    public void test_resetLastConnectionDate() {
        Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        String login = "super admin";

        User user = userDao.get(login);
        // Before
        Date lastConnectionBefore = user.getLastConnection();
        assertTrue(lastConnectionBefore.before(today));

        userDao.resetLastConnectionDate(user);

        // Test
        Date lastConnectionAfter = DateUtils.truncate(userDao.get(login).getLastConnection(), Calendar.DAY_OF_MONTH);
        assertEquals(today, lastConnectionAfter);
    }

}