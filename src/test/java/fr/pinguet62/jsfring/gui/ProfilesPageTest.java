package fr.pinguet62.jsfring.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import fr.pinguet62.Config;
import fr.pinguet62.jsfring.dao.ProfileDao;
import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.profile.ProfileRow;
import fr.pinguet62.jsfring.gui.htmlunit.profile.ProfilesPage;
import fr.pinguet62.jsfring.model.Profile;

/** @see ProfilesPage */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = Config.SPRING)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
public final class ProfilesPageTest {

    @Inject
    private ProfileDao profileDao;

    @Test
    public void test_dataTable_actions() {
        ProfilesPage profilesPage = AbstractPage.get().gotoProfilesPage();

        assertTrue(profilesPage.isCreateButtonVisible());
        for (ProfileRow row : profilesPage.getRows()) {
            assertTrue(row.isActionButtonShowVisible());
            assertTrue(row.isActionButtonUpdateVisible());
            assertTrue(row.isActionButtonDeleteVisible());
        }
    }

    @Test
    public void test_dataTable_content() {
        List<Profile> profiles = profileDao.getAll();
        List<ProfileRow> rows = AbstractPage.get().gotoProfilesPage().getRows();

        assertEquals(profiles.get(0).getTitle(), rows.get(0).getTitle());
        assertEquals(profiles.get(1).getTitle(), rows.get(1).getTitle());
    }

}