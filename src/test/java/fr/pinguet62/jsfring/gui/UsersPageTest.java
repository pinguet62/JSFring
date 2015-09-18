package fr.pinguet62.jsfring.gui;

import static fr.pinguet62.jsfring.gui.htmlunit.DateUtils.equalsSecond;
import static fr.pinguet62.jsfring.gui.htmlunit.user.UsersPage.Column.EMAIL;
import static fr.pinguet62.jsfring.gui.htmlunit.user.UsersPage.Column.LOGIN;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

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
import com.mysema.query.jpa.impl.JPAQuery;

import fr.pinguet62.Config;
import fr.pinguet62.jsfring.dao.ProfileDao;
import fr.pinguet62.jsfring.dao.RightDao;
import fr.pinguet62.jsfring.dao.UserDao;
import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.datatable.AbstractDatatablePage;
import fr.pinguet62.jsfring.gui.htmlunit.datatable.AbstractRow;
import fr.pinguet62.jsfring.gui.htmlunit.datatable.popup.ConfirmPopup;
import fr.pinguet62.jsfring.gui.htmlunit.datatable.popup.DetailsPopup;
import fr.pinguet62.jsfring.gui.htmlunit.datatable.popup.Popup;
import fr.pinguet62.jsfring.gui.htmlunit.datatable.popup.ShowPopup;
import fr.pinguet62.jsfring.gui.htmlunit.datatable.popup.UpdatePopup;
import fr.pinguet62.jsfring.gui.htmlunit.field.Field;
import fr.pinguet62.jsfring.gui.htmlunit.user.UserRow;
import fr.pinguet62.jsfring.gui.htmlunit.user.UsersPage;
import fr.pinguet62.jsfring.gui.htmlunit.user.UsersPage.ActiveFilter;
import fr.pinguet62.jsfring.gui.htmlunit.user.UsersPage.Column;
import fr.pinguet62.jsfring.gui.htmlunit.user.popup.UserShowPopup;
import fr.pinguet62.jsfring.gui.htmlunit.user.popup.UserUpdatePopup;
import fr.pinguet62.jsfring.model.Profile;
import fr.pinguet62.jsfring.model.QUser;
import fr.pinguet62.jsfring.model.User;

/** @see UsersPage */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = Config.SPRING)
@DatabaseSetup(Config.DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class UsersPageTest {

    @Inject
    private ProfileDao profileDao;

    @Inject
    private RightDao rightDao;

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

        final int initialCount = usersPage.getTotalCount();

        usersPage.getRows().get(0).actionDelete().cancel();

        assertEquals(initialCount, usersPage.getTotalCount());
    }

    /**
     * @see AbstractRow#actionDelete()
     * @see ConfirmPopup
     * @see ConfirmPopup#confirm()
     */
    @Test
    public void test_dataTable_action_delete_confirm() {
        UsersPage usersPage = AbstractPage.get().gotoUsersPage();

        final int initialCount = usersPage.getTotalCount();

        usersPage.getRows().get(0).actionDelete().confirm();

        assertEquals(initialCount - 1, usersPage.getTotalCount());
    }

    /**
     * Visibility of action buttons.
     *
     * @see AbstractRow#isActionButtonShowVisible()
     * @see AbstractRow#isActionButtonUpdateVisible()
     * @see AbstractRow#isActionButtonDeleteVisible()
     */
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
     * {@link DetailsPopup Details} of {@link ShowPopup show popup} must be the
     * same than Database content.
     * <p>
     * Check if {@link Field}s are not updatable.
     *
     * @see AbstractRow#actionShow()
     * @see Popup#close()
     */
    @Test
    public void test_dataTable_action_show() {
        List<User> users = userDao.getAll();

        UsersPage page = AbstractPage.get().gotoUsersPage();

        int i = 0;
        int p = 0;

        while (true) {
            List<UserRow> rows = page.getRows();

            for (int r = 0; r < 2/* page size */&& i < users.size(); r++, i++) {
                User user = users.get(i);
                UserShowPopup popup = rows.get(r).actionShow();

                assertTrue(popup.getLogin().isReadonly());
                assertEquals(user.getLogin(), popup.getLogin().getValue());

                assertTrue(popup.getEmail().isReadonly());
                assertEquals(user.getEmail(), popup.getEmail().getValue());

                assertTrue(popup.isActive().isReadonly());
                assertEquals(user.isActive(), popup.isActive().getValue());

                assertTrue(popup.getLastConnection().isReadonly());
                assertTrue(equalsSecond(user.getLastConnection(), popup.getLastConnection().getValue()));

                assertTrue(popup.getProfiles().isReadonly());
                assertEquals(user.getProfiles().stream().map(Profile::getTitle).collect(Collectors.toList()), popup
                        .getProfiles().getValue());

                popup.close();
            }

            p++;
            if (p == 2 /* limit on 2 first pages */)
                break;
            else
                page.gotoNextPage();
        }
    }

    /**
     * Modification on a {@link UpdatePopup}.
     *
     * @see AbstractRow#actionUpdate()
     * @see UpdatePopup#submit()
     */
    @Test
    public void test_dataTable_action_update_submit() {
        final int idx = 0;
        final String newEmail = "new@value.ap";

        UsersPage page = AbstractPage.get().gotoUsersPage();

        // Before: same row informations
        UserRow row = page.getRows().get(idx);
        final String login = row.getLogin(); // must be unique
        assertNotEquals(newEmail, row.getEmail());

        // Update
        UserUpdatePopup updatePopup = row.actionUpdate();
        updatePopup.getEmail().setValue(newEmail);
        updatePopup.submit();

        // Before: find updated row (placed at the end)
        while (true) {
            // find updated row
            for (UserRow r : page.getRows())
                if (r.getLogin().equals(login)) {
                    assertEquals(newEmail, r.getEmail());
                    return; // find: stop
                }

            if (!page.hasNextPage())
                break;
            page.gotoNextPage();
        }
        fail("Updated row not found");
    }

    /**
     * {@link DetailsPopup Initial details} of {@link UpdatePopup update popup}
     * must be the same than Database content.
     * <p>
     * Check if {@link Field}s are updatable.
     *
     * @see AbstractRow#actionUpdate()
     * @see Popup#close()
     */
    @Test
    public void test_dataTable_action_update_values() {
        List<User> users = userDao.getAll();

        UsersPage page = AbstractPage.get().gotoUsersPage();

        int i = 0;
        int p = 0;

        while (true) {
            List<UserRow> rows = page.getRows();

            for (int r = 0; r < 2/* page size */&& i < users.size(); r++, i++) {
                User user = users.get(i);
                UserUpdatePopup popup = rows.get(r).actionUpdate();

                assertTrue(popup.getLogin().isReadonly());
                assertEquals(user.getLogin(), popup.getLogin().getValue());

                assertFalse(popup.getEmail().isReadonly());
                assertEquals(user.getEmail(), popup.getEmail().getValue());

                assertFalse(popup.isActive().isReadonly());
                assertEquals(user.isActive(), popup.isActive().getValue());

                assertTrue(popup.getLastConnection().isReadonly());
                assertTrue(equalsSecond(user.getLastConnection(), popup.getLastConnection().getValue()));

                assertFalse(popup.getProfiles().isReadonly());
                assertEquals(user.getProfiles().stream().map(Profile::getTitle).collect(Collectors.toList()), popup
                        .getProfiles().getValue());

                popup.close();
            }

            p++;
            if (p == 2 /* limit on 2 first pages */)
                break;
            else
                page.gotoNextPage();
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

    @Test
    public void test_datatable_filter_login() {
        final String value = "super";
        QUser u = QUser.user;
        List<String> logins = userDao.find(new JPAQuery().from(u).where(u.login.contains(value))).stream().map(User::getLogin)
                .sorted().collect(toList());

        UsersPage usersPage = AbstractPage.get().gotoUsersPage();
        usersPage.filterLogin(value);
        List<UserRow> rows = usersPage.getRows();

        for (int i = 0; i < 2 /* page size */&& i < logins.size(); i++)
            assertEquals(logins.get(i), rows.get(i).getLogin());
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
            assertTrue(equalsSecond(user.getLastConnection(), row.getLastConnection()));
        }
    }

    /** @see AbstractDatatablePage#getTotalCount() */
    @Test
    public void test_datatable_getTotalCount() {
        AbstractPage nav = AbstractPage.get();
        assertEquals(userDao.count(), nav.gotoUsersPage().getTotalCount());
        assertEquals(profileDao.count(), nav.gotoProfilesPage().getTotalCount());
        assertEquals(rightDao.count(), nav.gotoRightsPage().getTotalCount());
    }

    /** @see UsersPage#sortByEmail() */
    @Test
    public void test_dataTable_sort_email() {
        List<String> emails = userDao.getAll().stream().map(User::getEmail).sorted().collect(toList());

        UsersPage usersPage = AbstractPage.get().gotoUsersPage();
        usersPage.sortBy(EMAIL);
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
        usersPage.sortBy(LOGIN);
        List<UserRow> rows = usersPage.getRows();

        for (int i = 0; i < 2; i++) {
            assertEquals(logins.get(i), rows.get(i).getLogin());
        }
    }

}