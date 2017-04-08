package fr.pinguet62.jsfring.gui.jasperreport;

import static fr.pinguet62.jsfring.gui.htmlunit.AbstractPage.get;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.gui.htmlunit.jasperreport.ParametersJasperReportPage;
import fr.pinguet62.jsfring.gui.jasperreport.sample.ParametersJasperReportBean;

/** @see ParametersJasperReportBean */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
public class ParameterJasperReportITTest {

    private ParametersJasperReportPage page;

    @Before
    public void before() {
        page = get().gotoParametersJasperReportPage();
    }

    /** @see Date */
    // TODO Fix @Test
    public void test_date() throws IOException {
        Date date = new Date();

        page.setDate(date);
        InputStream is = page.exportTEXT();
        String content = IOUtils.toString(is);
        assertThat(content, containsString(new SimpleDateFormat("dd/MM/yy hh:mm").format(date)));
    }

    /** @see Integer */
    @Test
    public void test_integer() throws IOException {
        page.setInteger(42);
        InputStream is = page.exportTEXT();
        String content = IOUtils.toString(is);
        assertThat(content, containsString("42"));
    }

    /** @see List */
    @Test
    public void test_list() throws IOException {
        page.setList(asList("avion", "vélo"));
        InputStream is = page.exportTEXT();
        String content = IOUtils.toString(is);
        assertThat(content, containsString("avion"));
        assertThat(content, containsString("vélo"));
    }

    /** @see String */
    @Test
    public void test_string() throws IOException {
        page.setString("foo");
        InputStream is = page.exportTEXT();
        String content = IOUtils.toString(is);
        assertThat(content, containsString("foo"));
    }

}