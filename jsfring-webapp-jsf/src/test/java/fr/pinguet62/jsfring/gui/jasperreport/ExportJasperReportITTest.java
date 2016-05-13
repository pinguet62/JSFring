package fr.pinguet62.jsfring.gui.jasperreport;

import static fr.pinguet62.jsfring.gui.htmlunit.AbstractPage.get;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isCSV;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isDOCX;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isHTML;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isImage;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isODS;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isODT;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isPDF;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isPPTX;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isRTF;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isTXT;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isXLS;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isXLSX;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isXML;
import static org.junit.Assert.assertThat;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.gui.htmlunit.jasperreport.AbstractJasperReportPage;
import fr.pinguet62.jsfring.gui.htmlunit.jasperreport.UsersRightsJasperReportPage;
import fr.pinguet62.jsfring.gui.jasperreport.sample.UsersRightsJasperReportBean;

/**
 * @see AbstractJasperReportBean#getStreamedContent(ExportType)
 * @see ExportType
 * @see AbstractJasperReportPage
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringBootConfig.class)
@WebIntegrationTest
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