package fr.pinguet62.jsfring.gui.component.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.path.StringPath;

import fr.pinguet62.jsfring.gui.component.filter.operator.ContainsOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.EndsWithOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.EqualsToOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.IsNullOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.LikeOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.StartsWithOperator;
import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.filter.FilterField;
import fr.pinguet62.jsfring.gui.htmlunit.filter.FilterPathPage;
import fr.pinguet62.jsfring.gui.sample.FilterPathBean;
import fr.pinguet62.jsfring.model.QUser;

/**
 * @see PathFilter
 * @see StringPathFilter
 * @see NumberPathFilter
 * @see FilterPathBean
 */
public final class FilterPathPageTest {

    private static final StringPath root = QUser.user.login;

    private FilterPathPage page;

    /** Refresh page. */
    @Before
    public void before() {
        page = AbstractPage.get().gotoSampleFilterSimple();
    }

    /**
     * @see StringPathFilter
     * @see ContainsOperator
     */
    @Test
    public void test_StringPathFilter_Contains() {
        FilterField filter = page.getFilterString();
        filter.setOperator(ContainsOperator.class.getName());
        filter.setValue(0, "foo");
        page.submit();

        assertFalse(filter.isError());
        assertEquals(root.contains("foo").toString(), filter.getQuery());
    }

    /**
     * @see StringPathFilter
     * @see EndsWithOperator
     */
    @Test
    public void test_StringPathFilter_EndsWith() {
        FilterField filter = page.getFilterString();
        filter.setOperator(EndsWithOperator.class.getName());
        filter.setValue(0, "foo");
        page.submit();

        assertFalse(filter.isError());
        assertEquals(root.endsWith("foo").toString(), filter.getQuery());
    }

    /**
     * @see StringPathFilter
     * @see EqualsToOperator
     */
    @Test
    public void test_StringPathFilter_EqualsTo() {
        FilterField filter = page.getFilterString();
        filter.setOperator(EqualsToOperator.class.getName());
        filter.setValue(0, "foo");
        page.submit();

        assertFalse(filter.isError());
        assertEquals(root.eq("foo").toString(), filter.getQuery());
    }

    /**
     * @see StringPathFilter
     * @see IsNullOperator
     */
    @Test
    public void test_StringPathFilter_IsNull() {
        FilterField filter = page.getFilterString();
        filter.setOperator(IsNullOperator.class.getName());
        page.submit();

        assertFalse(filter.isError());
        assertEquals(root.isNull().toString(), filter.getQuery());
    }

    /**
     * @see StringPathFilter
     * @see LikeOperator
     */
    @Test
    public void test_StringPathFilter_Like() {
        FilterField filter = page.getFilterString();
        filter.setOperator(LikeOperator.class.getName());
        filter.setValue(0, "foo");
        page.submit();

        assertFalse(filter.isError());
        assertEquals(root.like("foo").toString(), filter.getQuery());
    }

    /** @see StringPathFilter */
    @Test
    public void test_StringPathFilter_none() {
        FilterField filter = page.getFilterString();
        filter.setOperator(StringPathFilter.class.getName());
        page.submit();

        assertFalse(filter.isError());
        assertEquals(new BooleanBuilder().toString(), filter.getQuery());
    }

    /**
     * @see StringPathFilter
     * @see StartsWithOperator
     */
    @Test
    public void test_StringPathFilter_StartsWith() {
        FilterField filter = page.getFilterString();
        filter.setOperator(StartsWithOperator.class.getName());
        filter.setValue(0, "foo");
        page.submit();

        assertFalse(filter.isError());
        assertEquals(root.startsWith("foo").toString(), filter.getQuery());
    }

}