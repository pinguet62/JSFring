package fr.pinguet62.jsfring.gui.component;

import static fr.pinguet62.jsfring.gui.htmlunit.DateUtils.equalsSecond;
import static fr.pinguet62.jsfring.gui.htmlunit.user.UsersPage.Column.EMAIL;
import static fr.pinguet62.jsfring.gui.htmlunit.user.UsersPage.Column.LOGIN;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import au.com.bytecode.opencsv.CSVReader;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.mysema.query.jpa.impl.JPAQuery;

import fr.pinguet62.Config;
import fr.pinguet62.FileChecker;
import fr.pinguet62.jsfring.dao.ProfileDao;
import fr.pinguet62.jsfring.dao.RightDao;
import fr.pinguet62.jsfring.dao.UserDao;
import fr.pinguet62.jsfring.gui.AbstractBean;
import fr.pinguet62.jsfring.gui.AbstractCrudBean;
import fr.pinguet62.jsfring.gui.AbstractSelectableBean;
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

/**
 * @see AbstractBean
 * @see AbstractSelectableBean
 * @see AbstractCrudBean
 * @see DataTableComponent
 * @see AbstractDatatablePage
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = Config.SPRING)
@DatabaseSetup(Config.DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public final class DataTableComponentTest {

    @Inject
    private ProfileDao profileDao;

    @Inject
    private RightDao rightDao;

    @Inject
    private UserDao userDao;

    /**
     * After delete cancel, no action must occurs.
     *
     * @see AbstractRow#actionDelete()
     * @see ConfirmPopup
     * @see ConfirmPopup#cancel()
     */
    @Test
    public void test_action_delete_cancel() {
        UsersPage usersPage = AbstractPage.get().gotoUsersPage();

        final int initialCount = usersPage.getTotalCount();

        usersPage.getRows().get(0).actionDelete().cancel();

        assertEquals(initialCount, usersPage.getTotalCount());
    }

    /**
     * The delete confirm:
     * <ol>
     * <li>The value must be deleted in database.</li>
     * <li>The row must disappear.</li>
     * <li>The total count must be decremented.</li>
     * </ol>
     *
     * @see AbstractRow#actionDelete()
     * @see ConfirmPopup
     * @see ConfirmPopup#confirm()
     */
    @Test
    public void test_action_delete_confirm() {
        UsersPage usersPage = AbstractPage.get().gotoUsersPage();

        // Before
        final long initialDbCount = userDao.count();
        final int initialDatatableCount = usersPage.getTotalCount();

        // row to delete
        UserRow row = usersPage.getRows().get(0);
        final String key = row.getLogin();
        row.actionDelete().confirm();

        // After
        assertEquals(initialDbCount - 1, userDao.count());
        assertEquals(initialDatatableCount - 1, usersPage.getTotalCount());
        for (UserRow r : usersPage)
            assertNotEquals(key, r.getLogin());
    }

    /**
     * Visibility of action buttons.
     * <p>
     * Depends on connected user's rights.
     *
     * @see AbstractRow#isActionButtonShowVisible()
     * @see AbstractRow#isActionButtonUpdateVisible()
     * @see AbstractRow#isActionButtonDeleteVisible()
     */
    @Test
    public void test_action_rendered() {
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
     * @see DetailsPopup
     * @see Popup#close()
     */
    @Test
    public void test_action_show() {
        List<User> users = userDao.getAll(); // default order
        UsersPage page = AbstractPage.get().gotoUsersPage();

        int i = 0;
        for (UserRow row : page) {
            User user = users.get(i);
            UserShowPopup popup = row.actionShow();

            assertTrue(popup.getLogin().isReadonly());
            assertEquals(user.getLogin(), popup.getLogin().getValue());

            assertTrue(popup.getEmail().isReadonly());
            assertEquals(user.getEmail(), popup.getEmail().getValue());

            assertTrue(popup.isActive().isReadonly());
            assertEquals(user.isActive(), popup.isActive().getValue());

            assertTrue(popup.getLastConnection().isReadonly());
            assertTrue(equalsSecond(user.getLastConnection(), popup.getLastConnection().getValue()));

            assertTrue(popup.getProfiles().isReadonly());
            assertEquals(user.getProfiles().stream().map(Profile::getTitle).collect(toList()), popup.getProfiles().getValue());

            popup.close();

            i++;
        }
    }

    /**
     * The modifications submit:
     * <ol>
     * <li>The row must updated.</li>
     * <li>The value must be updated in database.</li>
     * </ol>
     *
     * @see AbstractRow#actionUpdate()
     * @see UpdatePopup#submit()
     */
    @Test
    public void test_action_update_submit_modification() {
        final UsersPage page = AbstractPage.get().gotoUsersPage();

        final String newEmail = "new@value.ap";

        UserRow row = page.getRows().get(0);
        final String login = row.getLogin();
        assertNotEquals(newEmail, row.getEmail());

        // Action
        UserUpdatePopup updatePopup = row.actionUpdate();
        updatePopup.getEmail().setValue(newEmail);
        updatePopup.submit();

        // Before
        page.filterLogin(login); // because updated value is placed at the end
        assertEquals(1, page.getTotalCount());
        assertEquals(newEmail, page.getRows().get(0).getEmail());
        assertEquals(newEmail, userDao.get(login).getEmail());
    }

    /**
     * When user click on "Submit" button without modification, the data must
     * not change.
     */
    @Test
    public void test_action_update_submit_noModificiation() {
        final int idx = 0;
        final UsersPage page = AbstractPage.get().gotoUsersPage();

        // Before
        User userBefore = userDao.getAll().get(idx);
        final String login = userBefore.getLogin();
        final String email = userBefore.getEmail();
        final boolean active = userBefore.isActive();
        final Date lastConnection = userBefore.getLastConnection();
        final Set<Profile> profiles = userBefore.getProfiles();

        // Action
        page.getRows().get(idx).actionUpdate().submit();

        // After
        // - check row
        UserRow rowAfter = page.getRows().get(idx);
        assertEquals(login, rowAfter.getLogin());
        assertEquals(email, rowAfter.getEmail());
        assertEquals(active, rowAfter.getActive());
        assertTrue(equalsSecond(lastConnection, rowAfter.getLastConnection()));
        // - check database value
        User userAfter = userDao.getAll().get(idx);
        assertEquals(login, userAfter.getLogin());
        assertEquals(email, userAfter.getEmail());
        assertEquals(active, userAfter.isActive());
        assertEquals(lastConnection, userAfter.getLastConnection());
        assertEquals(profiles, userAfter.getProfiles());
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
    public void test_action_update_values() {
        List<User> users = userDao.getAll();

        UsersPage page = AbstractPage.get().gotoUsersPage();

        int i = 0;
        for (UserRow row : page) {
            User user = users.get(i);
            UserUpdatePopup popup = row.actionUpdate();

            assertTrue(popup.getLogin().isReadonly());
            assertEquals(user.getLogin(), popup.getLogin().getValue());

            assertFalse(popup.getEmail().isReadonly());
            assertEquals(user.getEmail(), popup.getEmail().getValue());

            assertFalse(popup.isActive().isReadonly());
            assertEquals(user.isActive(), popup.isActive().getValue());

            assertTrue(popup.getLastConnection().isReadonly());
            assertTrue(equalsSecond(user.getLastConnection(), popup.getLastConnection().getValue()));

            assertFalse(popup.getProfiles().isReadonly());
            assertEquals(user.getProfiles().stream().map(Profile::getTitle).collect(toList()), popup.getProfiles().getValue());

            popup.close();

            i++;
        }
    }

    /**
     * Visibility of action buttons.
     * <p>
     * Depends on connected user's rights.
     *
     * @see UsersPage#columnVisibile(Column)
     */
    @Test
    public void test_column_rendered() {
        UsersPage page = AbstractPage.get().gotoUsersPage();
        for (Column column : UsersPage.Column.values())
            assertTrue(page.columnVisibile(column));
    }

    /**
     * @see UsersPage#hideOrShowColumn(Column)
     * @see UsersPage#columnVisibile(Column)
     */
    // @Test
    public void test_columnToggler() {
        UsersPage page = AbstractPage.get().gotoUsersPage();
        for (Column column : UsersPage.Column.values()) {
            page.hideOrShowColumn(column);
            assertTrue(page.columnVisibile(column));
        }
    }

    /**
     * The table content must be equals to database values.
     *
     * @see AbstractDatatablePage#getRows()
     */
    @Test
    public void test_content() {
        List<User> users = userDao.getAll();
        UsersPage page = AbstractPage.get().gotoUsersPage();

        int i = 0;
        for (UserRow row : page) {
            User user = users.get(i);

            assertEquals(user.getLogin(), row.getLogin());
            assertEquals(user.getEmail(), row.getEmail());
            assertEquals(user.isActive(), row.getActive());
            assertTrue(equalsSecond(user.getLastConnection(), row.getLastConnection()));

            i++;
        }
    }

    /**
     * Check the file content: header & rows.
     *
     * @see AbstractDatatablePage#exportCSV()
     */
    @Test
    public void test_export_csv() throws IOException {
        UsersPage usersPage = AbstractPage.get().gotoUsersPage();
        InputStream is = usersPage.exportCSV();
        assertTrue(FileChecker.isCSV(is));

        // Content
        try (CSVReader reader = new CSVReader(new InputStreamReader(is))) {
            String[] header = Stream.of(Column.values()).sorted((a, b) -> Integer.compare(a.getIndex(), b.getIndex()))
                    .map(Column::getTitle).limit(Column.values().length - 1).toArray(String[]::new);
            assertArrayEquals(header, reader.readNext()); // header
            assertEquals(userDao.count(), reader.readAll().size()); // content
        }
    }

    /** @see AbstractDatatablePage#exportPDF() */
    @Test
    public void test_export_pdf() throws IOException {
        UsersPage usersPage = AbstractPage.get().gotoUsersPage();
        InputStream is = usersPage.exportPDF();
        assertTrue(FileChecker.isPDF(is));
    }

    /** @see AbstractDatatablePage#exportXLS() */
    @Test
    public void test_export_xls() throws IOException {
        UsersPage usersPage = AbstractPage.get().gotoUsersPage();
        InputStream is = usersPage.exportXLS();
        assertTrue(FileChecker.isXLS(is));
    }

    /** @see AbstractDatatablePage#exportXLSX() */
    @Test
    public void test_export_xlsx() throws IOException {
        UsersPage usersPage = AbstractPage.get().gotoUsersPage();
        InputStream is = usersPage.exportXLSX();
        assertTrue(FileChecker.isXLSX(is));
    }

    /** @see AbstractDatatablePage#exportXML() */
    @Test
    public void test_export_xml() throws Exception {
        UsersPage usersPage = AbstractPage.get().gotoUsersPage();
        InputStream is = usersPage.exportXML();
        assertTrue(FileChecker.isXML(is));
    }

    /** @see UsersPage#filterByActive(ActiveFilter) */
    @Test
    public void test_filter_custom() {
        UsersPage usersPage = AbstractPage.get().gotoUsersPage();

        assertEquals(3, usersPage.getTotalCount());

        usersPage.filterByActive(ActiveFilter.Yes);
        assertEquals(3, usersPage.getTotalCount());

        usersPage.filterByActive(ActiveFilter.No);
        assertEquals(0, usersPage.getTotalCount());

        usersPage.filterByActive(ActiveFilter.All);
        assertEquals(3, usersPage.getTotalCount());
    }

    /** @see UsersPage#filterLogin(String) */
    @Test
    public void test_filter_default() {
        final String value = "super";
        QUser u = QUser.user;
        List<String> logins = userDao.find(new JPAQuery().from(u).where(u.login.contains(value))).stream().map(User::getLogin)
                .sorted().collect(toList());

        // Action
        UsersPage page = AbstractPage.get().gotoUsersPage();
        page.filterLogin(value);

        // Test
        // - number of results
        assertEquals(logins.size(), page.getTotalCount());
        // - check order
        int i = 0;
        for (UserRow row : page) {
            assertEquals(logins.get(i), row.getLogin());
            i++;
        }
    }

    /**
     * The footer paginator contains the total count.
     *
     * @see AbstractDatatablePage#getTotalCount()
     */
    @Test
    public void test_getTotalCount() {
        AbstractPage nav = AbstractPage.get();
        assertEquals(userDao.count(), nav.gotoUsersPage().getTotalCount());
        assertEquals(profileDao.count(), nav.gotoProfilesPage().getTotalCount());
        assertEquals(rightDao.count(), nav.gotoRightsPage().getTotalCount());
    }

    /** @see UsersPage#sortByEmail() */
    @Test
    public void test_sort_email() {
        List<String> emails = userDao.getAll().stream().map(User::getEmail).sorted().collect(toList());

        UsersPage usersPage = AbstractPage.get().gotoUsersPage();
        usersPage.sortBy(EMAIL);
        List<UserRow> rows = usersPage.getRows();

        for (int i = 0; i < 2; i++)
            assertEquals(emails.get(i), rows.get(i).getEmail());
    }

    /** @see UsersPage#sortByLogin() */
    @Test
    public void test_sort_login() {
        List<String> logins = userDao.getAll().stream().map(User::getLogin).sorted().collect(toList());

        UsersPage usersPage = AbstractPage.get().gotoUsersPage();
        usersPage.sortBy(LOGIN);
        List<UserRow> rows = usersPage.getRows();

        for (int i = 0; i < 2; i++)
            assertEquals(logins.get(i), rows.get(i).getLogin());
    }

}