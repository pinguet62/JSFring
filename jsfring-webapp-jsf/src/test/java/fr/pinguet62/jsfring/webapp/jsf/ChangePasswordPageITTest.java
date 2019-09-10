package fr.pinguet62.jsfring.webapp.jsf;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.common.PasswordGenerator;
import fr.pinguet62.jsfring.model.sql.QUser;
import fr.pinguet62.jsfring.model.sql.User;
import fr.pinguet62.jsfring.service.UserService;
import fr.pinguet62.jsfring.webapp.jsf.config.scope.SpringRequestScoped;
import fr.pinguet62.jsfring.webapp.jsf.config.security.RequiresAnyUser;
import fr.pinguet62.jsfring.webapp.jsf.config.security.WithAnyUser;
import fr.pinguet62.jsfring.webapp.jsf.config.security.WithUserHavingRoles;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.ChangePasswordPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static fr.pinguet62.jsfring.common.security.userdetails.UserDetailsUtils.getCurrent;
import static fr.pinguet62.jsfring.model.sql.User.PASSWORD_REGEX;
import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.util.MatcherUtils.matches;
import static fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage.get;
import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

/**
 * @see ChangePasswordPage
 */
@SpringRequestScoped
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
// DbUnit
@TestExecutionListeners(mergeMode = MERGE_WITH_DEFAULTS, listeners = {OrderedDbUnitTestExecutionListener.class, WithSecurityContextTestExecutionListener.class})
@DatabaseSetup(DATASET)
public class ChangePasswordPageITTest {

    private ChangePasswordPage page;

    @Autowired
    private UserService userService;

    /**
     * Page requires login.
     */
    @BeforeEach
    public void before() {
        page = get().gotoChangePasswordPage();
    }

    private User getUser() {
        return userService.findAll(QUser.user.email.eq(getCurrent().getUsername())).blockFirst();
    }

    @Test
    @WithUserHavingRoles("RIGHT_RO")
    public void test() {
        final String newPassword = new PasswordGenerator().get();

        page.setActualPassword(getUser().getPassword());
        page.setNewPassword(newPassword);
        page.setConfirmPassword(newPassword);
        page.submit();

        // Checks
        assertThat(page.getMessageInfo(), is(not(nullValue())));
        assertThat(page.getMessageError(), is(nullValue()));
        assertThat(userService.get(getUser().getEmail()).block().getPassword(), is(equalTo(newPassword)));
    }

    /**
     * The confirm password doesn't match to the new password.
     */
    @Test
    @RequiresAnyUser
    public void test_confirmNotMatchs() {
        String newPassword = new PasswordGenerator().get();
        String confirmPassword = new PasswordGenerator().get();
        assertThat(confirmPassword, is(not(equalTo(newPassword))));

        page.setActualPassword(new PasswordGenerator().get());
        page.setNewPassword(newPassword);
        page.setConfirmPassword(confirmPassword);
        page.submit();

        assertThat(page.getMessageError(), is(not(nullValue())));
    }

    /**
     * The confirm password doesn't match to the new password.
     */
    @Test
    @WithAnyUser
    public void test_invalidCurrent() {
        String currentPassword = randomUUID().toString();
        assertThat(currentPassword, not(equalTo(getUser().getPassword())));

        String newPassword = new PasswordGenerator().get();

        page.setActualPassword(currentPassword);
        page.setNewPassword(newPassword);
        page.setConfirmPassword(newPassword);
        page.submit();

        assertThat(page.getMessageError(), is(not(nullValue())));
    }

    /**
     * The confirm password doesn't match to the new password.
     */
    @Test
    @WithAnyUser
    public void test_ruleValidation() {
        String newPassword = "toto";
        assertThat(newPassword, not(matches(PASSWORD_REGEX)));

        page.setActualPassword(getUser().getPassword());
        page.setNewPassword(newPassword);
        page.setConfirmPassword(newPassword);
        page.submit();

        assertThat(page.getMessageError(), is(not(nullValue())));
    }

}
