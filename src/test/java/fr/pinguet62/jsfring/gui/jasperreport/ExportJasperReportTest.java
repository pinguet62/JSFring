package fr.pinguet62.jsfring.gui.jasperreport;

import static org.junit.Assert.assertTrue;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.Config;
import fr.pinguet62.FileChecker;
import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.jasperreport.AbstractJasperReportPage;
import fr.pinguet62.jsfring.gui.htmlunit.jasperreport.UsersRightsJasperReportPage;
import fr.pinguet62.jsfring.gui.jasperreport.sample.UsersRightsJasperReportBean;

/**
 * @see AbstractJasperReportBean#getStreamedContent(ExportType)
 * @see ExportType
 * @see AbstractJasperReportPage
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = Config.SPRING)
@DatabaseSetup(Config.DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class ExportJasperReportTest {

    private UsersRightsJasperReportPage page;

    @Before
    public void before() {
        page = AbstractPage.get().gotoReportsUsersRightsPage();
    }

    /**
     * @see ExportType#CSV
     * @see UsersRightsJasperReportBean#getCsvFile()
     */
    @Test
    public void test_csv() {
        InputStream is = page.exportCSV();
        assertTrue(FileChecker.isCSV(is));
    }

    /**
     * @see ExportType#DOCX
     * @see UsersRightsJasperReportBean#getDocxFile()
     */
    @Test
    public void test_docx() {
        InputStream is = page.exportDOCX();
        assertTrue(FileChecker.isDOCX(is));
    }

    /**
     * @see ExportType#GRAPHICS_2D
     * @see UsersRightsJasperReportBean#getGraphics2dFile()
     */
    @Test
    public void test_graphics2d() {
        InputStream is = page.exportGraphics2D();
        assertTrue(FileChecker.isImage(is));
    }

    /**
     * @see ExportType#HTML
     * @see UsersRightsJasperReportBean#getHtmlFile()
     */
    @Test
    public void test_html() {
        InputStream is = page.exportHTML();
        assertTrue(FileChecker.isHTML(is));
    }

    /**
     * @see ExportType#ODS
     * @see UsersRightsJasperReportBean#getOdsFile()
     */
    @Test
    public void test_ods() {
        InputStream is = page.exportODS();
        assertTrue(FileChecker.isODS(is));
    }

    /**
     * @see ExportType#ODT
     * @see UsersRightsJasperReportBean#getOdtFile()
     */
    @Test
    public void test_odt() throws Exception {
        InputStream is = page.exportODT();
        assertTrue(FileChecker.isODT(is));
    }

    /**
     * @see ExportType#PDF
     * @see UsersRightsJasperReportBean#getPdfFile()
     */
    @Test
    public void test_pdf() {
        InputStream is = page.exportPDF();
        assertTrue(FileChecker.isPDF(is));
    }

    /**
     * @see ExportType#PPTX
     * @see UsersRightsJasperReportBean#getPptxFile()
     */
    @Test
    public void test_pptx() {
        InputStream is = page.exportPPTX();
        assertTrue(FileChecker.isPPTX(is));
    }

    /**
     * @see ExportType#RTF
     * @see UsersRightsJasperReportBean#getRtfFile()
     */
    @Test
    public void test_rtf() {
        InputStream is = page.exportRTF();
        assertTrue(FileChecker.isRTF(is));
    }

    /**
     * @see ExportType#TEXT
     * @see UsersRightsJasperReportBean#getTextFile()
     */
    @Test
    public void test_text() {
        InputStream is = page.exportTEXT();
        assertTrue(FileChecker.isTXT(is));
    }

    /**
     * @see ExportType#XLS
     * @see UsersRightsJasperReportBean#getXlsFile()
     */
    @Test
    public void test_xls() {
        InputStream is = page.exportXLS();
        assertTrue(FileChecker.isXLS(is));
    }

    /**
     * @see ExportType#XLSX
     * @see UsersRightsJasperReportBean#getXlsxFile()
     */
    @Test
    public void test_xlsx() {
        InputStream is = page.exportXLSX();
        assertTrue(FileChecker.isXLSX(is));
    }

    /**
     * @see ExportType#XML
     * @see UsersRightsJasperReportBean#getXmlFile()
     */
    @Test
    public void test_xml() {
        InputStream is = page.exportXML();
        assertTrue(FileChecker.isXML(is));
    }

}