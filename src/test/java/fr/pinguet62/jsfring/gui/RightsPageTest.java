package fr.pinguet62.jsfring.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Test;

import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.right.RightRow;
import fr.pinguet62.jsfring.gui.htmlunit.right.RightsPage;

/** @see RightsPage */
public final class RightsPageTest {

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
        List<RightRow> rows = AbstractPage.get().gotoRightsPage().getRows();

        {
            RightRow row0 = rows.get(0);
            assertEquals("RIGHT_RO", row0.getCode());
            assertEquals("Affichage des droits", row0.getTitle());
        }
        {
            RightRow row1 = rows.get(1);
            assertEquals("PROFILE_RO", row1.getCode());
            assertEquals("Affichage des profils", row1.getTitle());
        }
    }

}