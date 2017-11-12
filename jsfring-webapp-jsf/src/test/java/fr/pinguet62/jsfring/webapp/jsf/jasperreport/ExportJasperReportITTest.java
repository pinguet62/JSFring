package fr.pinguet62.jsfring.webapp.jsf.jasperreport;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.jasperreport.AbstractJasperReportPage;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.jasperreport.UsersRightsJasperReportPage;
import fr.pinguet62.jsfring.webapp.jsf.jasperreport.sample.UsersRightsJasperReportBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.io.InputStream;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.*;
import static fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage.get;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

/**
 * @see AbstractJasperReportBean#getStreamedContent(ExportType)
 * @see ExportType
 * @see AbstractJasperReportPage
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
// DbUnit
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup(DATASET)
public final class ExportJasperReportITTest {

    private UsersRightsJasperReportPage page;

    @Before
    public void before() {
        page = get().gotoReportsUsersRightsPage();
    }

    /**
     * @see ExportType#CSV
     * @see UsersRightsJasperReportBean#getCsvFile()
     */
    @Test
    public void test_csv() {
        InputStream is = page.exportCSV();
        assertThat(is, isCSV());
    }

    /**
     * @see ExportType#DOCX
     * @see UsersRightsJasperReportBean#getDocxFile()
     */
    @Test
    public void test_docx() {
        InputStream is = page.exportDOCX();
        assertThat(is, isDOCX());
    }

    /**
     * @see ExportType#HTML
     * @see UsersRightsJasperReportBean#getHtmlFile()
     */
    @Test
    public void test_html() {
        InputStream is = page.exportHTML();
        assertThat(is, isHTML());
    }

    /**
     * @see ExportType#GRAPHICS_2D
     * @see UsersRightsJasperReportBean#getJsonFile()
     */
    @Test
    public void test_json() {
        InputStream is = page.exportJSON();
        assertThat(is, isImage());
    }

    /**
     * @see ExportType#ODS
     * @see UsersRightsJasperReportBean#getOdsFile()
     */
    @Test
    public void test_ods() {
        InputStream is = page.exportODS();
        assertThat(is, isODS());
    }

    /**
     * @see ExportType#ODT
     * @see UsersRightsJasperReportBean#getOdtFile()
     */
    @Test
    public void test_odt() throws Exception {
        InputStream is = page.exportODT();
        assertThat(is, isODT());
    }

    /**
     * @see ExportType#PDF
     * @see UsersRightsJasperReportBean#getPdfFile()
     */
    @Test
    public void test_pdf() {
        InputStream is = page.exportPDF();
        assertThat(is, isPDF());
    }

    /**
     * @see ExportType#PPTX
     * @see UsersRightsJasperReportBean#getPptxFile()
     */
    @Test
    public void test_pptx() {
        InputStream is = page.exportPPTX();
        assertThat(is, isPPTX());
    }

    /**
     * @see ExportType#RTF
     * @see UsersRightsJasperReportBean#getRtfFile()
     */
    @Test
    public void test_rtf() {
        InputStream is = page.exportRTF();
        assertThat(is, isRTF());
    }

    /**
     * @see ExportType#TEXT
     * @see UsersRightsJasperReportBean#getTextFile()
     */
    @Test
    public void test_text() {
        InputStream is = page.exportTEXT();
        assertThat(is, isTXT());
    }

    /**
     * @see ExportType#XLS
     * @see UsersRightsJasperReportBean#getXlsFile()
     */
    @Test
    public void test_xls() {
        InputStream is = page.exportXLS();
        assertThat(is, isXLS());
    }

    /**
     * @see ExportType#XLSX
     * @see UsersRightsJasperReportBean#getXlsxFile()
     */
    @Test
    public void test_xlsx() {
        InputStream is = page.exportXLSX();
        assertThat(is, isXLSX());
    }

    /**
     * @see ExportType#XML
     * @see UsersRightsJasperReportBean#getXmlFile()
     */
    @Test
    public void test_xml() {
        InputStream is = page.exportXML();
        assertThat(is, isXML());
    }

}