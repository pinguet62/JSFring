package fr.pinguet62.jsfring.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import fr.pinguet62.Config;
import fr.pinguet62.jsfring.dao.RightDao;
import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.right.RightRow;
import fr.pinguet62.jsfring.gui.htmlunit.right.RightsPage;
import fr.pinguet62.jsfring.model.Right;

/** @see RightsPage */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = Config.SPRING)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
public final class RightsPageTest {

    @Inject
    private RightDao rightDao;

    @Test
    public void test_dataTable_actions() {
        RightsPage rightsPage = AbstractPage.get().gotoRightsPage();

        assertFalse(rightsPage.isCreateButtonVisible());
        for (RightRow row : rightsPage.getRows()) {
            assertFalse(row.isActionButtonShowVisible());
            assertFalse(row.isActionButtonUpdateVisible());
            assertFalse(row.isActionButtonDeleteVisible());
        }
    }

    @Test
    public void test_dataTable_content() {
        List<Right> rights = rightDao.getAll();
        List<RightRow> rows = AbstractPage.get().gotoRightsPage().getRows();

        {
            Right right = rights.get(0);
            RightRow row0 = rows.get(0);
            assertEquals(right.getCode(), row0.getCode());
            assertEquals(right.getTitle(), row0.getTitle());
        }
        {
            Right right = rights.get(1);
            RightRow row1 = rows.get(1);
            assertEquals(right.getCode(), row1.getCode());
            assertEquals(right.getTitle(), row1.getTitle());
        }
    }

}