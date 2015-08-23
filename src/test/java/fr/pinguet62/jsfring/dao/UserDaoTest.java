package fr.pinguet62.jsfring.dao;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import javax.inject.Inject;

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
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@Transactional
public class UserDaoTest {

    @Inject
    private UserDao userDao;

    /**
     * Check that {@link User#lastConnection last connection date} was updated
     * to current date, with precision of {@code 0.1sec}.
     *
     * @see UserDao#resetLastConnectionDate(User)
     */
    @Test
    public void test_resetLastConnectionDate() {
        String login = "super admin";
        User user = userDao.get(login);

        userDao.resetLastConnectionDate(user);

        Date newValue = userDao.get(login).getLastConnection();
        long diff = new Date().getTime() - newValue.getTime(); // ms
        assertTrue(Math.abs(diff) < 100_000/* 0.1sec */);
    }
}