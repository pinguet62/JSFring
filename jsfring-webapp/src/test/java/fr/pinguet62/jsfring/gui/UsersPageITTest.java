package fr.pinguet62.jsfring.gui;

import static fr.pinguet62.jsfring.gui.htmlunit.AbstractPage.get;
import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.inject.Inject;

import org.junit.Before;
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
import fr.pinguet62.jsfring.dao.sql.ProfileDao;
import fr.pinguet62.jsfring.dao.sql.UserDao;
import fr.pinguet62.jsfring.gui.htmlunit.field.Field;
import fr.pinguet62.jsfring.gui.htmlunit.user.UserRow;
import fr.pinguet62.jsfring.gui.htmlunit.user.UsersPage;
import fr.pinguet62.jsfring.gui.htmlunit.user.popup.UserCreatePopup;
import fr.pinguet62.jsfring.gui.htmlunit.user.popup.UserShowPopup;
import fr.pinguet62.jsfring.gui.htmlunit.user.popup.UserUpdatePopup;
import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.model.sql.QUser;
import fr.pinguet62.jsfring.model.sql.User;

/** @see UsersPage */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringBootConfig.class)
@WebIntegrationTest
@DatabaseSetup(DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class UsersPageITTest {

    @Inject
    private UserDao dao;

    private UsersPage page;

    @Inject
    private ProfileDao profileDao;

    @Before
    public void before() {
        page = get().gotoUsersPage();
    }

    @Test
    public void test_action_create() {
        // Data
        String login = UUID.randomUUID().toString();
        String email = "ramdon@domail.org";
        boolean active = new Random().nextBoolean();
        List<Profile> profiles = profileDao.findAll().stream().limit(2).collect(toList());

        // Fill fields
        UserCreatePopup popup = page.actionCreate();
        popup.getLogin().setValue(login);
        popup.getActive().setValue(active);
        popup.getProfiles().setValue(profiles.stream().map(Profile::getId).map(String::valueOf).collect(toList()));
        popup.submit();

        // Check
        User user = dao.findOne(QUser.user.login.eq(login));
        assertThat(user.getLogin(), is(equalTo(login)));
        assertThat(user.getEmail(), is(equalTo(email)));
        assertThat(user.getActive(), is(equalTo(active)));
        assertThat(user.getProfiles(), is(equalTo(profiles)));
    }

    @Test
    public void test_action_create_field_readonly() {
        UserCreatePopup popup = page.actionCreate();

        assertThat(popup.getLogin().isReadonly(), is(true));
        assertThat(popup.getEmail().isReadonly(), is(false));
        assertThat(popup.getActive().isReadonly(), is(false));
        assertThat(popup.getLastConnection().isReadonly(), is(true));
        assertThat(popup.getProfiles().isReadonly(), is(false));
    }

    @Test
    public void test_action_rendered() {
        assertThat(page.isCreateButtonVisible(), is(true));
        for (UserRow row : page.getRows()) {
            assertThat(row.isActionButtonShowVisible(), is(true));
            assertThat(row.isActionButtonUpdateVisible(), is(true));
            assertThat(row.isActionButtonDeleteVisible(), is(true));
        }
    }

    @Test
    public void test_action_update_field_readonly() {
        UserUpdatePopup popup = page.iterator().next().actionUpdate();

        assertThat(popup.getLogin().isReadonly(), is(true));
        assertThat(popup.getEmail().isReadonly(), is(false));
        assertThat(popup.isActive().isReadonly(), is(false));
        assertThat(popup.getLastConnection().isReadonly(), is(true));
        assertThat(popup.getProfiles().isReadonly(), is(false));
    }

    @Test
    public void test_action_update_field_value() {
        UserRow row = page.iterator().next();
        UserUpdatePopup popup = row.actionUpdate();
        User user = dao.findOne(row.getLogin());

        assertThat(popup.getLogin().getValue(), is(equalTo(user.getLogin())));
        assertThat(popup.getEmail().getValue(), is(equalTo(user.getEmail())));
        assertThat(popup.isActive().getValue(), is(equalTo(user.getActive())));
        if (user.getLastConnection() == null)
            assertThat(popup.getLastConnection().getValue(), is(nullValue()));
        else
            assertThat(popup.getLastConnection().getValue(), is(equalTo(user.getLastConnection())));
        assertThat(popup.getProfiles().getValue(), is(equalTo(user.getProfiles())));
    }

    @Test
    public void test_popup_show() {
        UserRow row = page.iterator().next();
        User user = dao.findOne(row.getLogin());
        UserShowPopup popup = row.actionShow();

        Field<?, ?> loginField = popup.getLogin();
        assertThat(loginField.isReadonly(), is(true));
        assertThat(loginField.getValue(), is(equalTo(user.getLogin())));

        Field<?, ?> emailField = popup.getEmail();
        assertThat(emailField.isReadonly(), is(true));
        assertThat(emailField.getValue(), is(equalTo(user.getEmail())));

        Field<?, ?> activeField = popup.isActive();
        assertThat(activeField.isReadonly(), is(true));
        assertThat(activeField.getValue(), is(equalTo(user.getActive())));

        Field<?, ?> lastConnectionField = popup.getLastConnection();
        assertThat(lastConnectionField.isReadonly(), is(true));
        if (user.getLastConnection() == null)
            assertThat(lastConnectionField.getValue(), is(nullValue()));
        else
            assertThat(lastConnectionField.getValue(), is(equalTo(user.getLastConnection())));

        Field<?, ?> profilesField = popup.getProfiles();
        assertThat(profilesField.isReadonly(), is(true));
        assertThat(profilesField.getValue(), is(equalTo(user.getProfiles())));
    }

}