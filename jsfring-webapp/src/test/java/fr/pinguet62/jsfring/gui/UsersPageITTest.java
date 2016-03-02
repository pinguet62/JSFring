package fr.pinguet62.jsfring.gui;

import static fr.pinguet62.jsfring.gui.htmlunit.AbstractPage.get;
import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.util.MatcherUtils.equalWithoutOrderTo;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Random;

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
import fr.pinguet62.jsfring.gui.htmlunit.user.UserRow;
import fr.pinguet62.jsfring.gui.htmlunit.user.UsersPage;
import fr.pinguet62.jsfring.gui.htmlunit.user.popup.UserCreatePopup;
import fr.pinguet62.jsfring.gui.htmlunit.user.popup.UserShowPopup;
import fr.pinguet62.jsfring.gui.htmlunit.user.popup.UserUpdatePopup;
import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.model.sql.User;

/** @see UsersPage */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringBootConfig.class)
@WebIntegrationTest
@DatabaseSetup(DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class UsersPageITTest {

    private UsersPage page;

    @Inject
    private ProfileDao profileDao;

    @Inject
    private UserDao userDao;

    @Before
    public void before() {
        page = get().gotoUsersPage();
    }

    @Test
    public void test_action_create() {
        // Data
        String login = randomUUID().toString().substring(0, 20);
        String email = "ramdon@domail.org";
        boolean active = new Random().nextBoolean();
        List<Profile> profiles = profileDao.findAll().stream().limit(2).collect(toList());

        // Fill fields
        UserCreatePopup popup = page.actionCreate();
        popup.getLogin().setValue(login);
        popup.getEmail().setValue(email);
        popup.getActive().setValue(active);
        popup.getProfiles().setValue(profiles.stream().map(Profile::getTitle).collect(toList()));
        popup.submit();

        // Check
        User user = userDao.findOne(login);
        assertThat(user, is(not(nullValue())));
        assertThat(user.getLogin(), is(equalTo(login)));
        assertThat(user.getEmail(), is(equalTo(email)));
        assertThat(user.getActive(), is(equalTo(active)));
        assertThat(user.getProfiles(), is(equalWithoutOrderTo(profiles)));
    }

    @Test
    public void test_action_create_field_readonly() {
        UserCreatePopup popup = page.actionCreate();

        assertThat(popup.getLogin().isReadonly(), is(false));
        assertThat(popup.getEmail().isReadonly(), is(false));
        assertThat(popup.getActive().isReadonly(), is(false));
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
    public void test_action_show_field_readonly() {
        UserShowPopup popup = page.iterator().next().actionShow();
        assertThat(popup.getLogin().isReadonly(), is(true));
        assertThat(popup.getEmail().isReadonly(), is(true));
        assertThat(popup.isActive().isReadonly(), is(true));
        assertThat(popup.getLastConnection().isReadonly(), is(true));
        assertThat(popup.getProfiles().isReadonly(), is(true));
    }

    @Test
    public void test_action_show_field_value() {
        UserRow row = page.iterator().next();
        UserShowPopup popup = row.actionShow();

        User user = userDao.findOne(row.getLogin());
        assertThat(popup.getLogin().getValue(), is(equalTo(user.getLogin())));
        assertThat(popup.getEmail().getValue(), is(equalTo(user.getEmail())));
        assertThat(popup.isActive().getValue(), is(equalTo(user.getActive())));
        if (user.getLastConnection() == null)
            assertThat(popup.getLastConnection().getValue(), is(nullValue()));
        else
            assertThat(popup.getLastConnection().getValue(), is(equalTo(user.getLastConnection())));
        assertThat(popup.getProfiles().getValue(),
                is(equalWithoutOrderTo(user.getProfiles().stream().map(Profile::getTitle).collect(toList()))));
    }

    @Test
    public void test_action_update() {
        UserRow row = page.iterator().next();
        UserUpdatePopup popup = row.actionUpdate();

        // Data
        String login = row.getLogin();
        String email = "ramdon@domail.org";
        boolean active = !row.isActive();
        List<Profile> profiles = profileDao.findAll().stream().limit(2).collect(toList());

        // Fill fields
        popup.getEmail().setValue(email);
        popup.getActive().setValue(active);
        popup.getProfiles().setValue(profiles.stream().map(Profile::getTitle).collect(toList()));
        popup.submit();

        // Check
        User user = userDao.findOne(login);
        assertThat(user.getLogin(), is(equalTo(login)));
        assertThat(user.getEmail(), is(equalTo(email)));
        assertThat(user.getActive(), is(equalTo(active)));
        assertThat(user.getProfiles(), is(equalWithoutOrderTo(profiles)));
    }

    @Test
    public void test_action_update_field_readonly() {
        UserUpdatePopup popup = page.iterator().next().actionUpdate();

        assertThat(popup.getLogin().isReadonly(), is(true));
        assertThat(popup.getEmail().isReadonly(), is(false));
        assertThat(popup.getActive().isReadonly(), is(false));
        assertThat(popup.getLastConnection().isReadonly(), is(true));
        assertThat(popup.getProfiles().isReadonly(), is(false));
    }

    @Test
    public void test_action_update_field_value() {
        UserRow row = page.iterator().next();
        UserUpdatePopup popup = row.actionUpdate();

        User user = userDao.findOne(row.getLogin());
        assertThat(popup.getLogin().getValue(), is(equalTo(user.getLogin())));
        assertThat(popup.getEmail().getValue(), is(equalTo(user.getEmail())));
        assertThat(popup.getActive().getValue(), is(equalTo(user.getActive())));
        if (user.getLastConnection() == null)
            assertThat(popup.getLastConnection().getValue(), is(nullValue()));
        else
            assertThat(popup.getLastConnection().getValue(), is(equalTo(user.getLastConnection())));
        assertThat(popup.getProfiles().getValue(),
                is(equalWithoutOrderTo(user.getProfiles().stream().map(Profile::getTitle).collect(toList()))));
    }

}