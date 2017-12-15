package fr.pinguet62.jsfring.webapp.jsf;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.dao.sql.UserDao;
import fr.pinguet62.jsfring.model.sql.QUser;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.ForgottenPasswordPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage.get;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

/**
 * @see ForgottenPasswordPage
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
// DbUnit
@TestExecutionListeners(mergeMode = MERGE_WITH_DEFAULTS, listeners = DbUnitTestExecutionListener.class)
@DatabaseSetup(DATASET)
public class ForgottenPasswordPageITTest {

    @Autowired
    private UserDao userDao;

    /**
     * No error: INFO message.
     */
    @Test
    public void test_forgottenPassword() {
        String email = userDao.findAll().get(0).getEmail();

        ForgottenPasswordPage forgottenPasswordPage = get().gotoLoginPage().gotoForgottenPasswordPage();

        forgottenPasswordPage.getEmail().setValue(email);
        forgottenPasswordPage.submit();

        assertThat(forgottenPasswordPage.getMessageInfo(), is(not(nullValue())));
    }

    /**
     * Unknown email: ERROR message.
     */
    @Test
    public void test_forgottenPassword_unknown() {
        String email = "unknown";
        assertThat(userDao.findAll(QUser.user.email.eq(email)), is(empty()));

        ForgottenPasswordPage forgottenPasswordPage = get().gotoLoginPage().gotoForgottenPasswordPage();

        forgottenPasswordPage.getEmail().setValue(email);
        forgottenPasswordPage.submit();

        assertThat(forgottenPasswordPage.getMessageError(), is(not(nullValue())));
    }

}