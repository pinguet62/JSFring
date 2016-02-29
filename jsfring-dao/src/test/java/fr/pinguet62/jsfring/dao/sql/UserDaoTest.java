package fr.pinguet62.jsfring.dao.sql;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static java.util.Arrays.asList;
import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.getInstance;
import static org.exparity.hamcrest.date.DateMatchers.before;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.model.sql.User;

/** @see UserDao */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringBootConfig.class)
@DatabaseSetup(DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class,
        TransactionalTestExecutionListener.class })
@Transactional
public class UserDaoTest {

    @Inject
    private UserDao userDao;

    /** @see UserDao#disableInactiveUsers(int) */
    @Test
    public void test_disableInactiveUsers_illegalArgument() {
        for (Integer arg : asList(-1, 0))
            try {
                userDao.disableInactiveUsers(arg);
                fail();
            } catch (IllegalArgumentException | InvalidDataAccessApiUsageException e) {}
    }

    /**
     * An {@link User} never connected must keep its state.
     *
     * @see UserDao#disableInactiveUsers(int)
     */
    @Test
    public void test_disableInactiveUsers_neverConnected() {
        final String id = userDao.findAll().get(0).getLogin();

        // Initial state
        User user = userDao.getOne(id);
        user.setLastConnection(null);
        user.setActive(true);
        userDao.saveAndFlush(user);

        userDao.disableInactiveUsers(1);

        // Check
        assertThat(userDao.getOne(id).getActive(), is(true));
    }

    /**
     * An {@link User} not connected after the limit date: must be deactivated.
     *
     * @see UserDao#disableInactiveUsers(int)
     */
    @Test
    public void test_disableInactiveUsers_old() {
        final int nbOfDays = 5;
        final String id = userDao.findAll().get(0).getLogin();

        // Initial state
        User user = userDao.getOne(id);
        user.setActive(true);
        Calendar calendar = getInstance();
        calendar.add(DATE, -nbOfDays - 5);
        user.setLastConnection(calendar.getTime());
        userDao.saveAndFlush(user);

        userDao.disableInactiveUsers(nbOfDays);

        // Check
        assertThat(userDao.findOne(id).getActive(), is(false));
    }

    /**
     * An {@link User} connected after le limit date: must stay activated.
     *
     * @see UserDao#disableInactiveUsers(int)
     */
    @Test
    public void test_disableInactiveUsers_recent() {
        final int nbOfDays = 5;
        final String id = userDao.findAll().get(0).getLogin();

        // Initial state
        User user = userDao.getOne(id);
        user.setActive(true);
        Calendar calendar = getInstance();
        calendar.add(DATE, -nbOfDays + 2);
        user.setLastConnection(calendar.getTime());
        userDao.saveAndFlush(user);

        userDao.disableInactiveUsers(nbOfDays);

        // Check
        assertThat(userDao.findOne(id).getActive(), is(true));
    }

    /**
     * Check that {@link User#lastConnection last connection date} was updated to current day.
     *
     * @see UserDao#resetLastConnectionDate(User)
     */
    @Test
    public void test_resetLastConnectionDate() {
        Date today = DateUtils.truncate(new Date(), DAY_OF_MONTH);
        String login = userDao.findAll().get(0).getLogin();

        // Initial state
        User user = userDao.findOne(login);
        Date lastConnectionBefore = user.getLastConnection();
        assertThat(lastConnectionBefore, is(before(today)));

        userDao.resetLastConnectionDate(user);

        // Test
        Date lastConnectionAfter = DateUtils.truncate(userDao.findOne(login).getLastConnection(), DAY_OF_MONTH);
        assertThat(lastConnectionAfter, is(equalTo(today)));
    }

}