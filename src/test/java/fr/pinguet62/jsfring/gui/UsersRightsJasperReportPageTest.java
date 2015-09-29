package fr.pinguet62.jsfring.gui;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import au.com.bytecode.opencsv.CSVReader;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.lowagie.text.pdf.PdfReader;

import fr.pinguet62.Config;
import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.jasperreport.UsersRightsJasperReportPage;

/** @see UsersRightsJasperReportPage */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = Config.SPRING)
@DatabaseSetup(Config.DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class UsersRightsJasperReportPageTest {

    /**
     * The generated file is a CSV file, correctly formatted.
     *
     * @see UsersRightsJasperReportPage#exportCSV()
     * @see CSVReader
     */
    @Test
    public void test_export_csv() throws IOException {
        UsersRightsJasperReportPage reportPage = AbstractPage.get().gotoReportsUsersRightsPage();
        InputStream is = reportPage.exportCSV();
        // Try read
        try (CSVReader reader = new CSVReader(new InputStreamReader(is))) {}
    }

    /**
     * The generated file is a DOCX file.
     *
     * @see UsersRightsJasperReportPage#exportDOCX()
     */
    @Test
    public void test_export_docx() throws IOException {
        UsersRightsJasperReportPage reportPage = AbstractPage.get().gotoReportsUsersRightsPage();
        InputStream is = reportPage.exportDOCX();
        // Check format
        assertTrue(XWPFDocument.hasOOXMLHeader(is));
    }

    /**
     * The generated file is a ODT file.
     *
     * @see UsersRightsJasperReportPage#exportODT()
     * @see OdfTextDocument
     */
    @Test
    public void test_export_odt() throws Exception {
        UsersRightsJasperReportPage reportPage = AbstractPage.get().gotoReportsUsersRightsPage();
        InputStream is = reportPage.exportODT();
        // Check format
        OdfTextDocument.loadDocument(is);
    }

    /**
     * The generated file is a PDF file.
     *
     * @see UsersRightsJasperReportPage#exportPDF()
     * @see PdfReader
     */
    @Test
    public void test_export_pdf() throws IOException {
        UsersRightsJasperReportPage reportPage = AbstractPage.get().gotoReportsUsersRightsPage();
        InputStream is = reportPage.exportPDF();
        // Try read
        new PdfReader(is);
    }

    /**
     * The generated file is a PPTX file.
     *
     * @see UsersRightsJasperReportPage#exportPPTX()
     */
    @Test
    public void test_export_pptx() throws IOException {
        UsersRightsJasperReportPage reportPage = AbstractPage.get().gotoReportsUsersRightsPage();
        InputStream is = reportPage.exportPPTX();
        // Check format
        assertTrue(XMLSlideShow.hasOOXMLHeader(is));
    }

    /**
     * The generated file is a XLSX file.
     *
     * @see UsersRightsJasperReportPage#exportXLSX()
     * @see XSSFWorkbook
     */
    @Test
    public void test_export_xlsx() throws IOException {
        UsersRightsJasperReportPage reportPage = AbstractPage.get().gotoReportsUsersRightsPage();
        InputStream is = reportPage.exportXLSX();
        // Check format
        assertTrue(POIXMLDocument.hasOOXMLHeader(is));
    }

}