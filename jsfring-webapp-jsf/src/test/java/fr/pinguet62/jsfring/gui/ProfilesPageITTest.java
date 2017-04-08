package fr.pinguet62.jsfring.gui;

import static fr.pinguet62.jsfring.gui.htmlunit.AbstractPage.get;
import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.util.MatcherUtils.equalWithoutOrderTo;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.dao.sql.ProfileDao;
import fr.pinguet62.jsfring.dao.sql.RightDao;
import fr.pinguet62.jsfring.gui.htmlunit.profile.ProfileRow;
import fr.pinguet62.jsfring.gui.htmlunit.profile.ProfilesPage;
import fr.pinguet62.jsfring.gui.htmlunit.profile.popup.ProfileCreatePopup;
import fr.pinguet62.jsfring.gui.htmlunit.profile.popup.ProfileShowPopup;
import fr.pinguet62.jsfring.gui.htmlunit.profile.popup.ProfileUpdatePopup;
import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.model.sql.QProfile;
import fr.pinguet62.jsfring.model.sql.Right;

/** @see ProfilesPage */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
//DbUnit
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup(DATASET)
public final class ProfilesPageITTest {

    private ProfilesPage page;

    @Inject
    private ProfileDao profileDao;

    @Inject
    private RightDao rightDao;

    @Before
    public void before() {
        page = get().gotoProfilesPage();
    }

    @Test
    public void test_action_create() {
        // Data
        String title = randomUUID().toString().substring(0, 29);
        List<Right> rights = rightDao.findAll().stream().limit(2).collect(toList());

        // Fill fields
        ProfileCreatePopup popup = page.actionCreate();
        popup.getTitle().setValue(title);
        popup.getRights().setValue(rights.stream().map(Right::getTitle).collect(toList()));
        popup.submit();

        // Check
        Profile lastProfile = profileDao.findAll(QProfile.profile.id.desc()).get(0);
        assertThat(lastProfile.getTitle(), is(equalTo(title)));
        assertThat(lastProfile.getRights(), is(equalWithoutOrderTo(rights)));
    }

    @Test
    public void test_action_create_field_readonly() {
        ProfileCreatePopup popup = page.actionCreate();
        assertThat(popup.getTitle().isReadonly(), is(false));
        assertThat(popup.getRights().isReadonly(), is(false));
    }

    @Test
    public void test_action_rendered() {
        assertThat(page.isCreateButtonVisible(), is(true));
        for (ProfileRow row : page.getRows()) {
            assertThat(row.isActionButtonShowVisible(), is(true));
            assertThat(row.isActionButtonUpdateVisible(), is(true));
            assertThat(row.isActionButtonDeleteVisible(), is(true));
        }
    }

    @Test
    public void test_action_show_field_readonly() {
        ProfileShowPopup popup = page.iterator().next().actionShow();
        assertThat(popup.getTitle().isReadonly(), is(true));
        assertThat(popup.getRights().isReadonly(), is(true));
    }

    @Test
    public void test_action_show_field_value() {
        ProfileRow row = page.iterator().next();
        Profile profile = profileDao.findOne(QProfile.profile.title.eq(row.getTitle()));

        ProfileShowPopup popup = row.actionShow();
        assertThat(popup.getTitle().getValue(), is(equalTo(profile.getTitle())));
        assertThat(popup.getRights().getValue(),
                is(equalWithoutOrderTo(profile.getRights().stream().map(Right::getTitle).collect(toList()))));
    }

    @Test
    public void test_action_update() {
        ProfileRow row = page.iterator().next();
        ProfileUpdatePopup popup = row.actionUpdate();

        // Data
        int id = profileDao.findOne(QProfile.profile.title.eq(row.getTitle())).getId();
        String title = randomUUID().toString().substring(0, 29);
        List<Right> rights = rightDao.findAll().stream().limit(2).collect(toList());

        // Fill fields
        popup.getTitle().setValue(title);
        popup.getRights().setValue(rights.stream().map(Right::getTitle).collect(toList()));
        popup.submit();

        // Check
        Profile profile = profileDao.findOne(id);
        assertThat(profile.getTitle(), is(equalTo(title)));
        assertThat(profile.getRights(), is(equalWithoutOrderTo(rights)));
    }

    @Test
    public void test_action_update_field_readonly() {
        ProfileUpdatePopup popup = page.iterator().next().actionUpdate();
        assertThat(popup.getTitle().isReadonly(), is(false));
        assertThat(popup.getRights().isReadonly(), is(false));
    }

    @Test
    public void test_action_update_field_value() {
        ProfileRow row = page.iterator().next();
        Profile profile = profileDao.findOne(QProfile.profile.title.eq(row.getTitle()));

        ProfileUpdatePopup popup = row.actionUpdate();
        assertThat(popup.getTitle().getValue(), is(equalTo(profile.getTitle())));
        assertThat(popup.getRights().getValue(),
                is(equalWithoutOrderTo(profile.getRights().stream().map(Right::getTitle).collect(toList()))));
    }

}