package frpinguet62.jsfring.util;

import static fr.pinguet62.jsfring.util.FileChecker.isCSV;
import static fr.pinguet62.jsfring.util.FileChecker.isDOC;
import static fr.pinguet62.jsfring.util.FileChecker.isDOCX;
import static fr.pinguet62.jsfring.util.FileChecker.isHTML;
import static fr.pinguet62.jsfring.util.FileChecker.isImage;
import static fr.pinguet62.jsfring.util.FileChecker.isODP;
import static fr.pinguet62.jsfring.util.FileChecker.isODS;
import static fr.pinguet62.jsfring.util.FileChecker.isODT;
import static fr.pinguet62.jsfring.util.FileChecker.isPDF;
import static fr.pinguet62.jsfring.util.FileChecker.isPPT;
import static fr.pinguet62.jsfring.util.FileChecker.isPPTX;
import static fr.pinguet62.jsfring.util.FileChecker.isRTF;
import static fr.pinguet62.jsfring.util.FileChecker.isTXT;
import static fr.pinguet62.jsfring.util.FileChecker.isXLS;
import static fr.pinguet62.jsfring.util.FileChecker.isXLSX;
import static fr.pinguet62.jsfring.util.FileChecker.isXML;
import static fr.pinguet62.jsfring.util.FileChecker.isZIP;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;

import org.junit.Test;

import fr.pinguet62.jsfring.util.FileChecker;

/**
 * Check the <i>exact type</i> of <b>Microsoft Office</b> and <b>Open
 * Document</b> documents.<br>
 * If a method return {@code true} for a document, all other methods must return
 * {@code false}.<br>
 * For example, if the document is a {@code DOC}, the document cannot be a
 * {@code DOCX}, {@code ODT}, {@code PPT}, ...
 * <p>
 * A document can have several types.<br>
 * For example, a {@code CSV} document is also {@code TXT}.
 * 
 * @see FileChecker
 */
public final class FileCheckerTest {

    /** @see FileChecker#isCSV(InputStream) */
    @Test
    public void test_isCSV() {
        String path = "/example.csv";
        assertTrue(isCSV(getClass().getResourceAsStream(path)));
        assertTrue(isTXT(getClass().getResourceAsStream(path)));
    }

    /** @see FileChecker#isDOC(InputStream) */
    @Test
    public void test_isDOC() {
        String path = "/example.doc";
        assertTrue(isDOC(getClass().getResourceAsStream(path)));
        assertFalse(isDOCX(getClass().getResourceAsStream(path)));
        assertFalse(isODP(getClass().getResourceAsStream(path)));
        assertFalse(isODS(getClass().getResourceAsStream(path)));
        assertFalse(isODT(getClass().getResourceAsStream(path)));
        assertFalse(isPPT(getClass().getResourceAsStream(path)));
        assertFalse(isPPTX(getClass().getResourceAsStream(path)));
        assertFalse(isRTF(getClass().getResourceAsStream(path)));
        assertFalse(isXLS(getClass().getResourceAsStream(path)));
        assertFalse(isXLSX(getClass().getResourceAsStream(path)));

        assertTrue(isZIP(getClass().getResourceAsStream(path)));
    }

    /** @see FileChecker#isDOCX(InputStream) */
    @Test
    public void test_isDOCX() {
        String path = "/example.docx";
        assertFalse(isDOC(getClass().getResourceAsStream(path)));
        assertTrue(isDOCX(getClass().getResourceAsStream(path)));
        assertFalse(isODP(getClass().getResourceAsStream(path)));
        assertFalse(isODS(getClass().getResourceAsStream(path)));
        assertFalse(isODT(getClass().getResourceAsStream(path)));
        assertFalse(isPPT(getClass().getResourceAsStream(path)));
        assertFalse(isPPTX(getClass().getResourceAsStream(path)));
        assertFalse(isRTF(getClass().getResourceAsStream(path)));
        assertFalse(isXLS(getClass().getResourceAsStream(path)));
        assertFalse(isXLSX(getClass().getResourceAsStream(path)));

        assertTrue(isZIP(getClass().getResourceAsStream(path)));
    }

    /** @see FileChecker#isHTML(InputStream) */
    @Test
    public void test_isHTML() {
        String path = "/example.html";
        assertTrue(isHTML(getClass().getResourceAsStream(path)));
        assertTrue(isTXT(getClass().getResourceAsStream(path)));
    }

    /** @see FileChecker#isImage(InputStream) */
    @Test
    public void test_isImage() {
        assertTrue(isImage(getClass().getResourceAsStream("/example.jpg")));
        assertTrue(isImage(getClass().getResourceAsStream("/example.png")));
        assertTrue(isImage(getClass().getResourceAsStream("/example.svg")));
    }

    /** @see FileChecker#isODP(InputStream) */
    @Test
    public void test_isODP() {
        String path = "/example.odp";
        assertFalse(isDOC(getClass().getResourceAsStream(path)));
        assertFalse(isDOCX(getClass().getResourceAsStream(path)));
        assertTrue(isODP(getClass().getResourceAsStream(path)));
        assertFalse(isODS(getClass().getResourceAsStream(path)));
        assertFalse(isODT(getClass().getResourceAsStream(path)));
        assertFalse(isPPT(getClass().getResourceAsStream(path)));
        assertFalse(isPPTX(getClass().getResourceAsStream(path)));
        assertFalse(isRTF(getClass().getResourceAsStream(path)));
        assertFalse(isXLS(getClass().getResourceAsStream(path)));
        assertFalse(isXLSX(getClass().getResourceAsStream(path)));

        assertTrue(isZIP(getClass().getResourceAsStream(path)));
    }

    /** @see FileChecker#isODS(InputStream) */
    @Test
    public void test_isODS() {
        String path = "/example.ods";
        assertFalse(isDOC(getClass().getResourceAsStream(path)));
        assertFalse(isDOCX(getClass().getResourceAsStream(path)));
        assertFalse(isODP(getClass().getResourceAsStream(path)));
        assertTrue(isODS(getClass().getResourceAsStream(path)));
        assertFalse(isODT(getClass().getResourceAsStream(path)));
        assertFalse(isPPT(getClass().getResourceAsStream(path)));
        assertFalse(isPPTX(getClass().getResourceAsStream(path)));
        assertFalse(isRTF(getClass().getResourceAsStream(path)));
        assertFalse(isXLS(getClass().getResourceAsStream(path)));
        assertFalse(isXLSX(getClass().getResourceAsStream(path)));

        assertTrue(isZIP(getClass().getResourceAsStream(path)));
    }

    /** @see FileChecker#isODT(InputStream) */
    @Test
    public void test_isODT() {
        String path = "/example.odt";
        assertFalse(isDOC(getClass().getResourceAsStream(path)));
        assertFalse(isDOCX(getClass().getResourceAsStream(path)));
        assertFalse(isODP(getClass().getResourceAsStream(path)));
        assertFalse(isODS(getClass().getResourceAsStream(path)));
        assertTrue(isODT(getClass().getResourceAsStream(path)));
        assertFalse(isPPT(getClass().getResourceAsStream(path)));
        assertFalse(isPPTX(getClass().getResourceAsStream(path)));
        assertFalse(isRTF(getClass().getResourceAsStream(path)));
        assertFalse(isXLS(getClass().getResourceAsStream(path)));
        assertFalse(isXLSX(getClass().getResourceAsStream(path)));

        assertTrue(isZIP(getClass().getResourceAsStream(path)));
    }

    /** @see FileChecker#isPDF(InputStream) */
    @Test
    public void test_isPDF() {
        assertTrue(isPDF(getClass().getResourceAsStream("/example.pdf")));
    }

    /** @see FileChecker#isPPT(InputStream) */
    @Test
    public void test_isPPT() {
        String path = "/example.ppt";
        assertFalse(isDOC(getClass().getResourceAsStream(path)));
        assertFalse(isDOCX(getClass().getResourceAsStream(path)));
        assertFalse(isODP(getClass().getResourceAsStream(path)));
        assertFalse(isODS(getClass().getResourceAsStream(path)));
        assertFalse(isODT(getClass().getResourceAsStream(path)));
        assertTrue(isPPT(getClass().getResourceAsStream(path)));
        assertFalse(isPPTX(getClass().getResourceAsStream(path)));
        assertFalse(isRTF(getClass().getResourceAsStream(path)));
        assertFalse(isXLS(getClass().getResourceAsStream(path)));
        assertFalse(isXLSX(getClass().getResourceAsStream(path)));

        assertTrue(isZIP(getClass().getResourceAsStream(path)));
    }

    /** @see FileChecker#isPPTX(InputStream) */
    @Test
    public void test_isPPTX() {
        String path = "/example.pptx";
        assertFalse(isDOC(getClass().getResourceAsStream(path)));
        assertFalse(isDOCX(getClass().getResourceAsStream(path)));
        assertFalse(isODP(getClass().getResourceAsStream(path)));
        assertFalse(isODS(getClass().getResourceAsStream(path)));
        assertFalse(isODT(getClass().getResourceAsStream(path)));
        assertFalse(isPPT(getClass().getResourceAsStream(path)));
        assertTrue(isPPTX(getClass().getResourceAsStream(path)));
        assertFalse(isRTF(getClass().getResourceAsStream(path)));
        assertFalse(isXLS(getClass().getResourceAsStream(path)));
        assertFalse(isXLSX(getClass().getResourceAsStream(path)));

        assertTrue(isZIP(getClass().getResourceAsStream(path)));
    }

    /** @see FileChecker#isRTF(InputStream) */
    @Test
    public void test_isRTF() {
        String path = "/example.rtf";
        assertFalse(isDOC(getClass().getResourceAsStream(path)));
        assertFalse(isDOCX(getClass().getResourceAsStream(path)));
        assertFalse(isODP(getClass().getResourceAsStream(path)));
        assertFalse(isODS(getClass().getResourceAsStream(path)));
        assertFalse(isODT(getClass().getResourceAsStream(path)));
        assertFalse(isPPT(getClass().getResourceAsStream(path)));
        assertFalse(isPPTX(getClass().getResourceAsStream(path)));
        assertTrue(isRTF(getClass().getResourceAsStream(path)));
        assertFalse(isXLS(getClass().getResourceAsStream(path)));
        assertFalse(isXLSX(getClass().getResourceAsStream(path)));

        assertTrue(isTXT(getClass().getResourceAsStream(path)));
    }

    /** @see FileChecker#isTXT(InputStream) */
    @Test
    public void test_isTXT() {
        assertTrue(isTXT(getClass().getResourceAsStream("/example.txt")));
    }

    /** @see FileChecker#isXLS(InputStream) */
    @Test
    public void test_isXLS() {
        String path = "/example.xls";
        assertFalse(isDOC(getClass().getResourceAsStream(path)));
        assertFalse(isDOCX(getClass().getResourceAsStream(path)));
        assertFalse(isODP(getClass().getResourceAsStream(path)));
        assertFalse(isODS(getClass().getResourceAsStream(path)));
        assertFalse(isODT(getClass().getResourceAsStream(path)));
        assertFalse(isPPT(getClass().getResourceAsStream(path)));
        assertFalse(isPPTX(getClass().getResourceAsStream(path)));
        assertFalse(isRTF(getClass().getResourceAsStream(path)));
        assertTrue(isXLS(getClass().getResourceAsStream(path)));
        assertFalse(isXLSX(getClass().getResourceAsStream(path)));

        assertTrue(isZIP(getClass().getResourceAsStream(path)));
    }

    /** @see FileChecker#isXLSX(InputStream) */
    @Test
    public void test_isXLSX() {
        String path = "/example.xlsx";
        assertFalse(isDOC(getClass().getResourceAsStream(path)));
        assertFalse(isDOCX(getClass().getResourceAsStream(path)));
        assertFalse(isODP(getClass().getResourceAsStream(path)));
        assertFalse(isODS(getClass().getResourceAsStream(path)));
        assertFalse(isODT(getClass().getResourceAsStream(path)));
        assertFalse(isPPT(getClass().getResourceAsStream(path)));
        assertFalse(isPPTX(getClass().getResourceAsStream(path)));
        assertFalse(isRTF(getClass().getResourceAsStream(path)));
        assertFalse(isXLS(getClass().getResourceAsStream(path)));
        assertTrue(isXLSX(getClass().getResourceAsStream(path)));

        assertTrue(isZIP(getClass().getResourceAsStream(path)));
    }

    /** @see FileChecker#isXML(InputStream) */
    @Test
    public void test_isXML() {
        String path = "/example.xml";
        assertTrue(isXML(getClass().getResourceAsStream(path)));
        assertTrue(isTXT(getClass().getResourceAsStream(path)));
    }

    /** @see FileChecker#isZIP(InputStream) */
    @Test
    public void test_isZIP() {
        String path = "/example.zip";
        assertTrue(isZIP(getClass().getResourceAsStream(path)));
    }

}