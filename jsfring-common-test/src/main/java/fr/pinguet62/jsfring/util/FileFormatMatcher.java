package fr.pinguet62.jsfring.util;

import com.lowagie.text.pdf.PdfReader;
import com.opencsv.CSVReader;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.poifs.filesystem.NotOLE2FileException;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jsoup.Jsoup;
import org.odftoolkit.odfdom.doc.OdfPresentationDocument;
import org.odftoolkit.odfdom.doc.OdfSpreadsheetDocument;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.w3c.tidy.Tidy;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.imageio.ImageIO;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.zip.ZipInputStream;

import static java.lang.Character.isISOControl;
import static java.lang.Character.isWhitespace;
import static javax.imageio.ImageIO.read;
import static javax.xml.parsers.DocumentBuilderFactory.newInstance;
import static org.apache.commons.io.IOUtils.toByteArray;

/**
 * Utility class used to verify is the format file is correct.
 */
public final class FileFormatMatcher {

    /**
     * @return The {@link Matcher} to check that the {@link InputStream} is a CSV file.
     * @see CSVReader
     */
    public static Matcher<InputStream> isCSV() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {
            }

            @Override
            protected boolean matchesSafely(InputStream is) {
                try (CSVReader reader = new CSVReader(new InputStreamReader(is))) {
                    return true;
                } catch (IOException e) {
                    return false;
                }
            }
        };
    }

    /**
     * @return The {@link Matcher} to check that the {@link InputStream} is a DOC file.
     * @see HWPFDocument
     */
    public static Matcher<InputStream> isDOC() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {
            }

            @Override
            protected boolean matchesSafely(InputStream is) {
                try {
                    new HWPFDocument(is);
                    return true;
                } catch (IOException | IllegalArgumentException e) {
                    return false;
                }
            }
        };
    }

    /**
     * @return The {@link Matcher} to check that the {@link InputStream} is a DOCX file.
     * @see XWPFDocument
     */
    public static Matcher<InputStream> isDOCX() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {
            }

            @Override
            protected boolean matchesSafely(InputStream is) {
                try (XWPFDocument docx = new XWPFDocument(is)) {
                    return true;
                } catch (POIXMLException | NotOfficeXmlFileException e) {
                    return false;
                } catch (IOException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        };
    }

    /**
     * @return The {@link Matcher} to check that the {@link InputStream} is a HTML file.
     * @see Jsoup
     */
    public static Matcher<InputStream> isHTML() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {
            }

            @Override
            protected boolean matchesSafely(InputStream is) {
                Tidy tidy = new Tidy();
                tidy.parse(is, new StringWriter());
                return tidy.getParseErrors() == 0;
            }
        };
    }

    /**
     * @return The {@link Matcher} to check that the {@link InputStream} is an <i>image</i> file.
     * @see ImageIO
     */
    public static Matcher<InputStream> isImage() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {
            }

            @Override
            protected boolean matchesSafely(InputStream is) {
                try {
                    read(is);
                    return true;
                } catch (IOException e) {
                    return false;
                }
            }
        };
    }

    /**
     * @return The {@link Matcher} to check that the {@link InputStream} is a ODP file.
     * @see OdfPresentationDocument
     */
    public static Matcher<InputStream> isODP() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {
            }

            @Override
            protected boolean matchesSafely(InputStream is) {
                try (OdfPresentationDocument odp = OdfPresentationDocument.loadDocument(is)) {
                    return true;
                } catch (NullPointerException | IllegalArgumentException | ClassCastException e) {
                    return false;
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }
        };
    }

    /**
     * @return The {@link Matcher} to check that the {@link InputStream} is a ODS file.
     * @see OdfSpreadsheetDocument
     */
    public static Matcher<InputStream> isODS() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {
            }

            @Override
            protected boolean matchesSafely(InputStream is) {
                try (OdfSpreadsheetDocument ods = OdfSpreadsheetDocument.loadDocument(is)) {
                    return true;
                } catch (NullPointerException | IllegalArgumentException | ClassCastException e) {
                    return false;
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }
        };
    }

    /**
     * @return The {@link Matcher} to check that the {@link InputStream} is a ODT file.
     * @see OdfTextDocument
     */
    public static Matcher<InputStream> isODT() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {
            }

            @Override
            protected boolean matchesSafely(InputStream is) {
                try (OdfTextDocument odt = OdfTextDocument.loadDocument(is)) {
                    return true;
                } catch (NullPointerException | IllegalArgumentException | ClassCastException e) {
                    return false;
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }
        };
    }

    /**
     * @return The {@link Matcher} to check that the {@link InputStream} is a PDF file.
     * @see PdfReader
     */
    public static Matcher<InputStream> isPDF() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {
            }

            @Override
            protected boolean matchesSafely(InputStream is) {
                try {
                    new PdfReader(is);
                    return true;
                } catch (IOException e) {
                    return false;
                }
            }
        };
    }

    /**
     * @return The {@link Matcher} to check that the {@link InputStream} is a PPT file.
     * @see HSLFSlideShow
     */
    public static Matcher<InputStream> isPPT() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {
            }

            @Override
            protected boolean matchesSafely(InputStream is) {
                try (HSLFSlideShow ppt = new HSLFSlideShow(is)) {
                    return true;
                } catch (OfficeXmlFileException | NotOLE2FileException | FileNotFoundException e) {
                    return false;
                } catch (IOException ioException) {
                    throw new IllegalArgumentException(ioException);
                }
            }
        };
    }

    /**
     * @return The {@link Matcher} to check that the {@link InputStream} is a PPTX file.
     * @see XMLSlideShow
     */
    public static Matcher<InputStream> isPPTX() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {
            }

            @Override
            protected boolean matchesSafely(InputStream is) {
                try (XMLSlideShow pptx = new XMLSlideShow(is)) {
                    return true;
                } catch (POIXMLException | NotOfficeXmlFileException e) {
                    return false;
                } catch (IOException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        };
    }

    /**
     * @return The {@link Matcher} to check that the {@link InputStream} is a RTF file.
     * @see RTFEditorKit
     */
    public static Matcher<InputStream> isRTF() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {
            }

            @Override
            protected boolean matchesSafely(InputStream is) {
                RTFEditorKit rtfParser = new RTFEditorKit();
                Document document = rtfParser.createDefaultDocument();
                try {
                    rtfParser.read(is, document, 0);
                    return true;
                } catch (NumberFormatException | IOException e) {
                    return false;
                } catch (BadLocationException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        };
    }

    /**
     * @return The {@link Matcher} to check that the {@link InputStream} is a TXT file.
     * @see Character#isISOControl(char)
     */
    public static Matcher<InputStream> isTXT() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {
            }

            @Override
            protected boolean matchesSafely(InputStream is) {
                try {
                    byte[] bytes = toByteArray(is);
                    for (byte b : bytes)
                        if (!isWhitespace(b) && isISOControl(b))
                            return false;
                    return true;
                } catch (IOException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        };
    }

    /**
     * @return The {@link Matcher} to check that the {@link InputStream} is a XLS file.
     * @see HSSFWorkbook
     */
    public static Matcher<InputStream> isXLS() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {
            }

            @Override
            protected boolean matchesSafely(InputStream is) {
                try (HSSFWorkbook xls = new HSSFWorkbook(is)) {
                    return true;
                } catch (NotOLE2FileException | IllegalArgumentException e) {
                    return false;
                } catch (IOException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        };
    }

    /**
     * @return The {@link Matcher} to check that the {@link InputStream} is a XLSX file.
     * @see XSSFWorkbook
     */
    public static Matcher<InputStream> isXLSX() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {
            }

            @Override
            protected boolean matchesSafely(InputStream is) {
                try (XSSFWorkbook xlsx = new XSSFWorkbook(is)) {
                    return true;
                } catch (POIXMLException | NotOfficeXmlFileException e) {
                    return false;
                } catch (IOException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        };

    }

    /**
     * @return The {@link Matcher} to check that the {@link InputStream} is a XML file.
     * @see DocumentBuilder
     */
    public static Matcher<InputStream> isXML() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {
            }

            @Override
            protected boolean matchesSafely(InputStream is) {
                try {
                    DocumentBuilderFactory factory = newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    builder.parse(is);
                    return true;
                } catch (SAXParseException e) {
                    return false;
                } catch (ParserConfigurationException | SAXException | IOException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        };
    }

    /**
     * @return The {@link Matcher} to check that the {@link InputStream} is a ZIP file.
     * @see ZipInputStream
     */
    public static Matcher<InputStream> isZIP() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {
            }

            @Override
            protected boolean matchesSafely(InputStream is) {
                ZipInputStream zipInputStream = new ZipInputStream(is);
                try {
                    while ((zipInputStream.getNextEntry()) != null) {
                    }
                } catch (IOException e) {
                    return false;
                }
                return true;
            }
        };
    }

    // Util
    private FileFormatMatcher() {
    }

}
