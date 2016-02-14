package fr.pinguet62.jsfring.gui.component;

import static fr.pinguet62.jsfring.gui.htmlunit.user.UsersPage.Column.EMAIL;
import static fr.pinguet62.jsfring.gui.htmlunit.user.UsersPage.Column.LOGIN;
import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isCSV;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isPDF;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isXLS;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isXLSX;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isXML;
import static fr.pinguet62.jsfring.util.MatcherUtils.equalToTruncated;
import static java.lang.Integer.compare;
import static java.util.Calendar.SECOND;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

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
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.mysema.query.jpa.impl.JPAQuery;

import au.com.bytecode.opencsv.CSVReader;
import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.dao.sql.ProfileDao;
import fr.pinguet62.jsfring.dao.sql.RightDao;
import fr.pinguet62.jsfring.dao.sql.UserDao;
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
import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.model.sql.QUser;
import fr.pinguet62.jsfring.model.sql.User;

/**
 * @see AbstractBean
 * @see AbstractSelectableBean
 * @see AbstractCrudBean
 * @see DataTableComponent
 * @see AbstractDatatablePage
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringBootConfig.class)
@WebIntegrationTest
@DatabaseSetup(DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public final class DataTableComponentITTest {

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

        assertThat(usersPage.getTotalCount(), is(equalTo(initialCount)));
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
        assertThat(userDao.count(), is(equalTo(initialDbCount - 1)));
        assertThat(usersPage.getTotalCount(), is(equalTo(initialDatatableCount - 1)));
        for (UserRow r : usersPage)
            assertThat(r.getLogin(), is(not(equalTo(key))));
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

        assertThat(usersPage.isCreateButtonVisible(), is(true));
        for (UserRow row : usersPage.getRows()) {
            assertThat(row.isActionButtonShowVisible(), is(true));
            assertThat(row.isActionButtonUpdateVisible(), is(true));
            assertThat(row.isActionButtonDeleteVisible(), is(true));
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
        List<User> users = userDao.findAll().stream().sorted(comparing(User::getLogin)).collect(toList());

        UsersPage page = AbstractPage.get().gotoUsersPage();
        page.sortBy(LOGIN);

        int i = 0;
        for (UserRow row : page) {
            User user = users.get(i);
            UserShowPopup popup = row.actionShow();

            assertThat(popup.getLogin().isReadonly(), is(true));
            assertThat(popup.getLogin().getValue(), is(equalTo(user.getLogin())));

            assertThat(popup.getEmail().isReadonly(), is(true));
            assertThat(popup.getEmail().getValue(), is(equalTo(user.getEmail())));

            assertThat(popup.isActive().isReadonly(), is(true));
            assertThat(popup.isActive().getValue(), is(equalTo(user.isActive())));

            assertThat(popup.getLastConnection().isReadonly(), is(true));
            if (user.getLastConnection() == null)
                assertThat(popup.getLastConnection().getValue(), is(nullValue()));
            else
                assertThat(popup.getLastConnection().getValue(), is(equalToTruncated(user.getLastConnection(), SECOND)));

            assertThat(popup.getProfiles().isReadonly(), is(true));
            assertThat(popup.getProfiles().getValue(),
                    is(equalTo(user.getProfiles().stream().map(Profile::getTitle).collect(toList()))));

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
        assertThat(row.getEmail(), is(not(equalTo(newEmail))));

        // Action
        UserUpdatePopup updatePopup = row.actionUpdate();
        updatePopup.getEmail().setValue(newEmail);
        updatePopup.submit();

        // Before
        page.filterLogin(login); // because updated value is placed at the end
        assertThat(page.getTotalCount(), is(equalTo(1)));
        assertThat(page.getRows().get(0).getEmail(), is(equalTo(newEmail)));
        assertThat(userDao.findOne(login).getEmail(), is(equalTo(newEmail)));
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
        User userBefore = userDao.findAll().get(idx);
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
        assertThat(rowAfter.getLogin(), is(equalTo(login)));
        assertThat(rowAfter.getEmail(), is(equalTo(email)));
        assertThat(rowAfter.isActive(), is(equalTo(active)));
        assertThat(rowAfter.getLastConnection(), is(equalToTruncated(lastConnection, SECOND)));
        // - check database value
        User userAfter = userDao.findAll().get(idx);
        assertThat(userAfter.getLogin(), is(equalTo(login)));
        assertThat(userAfter.getEmail(), is(equalTo(email)));
        assertThat(userAfter.isActive(), is(equalTo(active)));
        assertThat(userAfter.getLastConnection(), is(equalTo(lastConnection)));
        assertThat(userAfter.getProfiles(), is(equalTo(profiles)));
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
        List<User> users = userDao.findAll().stream().sorted(comparing(User::getLogin)).collect(toList());

        UsersPage page = AbstractPage.get().gotoUsersPage();
        page.sortBy(LOGIN);

        int i = 0;
        for (UserRow row : page) {
            User user = users.get(i);
            UserUpdatePopup popup = row.actionUpdate();

            assertThat(popup.getLogin().isReadonly(), is(true));
            assertThat(popup.getLogin().getValue(), is(equalTo(user.getLogin())));

            assertThat(popup.getEmail().isReadonly(), is(false));
            assertThat(popup.getEmail().getValue(), is(equalTo(user.getEmail())));

            assertThat(popup.isActive().isReadonly(), is(false));
            assertThat(popup.isActive().getValue(), is(equalTo(user.isActive())));

            assertThat(popup.getLastConnection().isReadonly(), is(true));
            if (user.getLastConnection() == null)
                assertThat(popup.getLastConnection().getValue(), is(nullValue()));
            else
                assertThat(popup.getLastConnection().getValue(), is(equalToTruncated(user.getLastConnection(), SECOND)));

            assertThat(popup.getProfiles().isReadonly(), is(false));
            assertThat(popup.getProfiles().getValue(),
                    is(equalTo(user.getProfiles().stream().map(Profile::getTitle).collect(toList()))));

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
        for (Column column : Column.values())
            assertThat(page.columnVisibile(column), is(true));
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
            assertThat(page.columnVisibile(column), is(false));
        }
    }

    /**
     * The table content must be equals to database values.
     *
     * @see AbstractDatatablePage#getRows()
     */
    @Test
    public void test_content() {
        List<User> users = userDao.findAll().stream().sorted(comparing(User::getLogin)).collect(toList());

        UsersPage page = AbstractPage.get().gotoUsersPage();
        page.sortBy(LOGIN);

        int i = 0;
        for (UserRow row : page) {
            User user = users.get(i);

            assertThat(row.getLogin(), is(equalTo(user.getLogin())));
            assertThat(row.getEmail(), is(equalTo(user.getEmail())));
            assertThat(row.isActive(), is(equalTo(user.isActive())));
            if (user.getLastConnection() == null)
                assertThat(row.getLastConnection(), is(nullValue()));
            else
                assertThat(row.getLastConnection(), is(equalToTruncated(user.getLastConnection(), SECOND)));

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
        assertThat(is, isCSV());

        // Content
        try (CSVReader reader = new CSVReader(new InputStreamReader(is))) {
            String[] header = Stream.of(Column.values()).sorted((a, b) -> compare(a.getIndex(), b.getIndex()))
                    .map(Column::getTitle).limit(Column.values().length - 1).toArray(String[]::new);
            assertArrayEquals(header, reader.readNext()); // header
            assertThat(reader.readAll(), hasSize((int) userDao.count())); // content
        }
    }

    /** @see AbstractDatatablePage#exportPDF() */
    @Test
    public void test_export_pdf() throws IOException {
        UsersPage usersPage = AbstractPage.get().gotoUsersPage();
        InputStream is = usersPage.exportPDF();
        assertThat(is, isPDF());
    }

    /** @see AbstractDatatablePage#exportXLS() */
    @Test
    public void test_export_xls() throws IOException {
        UsersPage usersPage = AbstractPage.get().gotoUsersPage();
        InputStream is = usersPage.exportXLS();
        assertThat(is, isXLS());
    }

    /** @see AbstractDatatablePage#exportXLSX() */
    @Test
    public void test_export_xlsx() throws IOException {
        UsersPage usersPage = AbstractPage.get().gotoUsersPage();
        InputStream is = usersPage.exportXLSX();
        assertThat(is, isXLSX());
    }

    /** @see AbstractDatatablePage#exportXML() */
    @Test
    public void test_export_xml() throws Exception {
        UsersPage usersPage = AbstractPage.get().gotoUsersPage();
        InputStream is = usersPage.exportXML();
        assertThat(is, isXML());
    }

    /** @see UsersPage#filterByActive(ActiveFilter) */
    @Test
    public void test_filter_custom() {
        UsersPage usersPage = AbstractPage.get().gotoUsersPage();

        assertThat(usersPage.getTotalCount(), is(equalTo(3)));

        usersPage.filterByActive(ActiveFilter.Yes);
        assertThat(usersPage.getTotalCount(), is(equalTo(3)));

        usersPage.filterByActive(ActiveFilter.No);
        assertThat(usersPage.getTotalCount(), is(equalTo(0)));

        usersPage.filterByActive(ActiveFilter.All);
        assertThat(usersPage.getTotalCount(), is(equalTo(3)));
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
        assertThat(page.getTotalCount(), is(equalTo(logins.size())));
        // - check order
        int i = 0;
        for (UserRow row : page) {
            assertThat(row.getLogin(), is(equalTo(logins.get(i))));
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

        assertThat(nav.gotoUsersPage().getTotalCount(), is(equalTo((int) userDao.count())));
        assertThat(nav.gotoProfilesPage().getTotalCount(), is(equalTo((int) profileDao.count())));
        assertThat(nav.gotoRightsPage().getTotalCount(), is(equalTo((int) rightDao.count())));
    }

    /** @see UsersPage#sortByEmail() */
    @Test
    public void test_sort_email() {
        List<String> emails = userDao.findAll().stream().map(User::getEmail).sorted().collect(toList());

        UsersPage usersPage = AbstractPage.get().gotoUsersPage();
        usersPage.sortBy(EMAIL);
        List<UserRow> rows = usersPage.getRows();

        for (int i = 0; i < 2; i++)
            assertThat(rows.get(i).getEmail(), is(equalTo(emails.get(i))));
    }

    /** @see UsersPage#sortByLogin() */
    @Test
    public void test_sort_login() {
        List<String> logins = userDao.findAll().stream().map(User::getLogin).sorted().collect(toList());

        UsersPage usersPage = AbstractPage.get().gotoUsersPage();
        usersPage.sortBy(LOGIN);
        List<UserRow> rows = usersPage.getRows();

        for (int i = 0; i < 2; i++)
            assertThat(rows.get(i).getLogin(), is(equalTo(logins.get(i))));
    }

}