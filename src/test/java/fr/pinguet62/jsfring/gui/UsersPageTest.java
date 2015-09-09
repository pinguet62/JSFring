package fr.pinguet62.jsfring.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
import fr.pinguet62.jsfring.gui.htmlunit.user.UserRow;
import fr.pinguet62.jsfring.gui.htmlunit.user.UsersPage;

/** @see UsersPage */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = Config.SPRING)
@DatabaseSetup(Config.DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class UsersPageTest {

    @Test
    public void test_dataTable_actions() {
        UsersPage usersPage = AbstractPage.get().gotoUsersPage();

        assertTrue(usersPage.isCreateButtonVisible());
        for (UserRow row : usersPage.getRows()) {
            assertTrue(row.isShowButtonVisible());
            assertTrue(row.isUpdateButtonVisible());
            assertTrue(row.isDeleteButtonVisible());
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