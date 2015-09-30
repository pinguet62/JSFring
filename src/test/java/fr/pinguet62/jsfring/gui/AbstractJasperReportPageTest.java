package fr.pinguet62.jsfring.gui;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;

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
import fr.pinguet62.jsfring.gui.jasperreport.AbstractJasperReportBean;
import fr.pinguet62.jsfring.gui.jasperreport.ExportType;
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
public class AbstractJasperReportPageTest {

    /**
     * @see ExportType#CSV
     * @see UsersRightsJasperReportBean#getCsvFile()
     */
    @Test
    public void test_export_csv() throws IOException {
        UsersRightsJasperReportPage reportPage = AbstractPage.get().gotoReportsUsersRightsPage();
        InputStream is = reportPage.exportCSV();
        assertTrue(FileChecker.isCSV(is));
    }

    /**
     * @see ExportType#DOCX
     * @see UsersRightsJasperReportBean#getDocxFile()
     */
    @Test
    public void test_export_docx() throws IOException {
        UsersRightsJasperReportPage reportPage = AbstractPage.get().gotoReportsUsersRightsPage();
        InputStream is = reportPage.exportDOCX();
        assertTrue(FileChecker.isDOCX(is));
    }

    /**
     * @see ExportType#GRAPHICS_2D
     * @see UsersRightsJasperReportBean#getGraphics2dFile()
     */
    @Test
    public void test_export_graphics2d() throws IOException {
        UsersRightsJasperReportPage reportPage = AbstractPage.get().gotoReportsUsersRightsPage();
        InputStream is = reportPage.exportGraphics2D();
        assertTrue(FileChecker.isImage(is));
    }

    /**
     * @see ExportType#HTML
     * @see UsersRightsJasperReportBean#getHtmlFile()
     */
    @Test
    public void test_export_html() throws Exception {
        UsersRightsJasperReportPage reportPage = AbstractPage.get().gotoReportsUsersRightsPage();
        InputStream is = reportPage.exportHTML();
        assertTrue(FileChecker.isHTML(is));
    }

    /**
     * @see ExportType#ODS
     * @see UsersRightsJasperReportBean#getOdsFile()
     */
    @Test
    public void test_export_ods() throws Exception {
        UsersRightsJasperReportPage reportPage = AbstractPage.get().gotoReportsUsersRightsPage();
        InputStream is = reportPage.exportODS();
        assertTrue(FileChecker.isODS(is));
    }

    /**
     * @see ExportType#ODT
     * @see UsersRightsJasperReportBean#getOdtFile()
     */
    @Test
    public void test_export_odt() throws Exception {
        UsersRightsJasperReportPage reportPage = AbstractPage.get().gotoReportsUsersRightsPage();
        InputStream is = reportPage.exportODT();
        assertTrue(FileChecker.isODT(is));
    }

    /**
     * @see ExportType#PDF
     * @see UsersRightsJasperReportBean#getPdfFile()
     */
    @Test
    public void test_export_pdf() {
        UsersRightsJasperReportPage reportPage = AbstractPage.get().gotoReportsUsersRightsPage();
        InputStream is = reportPage.exportPDF();
        assertTrue(FileChecker.isPDF(is));
    }

    /**
     * @see ExportType#PPTX
     * @see UsersRightsJasperReportBean#getPptxFile()
     */
    @Test
    public void test_export_pptx() throws IOException {
        UsersRightsJasperReportPage reportPage = AbstractPage.get().gotoReportsUsersRightsPage();
        InputStream is = reportPage.exportPPTX();
        assertTrue(FileChecker.isPPTX(is));
    }

    /**
     * @see ExportType#RTF
     * @see UsersRightsJasperReportBean#getRtfFile()
     */
    @Test
    public void test_export_rtf() {
        UsersRightsJasperReportPage reportPage = AbstractPage.get().gotoReportsUsersRightsPage();
        InputStream is = reportPage.exportRTF();
        assertTrue(FileChecker.isRTF(is));
    }

    /**
     * @see ExportType#TEXT
     * @see UsersRightsJasperReportBean#getTextFile()
     */
    @Test
    public void test_export_text() {
        UsersRightsJasperReportPage reportPage = AbstractPage.get().gotoReportsUsersRightsPage();
        InputStream is = reportPage.exportTEXT();
        assertTrue(FileChecker.isTXT(is));
    }

    /**
     * @see ExportType#XLS
     * @see UsersRightsJasperReportBean#getXlsFile()
     */
    @Test
    public void test_export_xls() throws IOException {
        UsersRightsJasperReportPage reportPage = AbstractPage.get().gotoReportsUsersRightsPage();
        InputStream is = reportPage.exportXLS();
        assertTrue(FileChecker.isXLS(is));
    }

    /**
     * @see ExportType#XLSX
     * @see UsersRightsJasperReportBean#getXlsxFile()
     */
    @Test
    public void test_export_xlsx() throws IOException {
        UsersRightsJasperReportPage reportPage = AbstractPage.get().gotoReportsUsersRightsPage();
        InputStream is = reportPage.exportXLSX();
        assertTrue(FileChecker.isXLSX(is));
    }

    /**
     * @see ExportType#XML
     * @see UsersRightsJasperReportBean#getXmlFile()
     */
    @Test
    public void test_export_xml() throws IOException {
        UsersRightsJasperReportPage reportPage = AbstractPage.get().gotoReportsUsersRightsPage();
        InputStream is = reportPage.exportXML();
        assertTrue(FileChecker.isXML(is));
    }

}