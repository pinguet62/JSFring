package fr.pinguet62.jsfring;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jsoup.Jsoup;
import org.odftoolkit.odfdom.doc.OdfSpreadsheetDocument;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.xml.sax.SAXException;

import au.com.bytecode.opencsv.CSVReader;

import com.lowagie.text.pdf.PdfReader;

/** Utility class used to verify is the format file is correct. */
public final class FileChecker {

    /** @see CSVReader */
    public static boolean isCSV(InputStream is) {
        try (CSVReader reader = new CSVReader(new InputStreamReader(is))) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /** @see XWPFDocument */
    public static boolean isDOCX(InputStream is) {
        try {
            return XWPFDocument.hasOOXMLHeader(is);
        } catch (IOException e) {
            return false;
        }
    }

    /** @see Jsoup */
    public static boolean isHTML(InputStream is) {
        try {
            String html = IOUtils.toString(is);
            // TODO Jsoup doesn't throw Exception
            Jsoup.parse(html);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /** @see ImageIO */
    public static boolean isImage(InputStream is) {
        try {
            ImageIO.read(is);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /** @see OdfSpreadsheetDocument */
    public static boolean isODS(InputStream is) {
        try {
            OdfSpreadsheetDocument.loadDocument(is);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** @see OdfTextDocument */
    public static boolean isODT(InputStream is) {
        try {
            OdfTextDocument.loadDocument(is);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** @see PdfReader */
    public static boolean isPDF(InputStream is) {
        try {
            new PdfReader(is);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /** @see XMLSlideShow */
    public static boolean isPPTX(InputStream is) {
        try {
            return XMLSlideShow.hasOOXMLHeader(is);
        } catch (IOException e) {
            return false;
        }
    }

    /** @see RTFEditorKit */
    public static boolean isRTF(InputStream is) {
        RTFEditorKit rtfParser = new RTFEditorKit();
        Document document = rtfParser.createDefaultDocument();
        try {
            rtfParser.read(is, document, 0);
            return true;
        } catch (IOException | BadLocationException e) {
            return false;
        }
    }

    /** @see Character#isISOControl(char) */
    public static boolean isTXT(InputStream is) {
        try {
            byte[] bytes = IOUtils.toByteArray(is);
            for (byte b : bytes)
                if (!Character.isWhitespace(b) && Character.isISOControl(b))
                    return false;
            return true;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /** @see POIFSFileSystem */
    public static boolean isXLS(InputStream is) {
        try {
            return POIFSFileSystem.hasPOIFSHeader(is);
        } catch (IOException e) {
            return false;
        }
    }

    /** @see POIXMLDocument */
    public static boolean isXLSX(InputStream is) {
        try {
            return POIXMLDocument.hasOOXMLHeader(is);
        } catch (IOException e) {
            return false;
        }
    }

    /** @see DocumentBuilder */
    public static boolean isXML(InputStream is) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.parse(is);
            return true;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            return false;
        }
    }

    // Util
    private FileChecker() {}

}