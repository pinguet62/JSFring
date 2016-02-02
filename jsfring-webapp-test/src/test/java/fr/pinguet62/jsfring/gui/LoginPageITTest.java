package fr.pinguet62.jsfring.gui;

import static fr.pinguet62.jsfring.test.Config.DATASET;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.dao.sql.UserDao;
import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.IndexPage;
import fr.pinguet62.jsfring.gui.htmlunit.LoginPage;
import fr.pinguet62.jsfring.model.sql.User;

/** @see LoginPage */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringBootConfig.class)
@WebIntegrationTest
@DatabaseSetup(DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class LoginPageITTest {

    @Inject
    private UserDao userDao;

    /** After login: redirect to index page. */
    @Test
    public void test_login() {
        User user = userDao.findAll().get(0);

        LoginPage loginPage = AbstractPage.get().gotoLoginPage();
        AbstractPage afterLogin = loginPage.doLogin(user.getLogin(), user.getPassword());

        assertTrue(afterLogin instanceof IndexPage);
    }

    /**
     * Invalid {@link User#password}:
     * <ul>
     * <li>Refresh current login page</li>
     * <li>Error message</li>
     * </ul>
     */
    @Test
    public void test_login_invalidPassword() {
        User user = userDao.findAll().get(0);
        String invalidPassword = UUID.randomUUID().toString();
        assertNotEquals(invalidPassword, user.getPassword());

        LoginPage loginPage = AbstractPage.get().gotoLoginPage();
        AbstractPage afterLogin = loginPage.doLogin(user.getLogin(), invalidPassword);

        assertTrue(afterLogin instanceof LoginPage);
        assertNotNull(loginPage.getMessageError());
    }

    /**
     * Unknown {@link User#login}:
     * <ul>
     * <li>Refresh current login page</li>
     * <li>Error message</li>
     * </ul>
     */
    @Test
    public void test_login_unknownLogin() {
        String login = UUID.randomUUID().toString();
        assertNull(userDao.findOne(login));

        LoginPage loginPage = AbstractPage.get().gotoLoginPage();
        AbstractPage afterLogin = loginPage.doLogin(login, "a password");

        assertTrue(afterLogin instanceof LoginPage);
        assertNotNull(loginPage.getMessageError());
    }

    // @Test
    // public void test_rememberMe() {
    // throw new UnsupportedOperationException("TODO");
    // }

}