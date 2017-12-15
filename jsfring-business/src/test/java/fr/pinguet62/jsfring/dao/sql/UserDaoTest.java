package fr.pinguet62.jsfring.dao.sql;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.model.sql.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.Arrays.asList;
import static java.util.Objects.nonNull;
import static org.exparity.hamcrest.date.LocalDateTimeMatchers.before;
import static org.exparity.hamcrest.date.LocalDateTimeMatchers.within;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

/**
 * @see UserDao
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringBootConfig.class)
@Transactional
// DbUnit
@TestExecutionListeners(mergeMode = MERGE_WITH_DEFAULTS, listeners = DbUnitTestExecutionListener.class)
@DatabaseSetup(DATASET)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    /**
     * @see UserDao#disableInactiveUsers(int)
     */
    @Test
    public void test_disableInactiveUsers_illegalArgument() {
        for (Integer arg : asList(-1, 0))
            try {
                userDao.disableInactiveUsers(arg);
                fail();
            } catch (IllegalArgumentException | InvalidDataAccessApiUsageException e) {
            }
    }

    /**
     * An {@link User} never connected must keep its state.
     *
     * @see UserDao#disableInactiveUsers(int)
     */
    @Test
    public void test_disableInactiveUsers_neverConnected() {
        final String id = userDao.findAll().get(0).getEmail();

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
        final String id = userDao.findAll().get(0).getEmail();

        // Initial state
        User user = userDao.getOne(id);
        user.setActive(true);
        user.setLastConnection(now().minusDays(nbOfDays).minusDays(5));
        userDao.saveAndFlush(user);

        userDao.disableInactiveUsers(nbOfDays);

        // Check
        assertThat(userDao.findById(id).get().getActive(), is(false));
    }

    /**
     * An {@link User} connected after le limit date: must stay activated.
     *
     * @see UserDao#disableInactiveUsers(int)
     */
    @Test
    public void test_disableInactiveUsers_recent() {
        final int nbOfDays = 5;
        final String id = userDao.findAll().get(0).getEmail();

        // Initial state
        User user = userDao.getOne(id);
        user.setActive(true);
        user.setLastConnection(now().minusDays(nbOfDays).plusDays(5));
        userDao.saveAndFlush(user);

        userDao.disableInactiveUsers(nbOfDays);

        // Check
        assertThat(userDao.findById(id).get().getActive(), is(true));
    }

    /**
     * Check that {@link User#lastConnection last connection date} was updated to current day.
     *
     * @see UserDao#resetLastConnectionDate(User)
     */
    @Test
    public void test_resetLastConnectionDate() {
        String email = userDao.findAll().stream().filter(u -> nonNull(u.getLastConnection())).map(User::getEmail).findAny().get();

        // Initial state
        User user = userDao.findById(email).get();
        LocalDateTime lastConnectionBefore = user.getLastConnection();
        assertThat(lastConnectionBefore, is(before(now())));

        userDao.resetLastConnectionDate(user);

        // Test
        LocalDateTime lastConnectionAfter = userDao.findById(email).get().getLastConnection();
        assertThat(lastConnectionAfter, is(within(1, SECONDS, now())));
    }

}