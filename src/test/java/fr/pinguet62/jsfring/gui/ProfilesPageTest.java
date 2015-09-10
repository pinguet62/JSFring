package fr.pinguet62.jsfring.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.profile.ProfilesPage;
import fr.pinguet62.jsfring.gui.htmlunit.profile.ProfileRow;

/** @see ProfilesPage */
public final class ProfilesPageTest {

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
        List<ProfileRow> rows = AbstractPage.get().gotoProfilesPage().getRows();
        assertEquals("Profile admin", rows.get(0).getTitle());
        assertEquals("User admin", rows.get(1).getTitle());
    }

}