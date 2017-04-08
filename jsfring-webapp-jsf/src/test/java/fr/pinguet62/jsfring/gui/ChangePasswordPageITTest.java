package fr.pinguet62.jsfring.gui;

import static fr.pinguet62.jsfring.gui.config.security.UserDetailsUtils.getCurrent;
import static fr.pinguet62.jsfring.gui.htmlunit.AbstractPage.get;
import static fr.pinguet62.jsfring.model.sql.User.PASSWORD_REGEX;
import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.util.MatcherUtils.matches;
import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.gui.config.scope.SpringRequestScoped;
import fr.pinguet62.jsfring.gui.config.security.RequiresAnyUser;
import fr.pinguet62.jsfring.gui.config.security.WithAnyUser;
import fr.pinguet62.jsfring.gui.config.security.WithUserHavingRoles;
import fr.pinguet62.jsfring.gui.htmlunit.ChangePasswordPage;
import fr.pinguet62.jsfring.model.sql.QUser;
import fr.pinguet62.jsfring.model.sql.User;
import fr.pinguet62.jsfring.service.UserService;
import fr.pinguet62.jsfring.util.PasswordGenerator;

/** @see ChangePasswordPage */
@SpringRequestScoped
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
@DatabaseSetup(DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class,
        WithSecurityContextTestExecutionListener.class })
public class ChangePasswordPageITTest {

    private ChangePasswordPage page;

    @Inject
    private UserService userService;

    /** Page requires login. */
    @Before
    public void before() {
        page = get().gotoChangePasswordPage();
    }

    private User getUser() {
        return userService.findAll(QUser.user.login.eq(getCurrent().getUsername())).get(0);
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
        assertThat(userService.get(getUser().getLogin()).getPassword(), is(equalTo(newPassword)));
    }

    /** The confirm password doesn't match to the new password. */
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

    /** The confirm password doesn't match to the new password. */
    @Test
    @WithAnyUser
    public void test_invalidCurrent() {
        String currentPassword = randomUUID().toString();
        assertThat(currentPassword, not(matches(getUser().getPassword())));

        String newPassword = new PasswordGenerator().get();

        page.setActualPassword(currentPassword);
        page.setNewPassword(newPassword);
        page.setConfirmPassword(newPassword);
        page.submit();

        assertThat(page.getMessageError(), is(not(nullValue())));
    }

    /** The confirm password doesn't match to the new password. */
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