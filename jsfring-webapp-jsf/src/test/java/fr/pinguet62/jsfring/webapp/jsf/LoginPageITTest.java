package fr.pinguet62.jsfring.webapp.jsf;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.dao.sql.UserDao;
import fr.pinguet62.jsfring.model.sql.User;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.IndexPage;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.LoginPage;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage.get;
import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@Ignore // TODO fix loginProcessingUrl filter
/** @see LoginPage */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
// DbUnit
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup(DATASET)
public class LoginPageITTest {

    @Autowired
    private UserDao userDao;

    /**
     * After login: redirect to index page.
     */
    @Test
    public void test_login() {
        User user = userDao.findAll().get(0);

        LoginPage loginPage = get().gotoLoginPage();
        AbstractPage afterLogin = loginPage.doLogin(user.getEmail(), user.getPassword());

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
        AbstractPage afterLogin = loginPage.doLogin(user.getEmail(), invalidPassword);

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
        assertThat(userDao.findById(login).isPresent(), is(false));

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