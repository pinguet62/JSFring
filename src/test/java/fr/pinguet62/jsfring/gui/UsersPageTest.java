package fr.pinguet62.jsfring.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.Config;
import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.datatable.AbstractRow;
import fr.pinguet62.jsfring.gui.htmlunit.user.UserRow;
import fr.pinguet62.jsfring.gui.htmlunit.user.UsersPage;
import fr.pinguet62.jsfring.gui.htmlunit.user.popup.UserShowPopup;
import fr.pinguet62.jsfring.gui.htmlunit.user.popup.UserUpdatePopup;

/** @see UsersPage */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = Config.SPRING)
@DatabaseSetup(Config.DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class UsersPageTest {

    /**
     * <ul>
     * <li>Each row must be different</li>
     * <li>Each page must be different</li>
     * </ul>
     *
     * @see AbstractRow#actionShow()
     */
    @Test
    public void test_action_show() {
        UsersPage page = AbstractPage.get().gotoUsersPage();
        {
            // Page 1
            List<UserRow> rows = page.getRows();
            {
                UserShowPopup popup = rows.get(0).actionShow();

                assertEquals("super admin", popup.getLogin().getValue());
                assertTrue(popup.getLogin().isReadonly());

                assertEquals("admin@domain.fr", popup.getEmail().getValue());
                assertTrue(popup.getEmail().isReadonly());

                assertEquals("true", popup.isActive().getValue());
                assertTrue(popup.isActive().isReadonly());

                assertEquals("2015-06-14", popup.getLastConnection().getValue());
                assertTrue(popup.getLastConnection().isReadonly());

                assertEquals(Arrays.asList("Profile admin", "User admin"), popup.getProfiles().getValue());
                assertTrue(popup.getProfiles().isReadonly());

                popup.close();
            }

            {
                UserShowPopup popup = rows.get(1).actionShow();

                assertEquals("admin profile", popup.getLogin().getValue());
                assertTrue(popup.getLogin().isReadonly());

                assertEquals("admin_profile@domain.fr", popup.getEmail().getValue());
                assertTrue(popup.getEmail().isReadonly());

                assertEquals("true", popup.isActive().getValue());
                assertTrue(popup.isActive().isReadonly());

                assertEquals("", popup.getLastConnection().getValue());
                assertTrue(popup.getLastConnection().isReadonly());

                assertEquals(Arrays.asList("Profile admin"), popup.getProfiles().getValue());
                assertTrue(popup.getProfiles().isReadonly());

                popup.close();
            }
        }

        page.gotoNextPage();
        {
            // Page 2
            List<UserRow> rows = page.getRows();
            {
                UserShowPopup popup = rows.get(0).actionShow();

                assertEquals("admin user", popup.getLogin().getValue());
                assertTrue(popup.getLogin().isReadonly());

                assertEquals("admin_user@domain.fr", popup.getEmail().getValue());
                assertTrue(popup.getEmail().isReadonly());

                assertEquals("true", popup.isActive().getValue());
                assertTrue(popup.isActive().isReadonly());

                assertEquals("", popup.getLastConnection().getValue());
                assertTrue(popup.getLastConnection().isReadonly());

                assertEquals(Arrays.asList("User admin"), popup.getProfiles().getValue());
                assertTrue(popup.getProfiles().isReadonly());

                popup.close();
            }
        }
    }

    /** @see AbstractRow#actionUpdate() */
    @Test
    public void test_action_update() {
        UsersPage page = AbstractPage.get().gotoUsersPage();
        {
            // Page 1
            List<UserRow> rows = page.getRows();
            {
                UserUpdatePopup popup = rows.get(0).actionUpdate();

                assertEquals("super admin", popup.getLogin().getValue());
                assertTrue(popup.getLogin().isReadonly());

                assertEquals("admin@domain.fr", popup.getEmail().getValue());
                assertFalse(popup.getEmail().isReadonly());

                assertEquals("true", popup.isActive().getValue());
                assertFalse(popup.isActive().isReadonly());

                assertEquals("2015-06-14", popup.getLastConnection().getValue());
                assertTrue(popup.getLastConnection().isReadonly());

                assertEquals(Arrays.asList("Profile admin", "User admin"), popup.getProfiles().getValue());
                assertFalse(popup.getProfiles().isReadonly());

                popup.close();
            }
            {
                UserUpdatePopup popup = rows.get(1).actionUpdate();

                assertEquals("admin profile", popup.getLogin().getValue());
                assertTrue(popup.getLogin().isReadonly());

                assertEquals("admin_profile@domain.fr", popup.getEmail().getValue());
                assertFalse(popup.getEmail().isReadonly());

                assertEquals("true", popup.isActive().getValue());
                assertFalse(popup.isActive().isReadonly());

                assertNull(popup.getLastConnection().getValue());
                assertTrue(popup.getLastConnection().isReadonly());

                assertEquals(Arrays.asList("Profile admin"), popup.getProfiles().getValue());
                assertFalse(popup.getProfiles().isReadonly());

                popup.close();
            }
        }
        page.gotoNextPage();
        {
            // Page 2
            List<UserRow> rows = page.getRows();
            {
                UserUpdatePopup popup = rows.get(0).actionUpdate();

                assertEquals("admin user", popup.getLogin().getValue());
                assertTrue(popup.getLogin().isReadonly());

                assertEquals("admin_user@domain.fr", popup.getEmail().getValue());
                assertFalse(popup.getEmail().isReadonly());

                assertEquals("true", popup.isActive().getValue());
                assertFalse(popup.isActive().isReadonly());

                assertNull(popup.getLastConnection().getValue());
                assertTrue(popup.getLastConnection().isReadonly());

                assertEquals(Arrays.asList("User admin"), popup.getProfiles().getValue());
                assertFalse(popup.getProfiles().isReadonly());

                popup.close();
            }
        }
    }

    @Test
    public void test_dataTable_actions_rendered() {
        UsersPage usersPage = AbstractPage.get().gotoUsersPage();

        assertTrue(usersPage.isCreateButtonVisible());
        for (UserRow row : usersPage.getRows()) {
            assertTrue(row.isActionButtonShowVisible());
            assertTrue(row.isActionButtonUpdateVisible());
            assertTrue(row.isActionButtonDeleteVisible());
        }
    }

    @Test
    public void test_dataTable_content() {
        List<UserRow> rows = AbstractPage.get().gotoUsersPage().getRows();

        {
            UserRow row0 = rows.get(0);
            assertEquals("super admin", row0.getLogin());
            assertEquals("admin@domain.fr", row0.getEmail());
            assertTrue(row0.getActive());
            assertEquals(new Date(2015 - 1900, 6 - 1, 14), row0.getLastConnection());
        }
        {
            UserRow row1 = rows.get(1);
            assertEquals("admin profile", row1.getLogin());
            assertEquals("admin_profile@domain.fr", row1.getEmail());
            assertTrue(row1.getActive());
            assertNull(row1.getLastConnection());
        }
    }

}