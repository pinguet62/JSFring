package fr.pinguet62.jsfring.gui.jasperreport;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.jasperreport.ParametersJasperReportPage;
import fr.pinguet62.jsfring.gui.jasperreport.sample.ParametersJasperReportBean;

/** @see ParametersJasperReportBean */
public final class ParameterJasperReportTest {

    private ParametersJasperReportPage page;

    @Before
    public void before() {
        page = AbstractPage.get().gotoParametersJasperReportPage();
    }

    /** @see Date */
    //@Test
    public void test_date() throws IOException {
        Date date = new Date();

        page.setDate(date);
        InputStream is = page.exportTEXT();
        String content = IOUtils.toString(is);
        assertTrue(content.contains(new SimpleDateFormat("dd/MM/yy hh:mm").format(date)));
    }

    /** @see Integer */
    @Test
    public void test_integer() throws IOException {
        page.setInteger(42);
        InputStream is = page.exportTEXT();
        String content = IOUtils.toString(is);
        assertTrue(content.contains("42"));
    }

    /** @see List */
    @Test
    public void test_list() throws IOException {
        page.setList(Arrays.asList("avion", "vélo"));
        InputStream is = page.exportTEXT();
        String content = IOUtils.toString(is);
        assertTrue(content.contains("avion"));
        assertTrue(content.contains("vélo"));
    }

    /** @see String */
    @Test
    public void test_string() throws IOException {
        page.setString("foo");
        InputStream is = page.exportTEXT();
        String content = IOUtils.toString(is);
        assertTrue(content.contains("foo"));
    }

}