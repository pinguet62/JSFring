package frpinguet62.jsfring.util;

import static fr.pinguet62.jsfring.util.FileFormatMatcher.isCSV;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isDOC;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isDOCX;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isHTML;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isImage;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isODP;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isODS;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isODT;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isPDF;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isPPT;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isPPTX;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isRTF;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isTXT;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isXLS;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isXLSX;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isXML;
import static fr.pinguet62.jsfring.util.FileFormatMatcher.isZIP;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.io.InputStream;

import org.junit.Test;

import fr.pinguet62.jsfring.util.FileFormatMatcher;

/**
 * Check the <i>exact type</i> of <b>Microsoft Office</b> and <b>Open Document</b> documents.<br>
 * If a method return {@code true} for a document, all other methods must return {@code false}.<br>
 * For example, if the document is a {@code DOC}, the document cannot be a {@code DOCX}, {@code ODT}, {@code PPT}, ...
 * <p>
 * A document can have several types.<br>
 * For example, a {@code CSV} document is also {@code TXT}.
 *
 * @see FileFormatMatcher
 */
public final class FileFormatMatcherTest {

    /**
     * Get the resource file test from extension.
     *
     * @param filename The extension, without {@code ""}.
     * @return The {@link InputStream} of resource.
     */
    private InputStream getFileAsStream(String extension) {
        return getClass().getResourceAsStream("/example." + extension);
    }

    /** @see FileFormatMatcher#isCSV(InputStream) */
    @Test
    public void test_isCSV() {
        String path = "csv";
        assertThat(getFileAsStream(path), isCSV());
        assertThat(getFileAsStream(path), not(isDOC()));
        assertThat(getFileAsStream(path), not(isDOCX()));
        // assertThat(getFileAsStream(path), not(isHTML()));
        assertThat(getFileAsStream(path), not(isODP()));
        assertThat(getFileAsStream(path), not(isODS()));
        assertThat(getFileAsStream(path), not(isODT()));
        assertThat(getFileAsStream(path), not(isPDF()));
        assertThat(getFileAsStream(path), not(isPPT()));
        assertThat(getFileAsStream(path), not(isPPTX()));
        // assertThat(getFileAsStream(path), not(isRTF()));
        assertThat(getFileAsStream(path), isTXT());
        assertThat(getFileAsStream(path), not(isXLS()));
        assertThat(getFileAsStream(path), not(isXLSX()));
        assertThat(getFileAsStream(path), not(isXML()));
        // assertThat(getFileAsStream(path), not(isZIP()));
    }

    /** @see FileFormatMatcher#isDOC(InputStream) */
    @Test
    public void test_isDOC() {
        String path = "doc";
        // assertThat(getFileAsStream(path), not(isCSV()));
        assertThat(getFileAsStream(path), isDOC());
        assertThat(getFileAsStream(path), not(isDOCX()));
        assertThat(getFileAsStream(path), not(isHTML()));
        assertThat(getFileAsStream(path), not(isODP()));
        assertThat(getFileAsStream(path), not(isODS()));
        assertThat(getFileAsStream(path), not(isODT()));
        assertThat(getFileAsStream(path), not(isPDF()));
        assertThat(getFileAsStream(path), not(isPPT()));
        assertThat(getFileAsStream(path), not(isPPTX()));
        assertThat(getFileAsStream(path), not(isRTF()));
        assertThat(getFileAsStream(path), not(isTXT()));
        assertThat(getFileAsStream(path), not(isXLS()));
        assertThat(getFileAsStream(path), not(isXLSX()));
        assertThat(getFileAsStream(path), not(isXML()));
        assertThat(getFileAsStream(path), isZIP());
    }

    /** @see FileFormatMatcher#isDOCX(InputStream) */
    @Test
    public void test_isDOCX() {
        String path = "docx";
        // assertThat(getFileAsStream(path), not(isCSV()));
        assertThat(getFileAsStream(path), not(isDOC()));
        assertThat(getFileAsStream(path), isDOCX());
        assertThat(getFileAsStream(path), not(isHTML()));
        assertThat(getFileAsStream(path), not(isODP()));
        assertThat(getFileAsStream(path), not(isODS()));
        assertThat(getFileAsStream(path), not(isODT()));
        assertThat(getFileAsStream(path), not(isPDF()));
        assertThat(getFileAsStream(path), not(isPPT()));
        assertThat(getFileAsStream(path), not(isPPTX()));
        assertThat(getFileAsStream(path), not(isRTF()));
        assertThat(getFileAsStream(path), not(isTXT()));
        assertThat(getFileAsStream(path), not(isXLS()));
        assertThat(getFileAsStream(path), not(isXLSX()));
        assertThat(getFileAsStream(path), not(isXML()));
        assertThat(getFileAsStream(path), isZIP());
    }

    /** @see FileFormatMatcher#isHTML(InputStream) */
    @Test
    public void test_isHTML() {
        String path = "html";
        // assertThat(getFileAsStream(path), not(isCSV()));
        assertThat(getFileAsStream(path), not(isDOC()));
        assertThat(getFileAsStream(path), not(isDOCX()));
        assertThat(getFileAsStream(path), isHTML());
        assertThat(getFileAsStream(path), not(isODP()));
        assertThat(getFileAsStream(path), not(isODS()));
        assertThat(getFileAsStream(path), not(isODT()));
        assertThat(getFileAsStream(path), not(isPDF()));
        assertThat(getFileAsStream(path), not(isPPT()));
        assertThat(getFileAsStream(path), not(isPPTX()));
        // assertThat(getFileAsStream(path), not(isRTF()));
        assertThat(getFileAsStream(path), isTXT());
        assertThat(getFileAsStream(path), not(isXLS()));
        assertThat(getFileAsStream(path), not(isXLSX()));
        assertThat(getFileAsStream(path), not(isXML()));
        // assertThat(getFileAsStream(path), not(isZIP()));
    }

    /** @see FileFormatMatcher#isImage(InputStream) */
    @Test
    public void test_isImage() {
        assertThat(getFileAsStream("jpg"), isImage());
        assertThat(getFileAsStream("png"), isImage());
        assertThat(getFileAsStream("svg"), isImage());
    }

    /** @see FileFormatMatcher#isODP(InputStream) */
    @Test
    public void test_isODP() {
        String path = "odp";
        // assertThat(getFileAsStream(path), not(isCSV()));
        assertThat(getFileAsStream(path), not(isDOC()));
        assertThat(getFileAsStream(path), not(isDOCX()));
        assertThat(getFileAsStream(path), not(isHTML()));
        assertThat(getFileAsStream(path), isODP());
        assertThat(getFileAsStream(path), not(isODS()));
        assertThat(getFileAsStream(path), not(isODT()));
        assertThat(getFileAsStream(path), not(isPDF()));
        assertThat(getFileAsStream(path), not(isPPT()));
        assertThat(getFileAsStream(path), not(isPPTX()));
        assertThat(getFileAsStream(path), not(isRTF()));
        assertThat(getFileAsStream(path), not(isTXT()));
        assertThat(getFileAsStream(path), not(isXLS()));
        assertThat(getFileAsStream(path), not(isXLSX()));
        assertThat(getFileAsStream(path), not(isXML()));
        assertThat(getFileAsStream(path), isZIP());
    }

    /** @see FileFormatMatcher#isODS(InputStream) */
    @Test
    public void test_isODS() {
        String path = "ods";
        // assertThat(getFileAsStream(path), not(isCSV()));
        assertThat(getFileAsStream(path), not(isDOC()));
        assertThat(getFileAsStream(path), not(isDOCX()));
        assertThat(getFileAsStream(path), not(isHTML()));
        assertThat(getFileAsStream(path), not(isODP()));
        assertThat(getFileAsStream(path), isODS());
        assertThat(getFileAsStream(path), not(isODT()));
        assertThat(getFileAsStream(path), not(isPDF()));
        assertThat(getFileAsStream(path), not(isPPT()));
        assertThat(getFileAsStream(path), not(isPPTX()));
        assertThat(getFileAsStream(path), not(isRTF()));
        assertThat(getFileAsStream(path), not(isTXT()));
        assertThat(getFileAsStream(path), not(isXLS()));
        assertThat(getFileAsStream(path), not(isXLSX()));
        assertThat(getFileAsStream(path), not(isXML()));
        assertThat(getFileAsStream(path), isZIP());
    }

    /** @see FileFormatMatcher#isODT(InputStream) */
    @Test
    public void test_isODT() {
        String path = "odt";
        // assertThat(getFileAsStream(path), not(isCSV()));
        assertThat(getFileAsStream(path), not(isDOC()));
        assertThat(getFileAsStream(path), not(isDOCX()));
        assertThat(getFileAsStream(path), not(isHTML()));
        assertThat(getFileAsStream(path), not(isODP()));
        assertThat(getFileAsStream(path), not(isODS()));
        assertThat(getFileAsStream(path), isODT());
        assertThat(getFileAsStream(path), not(isPDF()));
        assertThat(getFileAsStream(path), not(isPPT()));
        assertThat(getFileAsStream(path), not(isPPTX()));
        assertThat(getFileAsStream(path), not(isRTF()));
        assertThat(getFileAsStream(path), not(isTXT()));
        assertThat(getFileAsStream(path), not(isXLS()));
        assertThat(getFileAsStream(path), not(isXLSX()));
        assertThat(getFileAsStream(path), not(isXML()));
        assertThat(getFileAsStream(path), isZIP());
    }

    /** @see FileFormatMatcher#isPDF(InputStream) */
    @Test
    public void test_isPDF() {
        String path = "pdf";
        // assertThat(getFileAsStream(path), not(isCSV()));
        assertThat(getFileAsStream(path), not(isDOC()));
        assertThat(getFileAsStream(path), not(isDOCX()));
        assertThat(getFileAsStream(path), not(isHTML()));
        assertThat(getFileAsStream(path), not(isODP()));
        assertThat(getFileAsStream(path), not(isODS()));
        assertThat(getFileAsStream(path), not(isODT()));
        assertThat(getFileAsStream(path), isPDF());
        assertThat(getFileAsStream(path), not(isPPT()));
        assertThat(getFileAsStream(path), not(isPPTX()));
        assertThat(getFileAsStream(path), not(isRTF()));
        assertThat(getFileAsStream(path), not(isTXT()));
        assertThat(getFileAsStream(path), not(isXLS()));
        assertThat(getFileAsStream(path), not(isXLSX()));
        assertThat(getFileAsStream(path), not(isXML()));
        // assertThat(getFileAsStream(path), not(isZIP()));
    }

    /** @see FileFormatMatcher#isPPT(InputStream) */
    @Test
    public void test_isPPT() {
        String path = "ppt";
        // assertThat(getFileAsStream(path), not(isCSV()));
        assertThat(getFileAsStream(path), not(isDOC()));
        assertThat(getFileAsStream(path), not(isDOCX()));
        assertThat(getFileAsStream(path), not(isHTML()));
        assertThat(getFileAsStream(path), not(isODP()));
        assertThat(getFileAsStream(path), not(isODS()));
        assertThat(getFileAsStream(path), not(isODT()));
        assertThat(getFileAsStream(path), not(isPDF()));
        assertThat(getFileAsStream(path), isPPT());
        assertThat(getFileAsStream(path), not(isPPTX()));
        assertThat(getFileAsStream(path), not(isRTF()));
        assertThat(getFileAsStream(path), not(isTXT()));
        assertThat(getFileAsStream(path), not(isXLS()));
        assertThat(getFileAsStream(path), not(isXLSX()));
        assertThat(getFileAsStream(path), not(isXML()));
        assertThat(getFileAsStream(path), isZIP());
    }

    /** @see FileFormatMatcher#isPPTX(InputStream) */
    @Test
    public void test_isPPTX() {
        String path = "pptx";
        // assertThat(getFileAsStream(path), not(isCSV()));
        assertThat(getFileAsStream(path), not(isDOC()));
        assertThat(getFileAsStream(path), not(isDOCX()));
        assertThat(getFileAsStream(path), not(isHTML()));
        assertThat(getFileAsStream(path), not(isODP()));
        assertThat(getFileAsStream(path), not(isODS()));
        assertThat(getFileAsStream(path), not(isODT()));
        assertThat(getFileAsStream(path), not(isPDF()));
        assertThat(getFileAsStream(path), not(isPPT()));
        assertThat(getFileAsStream(path), isPPTX());
        assertThat(getFileAsStream(path), not(isRTF()));
        assertThat(getFileAsStream(path), not(isTXT()));
        assertThat(getFileAsStream(path), not(isXLS()));
        assertThat(getFileAsStream(path), not(isXLSX()));
        assertThat(getFileAsStream(path), not(isXML()));
        assertThat(getFileAsStream(path), isZIP());
    }

    /** @see FileFormatMatcher#isRTF(InputStream) */
    @Test
    public void test_isRTF() {
        String path = "rtf";
        // assertThat(getFileAsStream(path), not(isCSV()));
        assertThat(getFileAsStream(path), not(isDOC()));
        assertThat(getFileAsStream(path), not(isDOCX()));
        // assertThat(getFileAsStream(path), not(isHTML()));
        assertThat(getFileAsStream(path), not(isODP()));
        assertThat(getFileAsStream(path), not(isODS()));
        assertThat(getFileAsStream(path), not(isODT()));
        assertThat(getFileAsStream(path), not(isPDF()));
        assertThat(getFileAsStream(path), not(isPPT()));
        assertThat(getFileAsStream(path), not(isPPTX()));
        assertThat(getFileAsStream(path), isRTF());
        assertThat(getFileAsStream(path), isTXT());
        assertThat(getFileAsStream(path), not(isXLS()));
        assertThat(getFileAsStream(path), not(isXLSX()));
        assertThat(getFileAsStream(path), not(isXML()));
        // assertThat(getFileAsStream(path), not(isZIP()));
    }

    /** @see FileFormatMatcher#isTXT(InputStream) */
    @Test
    public void test_isTXT() {
        String path = "txt";
        // assertThat(getFileAsStream(path), not(isCSV()));
        assertThat(getFileAsStream(path), not(isDOC()));
        assertThat(getFileAsStream(path), not(isDOCX()));
        // assertThat(getFileAsStream(path), not(isHTML()));
        assertThat(getFileAsStream(path), not(isODP()));
        assertThat(getFileAsStream(path), not(isODS()));
        assertThat(getFileAsStream(path), not(isODT()));
        assertThat(getFileAsStream(path), not(isPDF()));
        assertThat(getFileAsStream(path), not(isPPT()));
        assertThat(getFileAsStream(path), not(isPPTX()));
        // assertThat(getFileAsStream(path), not(isRTF()));
        assertThat(getFileAsStream(path), isTXT());
        assertThat(getFileAsStream(path), not(isXLS()));
        assertThat(getFileAsStream(path), not(isXLSX()));
        assertThat(getFileAsStream(path), not(isXML()));
        // assertThat(getFileAsStream(path), not(isZIP()));
    }

    /** @see FileFormatMatcher#isXLS(InputStream) */
    @Test
    public void test_isXLS() {
        String path = "xls";
        // assertThat(getFileAsStream(path), not(isCSV()));
        assertThat(getFileAsStream(path), not(isDOC()));
        assertThat(getFileAsStream(path), not(isDOCX()));
        assertThat(getFileAsStream(path), not(isHTML()));
        assertThat(getFileAsStream(path), not(isODP()));
        assertThat(getFileAsStream(path), not(isODS()));
        assertThat(getFileAsStream(path), not(isODT()));
        assertThat(getFileAsStream(path), not(isPDF()));
        assertThat(getFileAsStream(path), not(isPPT()));
        assertThat(getFileAsStream(path), not(isPPTX()));
        assertThat(getFileAsStream(path), not(isRTF()));
        assertThat(getFileAsStream(path), not(isTXT()));
        assertThat(getFileAsStream(path), isXLS());
        assertThat(getFileAsStream(path), not(isXLSX()));
        assertThat(getFileAsStream(path), not(isXML()));
        assertThat(getFileAsStream(path), isZIP());
    }

    /** @see FileFormatMatcher#isXLSX(InputStream) */
    @Test
    public void test_isXLSX() {
        String path = "xlsx";
        // assertThat(getFileAsStream(path), not(isCSV()));
        assertThat(getFileAsStream(path), not(isDOC()));
        assertThat(getFileAsStream(path), not(isDOCX()));
        assertThat(getFileAsStream(path), not(isHTML()));
        assertThat(getFileAsStream(path), not(isODP()));
        assertThat(getFileAsStream(path), not(isODS()));
        assertThat(getFileAsStream(path), not(isODT()));
        assertThat(getFileAsStream(path), not(isPDF()));
        assertThat(getFileAsStream(path), not(isPPT()));
        assertThat(getFileAsStream(path), not(isPPTX()));
        assertThat(getFileAsStream(path), not(isRTF()));
        assertThat(getFileAsStream(path), not(isTXT()));
        assertThat(getFileAsStream(path), not(isXLS()));
        assertThat(getFileAsStream(path), isXLSX());
        assertThat(getFileAsStream(path), not(isXML()));
        assertThat(getFileAsStream(path), isZIP());
    }

    /** @see FileFormatMatcher#isXML(InputStream) */
    @Test
    public void test_isXML() {
        String path = "xml";
        // assertThat(getFileAsStream(path), not(isCSV()));
        assertThat(getFileAsStream(path), not(isDOC()));
        assertThat(getFileAsStream(path), not(isDOCX()));
        assertThat(getFileAsStream(path), not(isHTML()));
        assertThat(getFileAsStream(path), not(isODP()));
        assertThat(getFileAsStream(path), not(isODS()));
        assertThat(getFileAsStream(path), not(isODT()));
        assertThat(getFileAsStream(path), not(isPDF()));
        assertThat(getFileAsStream(path), not(isPPT()));
        assertThat(getFileAsStream(path), not(isPPTX()));
        // assertThat(getFileAsStream(path), not(isRTF()));
        assertThat(getFileAsStream(path), isTXT());
        assertThat(getFileAsStream(path), not(isXLS()));
        assertThat(getFileAsStream(path), not(isXLSX()));
        assertThat(getFileAsStream(path), isXML());
        // assertThat(getFileAsStream(path), not(isZIP()));
    }

    /** @see FileFormatMatcher#isZIP(InputStream) */
    @Test
    public void test_isZIP() {
        String path = "zip";
        // assertThat(getFileAsStream(path), not(isCSV()));
        assertThat(getFileAsStream(path), not(isDOC()));
        assertThat(getFileAsStream(path), not(isDOCX()));
        // assertThat(getFileAsStream(path), not(isHTML()));
        assertThat(getFileAsStream(path), not(isODP()));
        assertThat(getFileAsStream(path), not(isODS()));
        assertThat(getFileAsStream(path), not(isODT()));
        assertThat(getFileAsStream(path), not(isPDF()));
        assertThat(getFileAsStream(path), not(isPPT()));
        assertThat(getFileAsStream(path), not(isPPTX()));
        // assertThat(getFileAsStream(path), not(isRTF()));
        assertThat(getFileAsStream(path), not(isTXT()));
        assertThat(getFileAsStream(path), not(isXLS()));
        assertThat(getFileAsStream(path), not(isXLSX()));
        assertThat(getFileAsStream(path), not(isXML()));
        assertThat(getFileAsStream(path), isZIP());
    }

}