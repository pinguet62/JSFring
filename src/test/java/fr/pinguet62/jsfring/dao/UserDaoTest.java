package fr.pinguet62.jsfring.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

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

import fr.pinguet62.Config;
import fr.pinguet62.jsfring.model.User;

/** @see UserDao */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = Config.SPRING)
@DatabaseSetup(Config.DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class })
@Transactional
public class UserDaoTest {

    @Inject
    private UserDao userDao;

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