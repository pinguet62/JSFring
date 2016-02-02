package fr.pinguet62.jsfring.gui;

import static fr.pinguet62.jsfring.test.Config.DATASET;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.datatable.AbstractRow;
import fr.pinguet62.jsfring.gui.htmlunit.right.RightRow;
import fr.pinguet62.jsfring.gui.htmlunit.right.RightsPage;

/** @see RightsPage */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringBootConfig.class)
@WebIntegrationTest
@DatabaseSetup(DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public final class RightsPageITTest {

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
    public void test_dataTable_action_rendered() {
        RightsPage rightsPage = AbstractPage.get().gotoRightsPage();

        assertFalse(rightsPage.isCreateButtonVisible());
        for (RightRow row : rightsPage.getRows()) {
            assertFalse(row.isActionButtonShowVisible());
            assertFalse(row.isActionButtonUpdateVisible());
            assertFalse(row.isActionButtonDeleteVisible());
        }
    }

}