package fr.pinguet62.jsfring.gui;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.Config;
import fr.pinguet62.jsfring.dao.UserDao;
import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.IndexPage;
import fr.pinguet62.jsfring.gui.htmlunit.LoginPage;
import fr.pinguet62.jsfring.model.User;

/** @see LoginPage */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = Config.SPRING)
@DatabaseSetup(Config.DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class LoginPageTest {

    @Inject
    private UserDao userDao;

    /** After login: redirect to index page. */
    @Test
    public void test_login() {
        User user = userDao.getAll().get(0);

        LoginPage loginPage = AbstractPage.get().gotoLoginPage();
        AbstractPage afterLogin = loginPage.doLogin(user.getLogin(), user.getPassword());

        assertTrue(afterLogin instanceof IndexPage);
    }

    /**
     * Bad {@link User#login} or {@link User#password}:
     * <ul>
     * <li>Redirect to the same login page</li>
     * <li>Error message</li>
     * </ul>
     */
    @Test
    public void test_login_invalid() {
        String login = "foo";
        assertNull(userDao.get(login));

        LoginPage loginPage = AbstractPage.get().gotoLoginPage();
        AbstractPage afterLogin = loginPage.doLogin(login, "password");

        assertTrue(afterLogin instanceof LoginPage);
        assertNotNull(loginPage.getMessageError());
    }

    @Test
    public void test_rememberMe() {
        throw new UnsupportedOperationException("TODO");
    }

}