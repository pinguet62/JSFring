package fr.pinguet62.jsfring.gui;

import static fr.pinguet62.jsfring.gui.htmlunit.AbstractPage.get;
import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.dao.sql.RightDao;
import fr.pinguet62.jsfring.gui.htmlunit.right.RightRow;
import fr.pinguet62.jsfring.gui.htmlunit.right.RightsPage;
import fr.pinguet62.jsfring.gui.htmlunit.right.popup.RightShowPopup;
import fr.pinguet62.jsfring.gui.htmlunit.right.popup.RightUpdatePopup;
import fr.pinguet62.jsfring.model.sql.QRight;
import fr.pinguet62.jsfring.model.sql.Right;

/** @see RightsPage */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
//DbUnit
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup(DATASET)
public final class RightsPageITTest {

    private RightsPage page;

    @Inject
    private RightDao rightDao;

    @Before
    public void before() {
        page = get().gotoRightsPage();
    }

    @Test
    public void test_action_rendered() {
        assertThat(page.isCreateButtonVisible(), is(false));
        for (RightRow row : page.getRows()) {
            assertThat(row.isActionButtonShowVisible(), is(true));
            assertThat(row.isActionButtonUpdateVisible(), is(true));
            assertThat(row.isActionButtonDeleteVisible(), is(false));
        }
    }

    @Test
    public void test_action_show_field_readonly() {
        RightShowPopup popup = page.iterator().next().actionShow();

        assertThat(popup.getCode().isReadonly(), is(true));
        assertThat(popup.getTitle().isReadonly(), is(true));
    }

    @Test
    public void test_action_show_field_value() {
        RightRow row = page.iterator().next();
        RightShowPopup popup = row.actionShow();
        Right right = rightDao.findOne(QRight.right_.code.eq(row.getCode()));

        assertThat(popup.getCode().getValue(), is(equalTo(right.getCode())));
        assertThat(popup.getTitle().getValue(), is(equalTo(right.getTitle())));
    }

    @Test
    public void test_action_update_field_readonly() {
        RightUpdatePopup popup = page.iterator().next().actionUpdate();

        assertThat(popup.getCode().isReadonly(), is(true));
        assertThat(popup.getTitle().isReadonly(), is(false));
    }

    @Test
    public void test_action_update_field_value() {
        RightRow row = page.iterator().next();
        RightUpdatePopup popup = row.actionUpdate();
        Right right = rightDao.findOne(QRight.right_.code.eq(row.getCode()));

        assertThat(popup.getCode().getValue(), is(equalTo(right.getCode())));
        assertThat(popup.getTitle().getValue(), is(equalTo(right.getTitle())));
    }

}