package fr.pinguet62.jsfring.gui;

import static fr.pinguet62.jsfring.gui.htmlunit.DateUtils.getDatetime;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import au.com.bytecode.opencsv.CSVReader;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.lowagie.text.pdf.PdfReader;

import fr.pinguet62.Config;
import fr.pinguet62.jsfring.dao.UserDao;
import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.datatable.AbstractDatatablePage;
import fr.pinguet62.jsfring.gui.htmlunit.datatable.AbstractRow;
import fr.pinguet62.jsfring.gui.htmlunit.datatable.popup.ConfirmPopup;
import fr.pinguet62.jsfring.gui.htmlunit.datatable.popup.UpdatePopup;
import fr.pinguet62.jsfring.gui.htmlunit.user.UserRow;
import fr.pinguet62.jsfring.gui.htmlunit.user.UsersPage;
import fr.pinguet62.jsfring.gui.htmlunit.user.UsersPage.ActiveFilter;
import fr.pinguet62.jsfring.gui.htmlunit.user.UsersPage.Column;
import fr.pinguet62.jsfring.gui.htmlunit.user.popup.UserShowPopup;
import fr.pinguet62.jsfring.gui.htmlunit.user.popup.UserUpdatePopup;
import fr.pinguet62.jsfring.model.User;

/** @see UsersPage */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = Config.SPRING)
@DatabaseSetup(Config.DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class UsersPageTest {

    @Inject
    private UserDao userDao;

    /**
     * @see AbstractRow#actionDelete()
     * @see ConfirmPopup
     * @see ConfirmPopup#cancel()
     */
    @Test
    public void test_dataTable_action_delete_cancel() {
        UsersPage usersPage = AbstractPage.get().gotoUsersPage();

        // Before
        List<UserRow> rows = usersPage.getRows();
        UserRow row0 = rows.get(0);
        String row0Value = row0.getLogin();
        String row1Value = rows.get(1).getLogin();
        assertNotEquals(row0Value, row1Value);

        row0.actionDelete().cancel();

        // After: the 1st row is yet present
        assertEquals(row0Value, usersPage.getRows().get(0).getLogin());
    }

    /**
     * @see AbstractRow#actionDelete()
     * @see ConfirmPopup
     * @see ConfirmPopup#confirm()
     */
    @Test
    public void test_dataTable_action_delete_confirm() {
        UsersPage usersPage = AbstractPage.get().gotoUsersPage();

        // Before: 1st and 2nd rows are different
        List<UserRow> rows = usersPage.getRows();
        UserRow row0 = rows.get(0);
        String row1Value = rows.get(1).getLogin();
        assertNotEquals(row0.getLogin(), row1Value);

        row0.actionDelete().confirm();

        // After: the 2nd row is now the 1st
        assertEquals(row1Value, usersPage.getRows().get(0).getLogin());
    }

    @Test
    public void test_dataTable_action_rendered() {
        UsersPage usersPage = AbstractPage.get().gotoUsersPage();

        assertTrue(usersPage.isCreateButtonVisible());
        for (UserRow row : usersPage.getRows()) {
            assertTrue(row.isActionButtonShowVisible());
            assertTrue(row.isActionButtonUpdateVisible());
            assertTrue(row.isActionButtonDeleteVisible());
        }
    }

    /**
     * <ul>
     * <li>Each row must be different</li>
     * <li>Each page must be different</li>
     * </ul>
     *
     * @see AbstractRow#actionShow()
     */
    @Test
    public void test_dataTable_action_show() {
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

                assertEquals(true, popup.isActive().getValue());
                assertTrue(popup.isActive().isReadonly());

                assertEquals(getDatetime(2015, 6, 14, 13, 45, 41), popup.getLastConnection().getValue());
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

                assertEquals(true, popup.isActive().getValue());
                assertTrue(popup.isActive().isReadonly());

                assertNull(popup.getLastConnection().getValue());
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

                assertEquals(true, popup.isActive().getValue());
                assertTrue(popup.isActive().isReadonly());

                assertNull(popup.getLastConnection().getValue());
                assertTrue(popup.getLastConnection().isReadonly());

                assertEquals(Arrays.asList("User admin"), popup.getProfiles().getValue());
                assertTrue(popup.getProfiles().isReadonly());

                popup.close();
            }
        }
    }

    /**
     * @see AbstractRow#actionUpdate()
     * @see UpdatePopup#submit()
     */
    @Test
    public void test_dataTable_action_update_submit() {
        UsersPage page = AbstractPage.get().gotoUsersPage();

        // Before
        UserRow row = page.getRows().get(0);
        assertEquals("admin@domain.fr", row.getEmail());

        // Update
        final String newEmail = "new@value.ap";
        UserUpdatePopup updatePopup = row.actionUpdate();
        updatePopup.getEmail().setValue(newEmail);
        updatePopup.submit();

        // Before
        assertEquals(newEmail, page.getRows().get(0).getEmail());
    }

    /** @see AbstractRow#actionUpdate() */
    @Test
    public void test_dataTable_action_update_values() {
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

                assertEquals(true, popup.isActive().getValue());
                assertFalse(popup.isActive().isReadonly());

                assertEquals(getDatetime(2015, 6, 14, 13, 45, 41), popup.getLastConnection().getValue());
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

                assertEquals(true, popup.isActive().getValue());
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

                assertEquals(true, popup.isActive().getValue());
                assertFalse(popup.isActive().isReadonly());

                assertNull(popup.getLastConnection().getValue());
                assertTrue(popup.getLastConnection().isReadonly());

                assertEquals(Arrays.asList("User admin"), popup.getProfiles().getValue());
                assertFalse(popup.getProfiles().isReadonly());

                popup.close();
            }
        }
    }

    /** @see UsersPage#hideOrShowColumn(Column) */
    @Test
    public void test_dataTable_columnToggler() {
        UsersPage usersPage = AbstractPage.get().gotoUsersPage();
        for (Column column : UsersPage.Column.values())
            usersPage.hideOrShowColumn(column);
    }

    /** @see AbstractDatatablePage#exportCSV() */
    @Test
    public void test_dataTable_export_csv() throws IOException {
        UsersPage usersPage = AbstractPage.get().gotoUsersPage();
        InputStream is = usersPage.exportCSV();
        // Try read
        try (CSVReader reader = new CSVReader(new InputStreamReader(is))) {
            reader.readAll();
        }
    }

    /** @see AbstractDatatablePage#exportPDF() */
    @Test
    public void test_dataTable_export_pdf() throws IOException {
        UsersPage usersPage = AbstractPage.get().gotoUsersPage();
        InputStream is = usersPage.exportPDF();
        // Try read
        new PdfReader(is);
    }

    /** @see AbstractDatatablePage#exportXLS() */
    @Test
    public void test_dataTable_export_xls() throws IOException {
        UsersPage usersPage = AbstractPage.get().gotoUsersPage();
        InputStream is = usersPage.exportXLS();
        // TODO Try read
        assertNotNull(is);
    }

    /** @see AbstractDatatablePage#exportXML() */
    @Test
    public void test_dataTable_export_xml() throws Exception {
        UsersPage usersPage = AbstractPage.get().gotoUsersPage();
        InputStream is = usersPage.exportXML();
        // Try read
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.parse(is);
    }

    /** @see UsersPage#filterByActive(ActiveFilter) */
    @Test
    public void test_dataTable_filter_email() {
        UsersPage usersPage = AbstractPage.get().gotoUsersPage();

        assertEquals(3, usersPage.getTotalCount());

        usersPage.filterByActive(ActiveFilter.Yes);
        assertEquals(3, usersPage.getTotalCount());

        usersPage.filterByActive(ActiveFilter.No);
        assertEquals(0, usersPage.getTotalCount());

        usersPage.filterByActive(ActiveFilter.All);
        assertEquals(3, usersPage.getTotalCount());
    }

    /** @see AbstractDatatablePage#getRows() */
    @Test
    public void test_dataTable_getRows() {
        List<User> users = userDao.getAll();
        List<UserRow> rows = AbstractPage.get().gotoUsersPage().getRows();

        for (int i = 0; i < 2; i++) {
            User user = users.get(i);
            UserRow row = rows.get(i);

            assertEquals(user.getLogin(), row.getLogin());
            assertEquals(user.getEmail(), row.getEmail());
            assertEquals(user.isActive(), row.getActive());
            assertEquals(user.getLastConnection(), row.getLastConnection());
        }
    }

    /** @see AbstractDatatablePage#getTotalCount() */
    @Test
    public void test_datatable_getTotalCount() {
        AbstractPage nav = AbstractPage.get();
        assertEquals(3, nav.gotoUsersPage().getTotalCount());
        assertEquals(3, nav.gotoProfilesPage().getTotalCount());
        assertEquals(5, nav.gotoRightsPage().getTotalCount());
    }

    /** @see UsersPage#sortByEmail() */
    @Test
    public void test_dataTable_sort_email() {
        List<String> emails = userDao.getAll().stream().map(User::getEmail).sorted().collect(toList());

        UsersPage usersPage = AbstractPage.get().gotoUsersPage();
        usersPage.sortByEmail();
        List<UserRow> rows = usersPage.getRows();

        for (int i = 0; i < 2; i++) {
            assertEquals(emails.get(i), rows.get(i).getEmail());
        }
    }

    /** @see UsersPage#sortByLogin() */
    @Test
    public void test_dataTable_sort_login() {
        List<String> logins = userDao.getAll().stream().map(User::getLogin).sorted().collect(toList());

        UsersPage usersPage = AbstractPage.get().gotoUsersPage();
        usersPage.sortByLogin();
        List<UserRow> rows = usersPage.getRows();

        for (int i = 0; i < 2; i++) {
            assertEquals(logins.get(i), rows.get(i).getLogin());
        }
    }

}