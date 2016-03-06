package fr.pinguet62.jsfring.gui;

import static fr.pinguet62.jsfring.gui.htmlunit.AbstractPage.get;
import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

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

        LoginPage loginPage = get().gotoLoginPage();
        AbstractPage afterLogin = loginPage.doLogin(user.getLogin(), user.getPassword());

        assertThat(afterLogin, is(instanceOf(IndexPage.class)));
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
        String invalidPassword = randomUUID().toString();
        assertThat(user.getPassword(), is(not(equalTo(invalidPassword))));

        LoginPage loginPage = get().gotoLoginPage();
        AbstractPage afterLogin = loginPage.doLogin(user.getLogin(), invalidPassword);

        assertThat(afterLogin, is(instanceOf(LoginPage.class)));
        assertThat(loginPage.getMessageError(), is(not(nullValue())));
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
        String login = randomUUID().toString();
        assertThat(userDao.findOne(login), is(nullValue()));

        LoginPage loginPage = get().gotoLoginPage();
        AbstractPage afterLogin = loginPage.doLogin(login, "a password");

        assertThat(afterLogin, is(instanceOf(LoginPage.class)));
        assertThat(loginPage.getMessageError(), is(not(nullValue())));
    }

    // TODO Remember me
    // @Test
    // public void test_rememberMe() {
    // throw new UnsupportedOperationException("TODO");
    // }

}