package fr.pinguet62.jsfring.webapp.jsf.component;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isCSV;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isPDF;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isXLS;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isXLSX;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isXML;
import static fr.pinguet62.jsfring.util.MatcherUtils.equalToTruncated;
import static fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage.get;
import static fr.pinguet62.jsfring.webapp.jsf.htmlunit.user.UsersPage.Column.EMAIL;
import static fr.pinguet62.jsfring.webapp.jsf.htmlunit.user.UsersPage.Column.values;
import static java.lang.Integer.compare;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.opencsv.CSVReader;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.dao.sql.ProfileDao;
import fr.pinguet62.jsfring.dao.sql.RightDao;
import fr.pinguet62.jsfring.dao.sql.UserDao;
import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.model.sql.QUser;
import fr.pinguet62.jsfring.model.sql.User;
import fr.pinguet62.jsfring.webapp.jsf.AbstractBean;
import fr.pinguet62.jsfring.webapp.jsf.AbstractCrudBean;
import fr.pinguet62.jsfring.webapp.jsf.AbstractSelectableBean;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.AbstractDatatablePage;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.AbstractRow;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup.ConfirmPopup;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup.DetailsPopup;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup.Popup;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup.ShowPopup;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup.UpdatePopup;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.Field;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.user.UserRow;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.user.UsersPage;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.user.UsersPage.ActiveFilter;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.user.UsersPage.Column;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.user.popup.UserShowPopup;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.user.popup.UserUpdatePopup;

/**
 * @see AbstractBean
 * @see AbstractSelectableBean
 * @see AbstractCrudBean
 * @see DataTableComponent
 * @see AbstractDatatablePage
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
// DbUnit
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup(DATASET)
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
        UsersPage usersPage = get().gotoUsersPage();

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
        UsersPage usersPage = get().gotoUsersPage();

        // Before
        final long initialDbCount = userDao.count();
        final int initialDatatableCount = usersPage.getTotalCount();

        // row to delete
        UserRow row = usersPage.getRows().get(0);
        final String key = row.getEmail();
        row.actionDelete().confirm();

        // After
        assertThat(userDao.count(), is(equalTo(initialDbCount - 1)));
        assertThat(usersPage.getTotalCount(), is(equalTo(initialDatatableCount - 1)));
        for (UserRow r : usersPage)
            assertThat(r.getEmail(), is(not(equalTo(key))));
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
        UsersPage usersPage = get().gotoUsersPage();

        assertThat(usersPage.isCreateButtonVisible(), is(true));
        for (UserRow row : usersPage.getRows()) {
            assertThat(row.isActionButtonShowVisible(), is(true));
            assertThat(row.isActionButtonUpdateVisible(), is(true));
            assertThat(row.isActionButtonDeleteVisible(), is(true));
        }
    }

    /**
     * {@link DetailsPopup Details} of {@link ShowPopup show popup} must be the same than Database content.
     * <p>
     * Check if {@link Field}s are not updatable.
     *
     * @see AbstractRow#actionShow()
     * @see DetailsPopup
     * @see Popup#close()
     */
    @Test
    public void test_action_show() {
        List<User> users = userDao.findAll().stream().sorted(comparing(User::getEmail)).collect(toList());

        UsersPage page = get().gotoUsersPage();
        page.sortBy(EMAIL);

        int i = 0;
        for (UserRow row : page) {
            User user = users.get(i);
            UserShowPopup popup = row.actionShow();

            assertThat(popup.getEmail().isReadonly(), is(true));
            assertThat(popup.getEmail().getValue(), is(equalTo(user.getEmail())));

            assertThat(popup.isActive().isReadonly(), is(true));
            assertThat(popup.isActive().getValue(), is(equalTo(user.getActive())));

            assertThat(popup.getLastConnection().isReadonly(), is(true));
            if (user.getLastConnection() == null)
                assertThat(popup.getLastConnection().getValue(), is(nullValue()));
            else
                assertThat(popup.getLastConnection().getValue(), is(equalToTruncated(user.getLastConnection(), SECONDS)));

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
        UsersPage page = get().gotoUsersPage();
        UserRow row = page.getRows().get(0);
        String email = row.getEmail();
        boolean newValue = !userDao.findById(email).get().getActive();
        assertThat(row.isActive(), is(not(equalTo(newValue))));

        // Action
        UserUpdatePopup updatePopup = row.actionUpdate();
        updatePopup.getActive().setValue(newValue);
        updatePopup.submit();

        // Before
        page.filterEmail(email); // because updated value is placed at the end
        assertThat(page.getTotalCount(), is(equalTo(1)));
        assertThat(page.getRows().get(0).isActive(), is(equalTo(newValue)));
        assertThat(userDao.findById(email).get().getActive(), is(equalTo(newValue)));
    }

    /**
     * When user click on "Submit" button without modification, the data must not change.
     */
    @Test
    public void test_action_update_submit_noModificiation() {
        final int idx = 0;
        final UsersPage page = get().gotoUsersPage();

        // Before
        User userBefore = userDao.findAll().get(idx);
        final String email = userBefore.getEmail();
        final boolean active = userBefore.getActive();
        final LocalDateTime lastConnection = userBefore.getLastConnection();
        final Set<Profile> profiles = userBefore.getProfiles();

        // Action
        page.getRows().get(idx).actionUpdate().submit();

        // After
        // - check row
        UserRow rowAfter = page.getRows().get(idx);
        assertThat(rowAfter.getEmail(), is(equalTo(email)));
        assertThat(rowAfter.isActive(), is(equalTo(active)));
        // assertThat(rowAfter.getLastConnection(), is(equalToTruncated(lastConnection, SECOND))); // TODO fix timestamp
        // - check database value
        User userAfter = userDao.findAll().get(idx);
        assertThat(userAfter.getEmail(), is(equalTo(email)));
        assertThat(userAfter.getActive(), is(equalTo(active)));
        assertThat(userAfter.getLastConnection(), is(equalTo(lastConnection)));
        assertThat(userAfter.getProfiles(), is(equalTo(profiles)));
    }

    /**
     * {@link DetailsPopup Initial details} of {@link UpdatePopup update popup} must be the same than Database content.
     * <p>
     * Check if {@link Field}s are updatable.
     *
     * @see AbstractRow#actionUpdate()
     * @see Popup#close()
     */
    @Test
    public void test_action_update_values() {
        List<User> users = userDao.findAll().stream().sorted(comparing(User::getEmail)).collect(toList());

        UsersPage page = get().gotoUsersPage();
        page.sortBy(EMAIL);

        int i = 0;
        for (UserRow row : page) {
            User user = users.get(i);
            UserUpdatePopup popup = row.actionUpdate();

            assertThat(popup.getEmail().isReadonly(), is(true));
            assertThat(popup.getEmail().getValue(), is(equalTo(user.getEmail())));

            assertThat(popup.getActive().isReadonly(), is(false));
            assertThat(popup.getActive().getValue(), is(equalTo(user.getActive())));

            assertThat(popup.getLastConnection().isReadonly(), is(true));
            if (user.getLastConnection() == null)
                assertThat(popup.getLastConnection().getValue(), is(nullValue()));
            else
                assertThat(popup.getLastConnection().getValue(), is(equalToTruncated(user.getLastConnection(), SECONDS)));

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
        UsersPage page = get().gotoUsersPage();
        for (Column column : values())
            assertThat(page.columnVisibile(column), is(true));
    }

    /**
     * @see UsersPage#hideOrShowColumn(Column)
     * @see UsersPage#columnVisibile(Column)
     */
    // @Test
    public void test_columnToggler() {
        UsersPage page = get().gotoUsersPage();
        for (Column column : values()) {
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
        List<User> users = userDao.findAll().stream().sorted(comparing(User::getEmail)).collect(toList());

        UsersPage page = get().gotoUsersPage();
        page.sortBy(EMAIL);

        int i = 0;
        for (UserRow row : page) {
            User user = users.get(i);

            assertThat(row.getEmail(), is(equalTo(user.getEmail())));
            assertThat(row.isActive(), is(equalTo(user.getActive())));
            if (user.getLastConnection() == null)
                assertThat(row.getLastConnection(), is(nullValue()));
            else
                assertThat(row.getLastConnection(), is(equalToTruncated(user.getLastConnection(), SECONDS)));

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
        UsersPage usersPage = get().gotoUsersPage();
        InputStream is = usersPage.exportCSV();
        assertThat(is, isCSV());

        // Content
        try (CSVReader reader = new CSVReader(new InputStreamReader(is))) {
            String[] header = Stream.of(values()).sorted((a, b) -> compare(a.getIndex(), b.getIndex())).map(Column::getTitle)
                    .limit(values().length - 1).toArray(String[]::new);
            assertArrayEquals(header, reader.readNext()); // header
            assertThat(reader.readAll(), hasSize((int) userDao.count())); // content
        }
    }

    /** @see AbstractDatatablePage#exportPDF() */
    @Test
    public void test_export_pdf() throws IOException {
        UsersPage usersPage = get().gotoUsersPage();
        InputStream is = usersPage.exportPDF();
        assertThat(is, isPDF());
    }

    /** @see AbstractDatatablePage#exportXLS() */
    @Test
    public void test_export_xls() throws IOException {
        UsersPage usersPage = get().gotoUsersPage();
        InputStream is = usersPage.exportXLS();
        assertThat(is, isXLS());
    }

    /** @see AbstractDatatablePage#exportXLSX() */
    @Test
    public void test_export_xlsx() throws IOException {
        UsersPage usersPage = get().gotoUsersPage();
        InputStream is = usersPage.exportXLSX();
        assertThat(is, isXLSX());
    }

    /** @see AbstractDatatablePage#exportXML() */
    @Test
    public void test_export_xml() throws Exception {
        UsersPage usersPage = get().gotoUsersPage();
        InputStream is = usersPage.exportXML();
        assertThat(is, isXML());
    }

    /** @see UsersPage#filterByActive(ActiveFilter) */
    @Test
    public void test_filter_custom() {
        UsersPage usersPage = get().gotoUsersPage();

        assertThat(usersPage.getTotalCount(), is(equalTo(3)));

        usersPage.filterByActive(ActiveFilter.Yes);
        // assertThat(usersPage.getTotalCount(), is(equalTo(3))); // TODO fix

        usersPage.filterByActive(ActiveFilter.No);
        assertThat(usersPage.getTotalCount(), is(equalTo(0)));

        usersPage.filterByActive(ActiveFilter.All);
        assertThat(usersPage.getTotalCount(), is(equalTo(3)));
    }

    /** @see UsersPage#filterEmail(String) */
    @Test
    public void test_filter_default() {
        final String value = "super";
        List<String> emails = userDao.findAll(QUser.user.email.contains(value)).stream()
                .map(User::getEmail).sorted().collect(toList());

        // Action
        UsersPage page = get().gotoUsersPage();
        page.filterEmail(value);

        // Test
        // - number of results
        assertThat(page.getTotalCount(), is(equalTo(emails.size())));
        // - check order
        int i = 0;
        for (UserRow row : page) {
            assertThat(row.getEmail(), is(equalTo(emails.get(i))));
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
        AbstractPage nav = get();

        assertThat(nav.gotoUsersPage().getTotalCount(), is(equalTo((int) userDao.count())));
        assertThat(nav.gotoProfilesPage().getTotalCount(), is(equalTo((int) profileDao.count())));
        assertThat(nav.gotoRightsPage().getTotalCount(), is(equalTo((int) rightDao.count())));
    }

    /** @see UsersPage#sortByEmail() */
    @Test
    public void test_sort_email() {
        List<String> emails = userDao.findAll().stream().map(User::getEmail).sorted().collect(toList());

        UsersPage usersPage = get().gotoUsersPage();
        usersPage.sortBy(EMAIL);
        List<UserRow> rows = usersPage.getRows();

        for (int i = 0; i < 2; i++)
            assertThat(rows.get(i).getEmail(), is(equalTo(emails.get(i))));
    }

}