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

    /**
     * Get the resource file test from extension.
     * 
     * @param filename The extension, without {@code ""}.
     * @return The {@link InputStream} of resource.
     */
    private InputStream getFileAsStream(String extension) {
        return getClass().getResourceAsStream("/example." + extension);
    }

    /** @see FileChecker#isCSV(InputStream) */
    @Test
    public void test_isCSV() {
        String path = "csv";
        assertTrue(isCSV(getFileAsStream(path)));
        assertTrue(isTXT(getFileAsStream(path)));
    }

    /** @see FileChecker#isDOC(InputStream) */
    @Test
    public void test_isDOC() {
        String path = "doc";
        assertTrue(isDOC(getFileAsStream(path)));
        assertFalse(isDOCX(getFileAsStream(path)));
        assertFalse(isODP(getFileAsStream(path)));
        assertFalse(isODS(getFileAsStream(path)));
        assertFalse(isODT(getFileAsStream(path)));
        assertFalse(isPPT(getFileAsStream(path)));
        assertFalse(isPPTX(getFileAsStream(path)));
        assertFalse(isRTF(getFileAsStream(path)));
        assertFalse(isXLS(getFileAsStream(path)));
        assertFalse(isXLSX(getFileAsStream(path)));

        assertTrue(isZIP(getFileAsStream(path)));
    }

    /** @see FileChecker#isDOCX(InputStream) */
    @Test
    public void test_isDOCX() {
        String path = "docx";
        assertFalse(isDOC(getFileAsStream(path)));
        assertTrue(isDOCX(getFileAsStream(path)));
        assertFalse(isODP(getFileAsStream(path)));
        assertFalse(isODS(getFileAsStream(path)));
        assertFalse(isODT(getFileAsStream(path)));
        assertFalse(isPPT(getFileAsStream(path)));
        assertFalse(isPPTX(getFileAsStream(path)));
        assertFalse(isRTF(getFileAsStream(path)));
        assertFalse(isXLS(getFileAsStream(path)));
        assertFalse(isXLSX(getFileAsStream(path)));

        assertTrue(isZIP(getFileAsStream(path)));
    }

    /** @see FileChecker#isHTML(InputStream) */
    @Test
    public void test_isHTML() {
        String path = "html";
        assertTrue(isHTML(getFileAsStream(path)));
        assertTrue(isTXT(getFileAsStream(path)));
    }

    /** @see FileChecker#isImage(InputStream) */
    @Test
    public void test_isImage() {
        assertTrue(isImage(getFileAsStream("jpg")));
        assertTrue(isImage(getFileAsStream("png")));
        assertTrue(isImage(getFileAsStream("svg")));
    }

    /** @see FileChecker#isODP(InputStream) */
    @Test
    public void test_isODP() {
        String path = "odp";
        assertFalse(isDOC(getFileAsStream(path)));
        assertFalse(isDOCX(getFileAsStream(path)));
        assertTrue(isODP(getFileAsStream(path)));
        assertFalse(isODS(getFileAsStream(path)));
        assertFalse(isODT(getFileAsStream(path)));
        assertFalse(isPPT(getFileAsStream(path)));
        assertFalse(isPPTX(getFileAsStream(path)));
        assertFalse(isRTF(getFileAsStream(path)));
        assertFalse(isXLS(getFileAsStream(path)));
        assertFalse(isXLSX(getFileAsStream(path)));

        assertTrue(isZIP(getFileAsStream(path)));
    }

    /** @see FileChecker#isODS(InputStream) */
    @Test
    public void test_isODS() {
        String path = "ods";
        assertFalse(isDOC(getFileAsStream(path)));
        assertFalse(isDOCX(getFileAsStream(path)));
        assertFalse(isODP(getFileAsStream(path)));
        assertTrue(isODS(getFileAsStream(path)));
        assertFalse(isODT(getFileAsStream(path)));
        assertFalse(isPPT(getFileAsStream(path)));
        assertFalse(isPPTX(getFileAsStream(path)));
        assertFalse(isRTF(getFileAsStream(path)));
        assertFalse(isXLS(getFileAsStream(path)));
        assertFalse(isXLSX(getFileAsStream(path)));

        assertTrue(isZIP(getFileAsStream(path)));
    }

    /** @see FileChecker#isODT(InputStream) */
    @Test
    public void test_isODT() {
        String path = "odt";
        assertFalse(isDOC(getFileAsStream(path)));
        assertFalse(isDOCX(getFileAsStream(path)));
        assertFalse(isODP(getFileAsStream(path)));
        assertFalse(isODS(getFileAsStream(path)));
        assertTrue(isODT(getFileAsStream(path)));
        assertFalse(isPPT(getFileAsStream(path)));
        assertFalse(isPPTX(getFileAsStream(path)));
        assertFalse(isRTF(getFileAsStream(path)));
        assertFalse(isXLS(getFileAsStream(path)));
        assertFalse(isXLSX(getFileAsStream(path)));

        assertTrue(isZIP(getFileAsStream(path)));
    }

    /** @see FileChecker#isPDF(InputStream) */
    @Test
    public void test_isPDF() {
        assertTrue(isPDF(getFileAsStream("pdf")));
    }

    /** @see FileChecker#isPPT(InputStream) */
    @Test
    public void test_isPPT() {
        String path = "ppt";
        assertFalse(isDOC(getFileAsStream(path)));
        assertFalse(isDOCX(getFileAsStream(path)));
        assertFalse(isODP(getFileAsStream(path)));
        assertFalse(isODS(getFileAsStream(path)));
        assertFalse(isODT(getFileAsStream(path)));
        assertTrue(isPPT(getFileAsStream(path)));
        assertFalse(isPPTX(getFileAsStream(path)));
        assertFalse(isRTF(getFileAsStream(path)));
        assertFalse(isXLS(getFileAsStream(path)));
        assertFalse(isXLSX(getFileAsStream(path)));

        assertTrue(isZIP(getFileAsStream(path)));
    }

    /** @see FileChecker#isPPTX(InputStream) */
    @Test
    public void test_isPPTX() {
        String path = "pptx";
        assertFalse(isDOC(getFileAsStream(path)));
        assertFalse(isDOCX(getFileAsStream(path)));
        assertFalse(isODP(getFileAsStream(path)));
        assertFalse(isODS(getFileAsStream(path)));
        assertFalse(isODT(getFileAsStream(path)));
        assertFalse(isPPT(getFileAsStream(path)));
        assertTrue(isPPTX(getFileAsStream(path)));
        assertFalse(isRTF(getFileAsStream(path)));
        assertFalse(isXLS(getFileAsStream(path)));
        assertFalse(isXLSX(getFileAsStream(path)));

        assertTrue(isZIP(getFileAsStream(path)));
    }

    /** @see FileChecker#isRTF(InputStream) */
    @Test
    public void test_isRTF() {
        String path = "rtf";
        assertFalse(isDOC(getFileAsStream(path)));
        assertFalse(isDOCX(getFileAsStream(path)));
        assertFalse(isODP(getFileAsStream(path)));
        assertFalse(isODS(getFileAsStream(path)));
        assertFalse(isODT(getFileAsStream(path)));
        assertFalse(isPPT(getFileAsStream(path)));
        assertFalse(isPPTX(getFileAsStream(path)));
        assertTrue(isRTF(getFileAsStream(path)));
        assertFalse(isXLS(getFileAsStream(path)));
        assertFalse(isXLSX(getFileAsStream(path)));

        assertTrue(isTXT(getFileAsStream(path)));
    }

    /** @see FileChecker#isTXT(InputStream) */
    @Test
    public void test_isTXT() {
        assertTrue(isTXT(getFileAsStream("txt")));
    }

    /** @see FileChecker#isXLS(InputStream) */
    @Test
    public void test_isXLS() {
        String path = "xls";
        assertFalse(isDOC(getFileAsStream(path)));
        assertFalse(isDOCX(getFileAsStream(path)));
        assertFalse(isODP(getFileAsStream(path)));
        assertFalse(isODS(getFileAsStream(path)));
        assertFalse(isODT(getFileAsStream(path)));
        assertFalse(isPPT(getFileAsStream(path)));
        assertFalse(isPPTX(getFileAsStream(path)));
        assertFalse(isRTF(getFileAsStream(path)));
        assertTrue(isXLS(getFileAsStream(path)));
        assertFalse(isXLSX(getFileAsStream(path)));

        assertTrue(isZIP(getFileAsStream(path)));
    }

    /** @see FileChecker#isXLSX(InputStream) */
    @Test
    public void test_isXLSX() {
        String path = "xlsx";
        assertFalse(isDOC(getFileAsStream(path)));
        assertFalse(isDOCX(getFileAsStream(path)));
        assertFalse(isODP(getFileAsStream(path)));
        assertFalse(isODS(getFileAsStream(path)));
        assertFalse(isODT(getFileAsStream(path)));
        assertFalse(isPPT(getFileAsStream(path)));
        assertFalse(isPPTX(getFileAsStream(path)));
        assertFalse(isRTF(getFileAsStream(path)));
        assertFalse(isXLS(getFileAsStream(path)));
        assertTrue(isXLSX(getFileAsStream(path)));

        assertTrue(isZIP(getFileAsStream(path)));
    }

    /** @see FileChecker#isXML(InputStream) */
    @Test
    public void test_isXML() {
        String path = "xml";
        assertTrue(isXML(getFileAsStream(path)));
        assertTrue(isTXT(getFileAsStream(path)));
    }

    /** @see FileChecker#isZIP(InputStream) */
    @Test
    public void test_isZIP() {
        String path = "zip";
        assertTrue(isZIP(getFileAsStream(path)));
    }

}