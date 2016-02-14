package fr.pinguet62.jsfring.gui;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static org.hamcrest.Matchers.empty;
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
import com.mysema.query.jpa.impl.JPAQuery;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.dao.sql.UserDao;
import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.ForgottenPasswordPage;
import fr.pinguet62.jsfring.model.sql.QUser;

/** @see ForgottenPasswordPage */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringBootConfig.class)
@WebIntegrationTest
@DatabaseSetup(DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class ForgottenPasswordPageITTest {

    @Inject
    private UserDao userDao;

    /** No error: INFO message. */
    @Test
    public void test_forgottenPassword() {
        String email = userDao.findAll().get(0).getEmail();

        ForgottenPasswordPage forgottenPasswordPage = AbstractPage.get().gotoLoginPage().gotoForgottenPasswordPage();

        forgottenPasswordPage.getEmail().setValue(email);
        forgottenPasswordPage.submit();

        assertThat(forgottenPasswordPage.getMessageInfo(), is(not(nullValue())));
    }

    /** Unknown email: ERROR message. */
    @Test
    public void test_forgottenPassword_unknown() {
        String email = "unknown";
        assertThat(userDao.find(new JPAQuery().from(QUser.user).where(QUser.user.email.eq(email))), is(empty()));

        ForgottenPasswordPage forgottenPasswordPage = AbstractPage.get().gotoLoginPage().gotoForgottenPasswordPage();

        forgottenPasswordPage.getEmail().setValue(email);
        forgottenPasswordPage.submit();

        assertThat(forgottenPasswordPage.getMessageError(), is(not(nullValue())));
    }

}