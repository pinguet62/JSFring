package fr.pinguet62.jsfring.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
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
import org.xml.sax.SAXException;

import au.com.bytecode.opencsv.CSVReader;

import com.lowagie.text.pdf.PdfReader;

// TODO Exception: catch(Exception) to high level Exception
/** Utility class used to verify is the format file is correct. */
public final class FileFormatMatcher {

    /** @see CSVReader */
    public static Matcher<InputStream> isCSV() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {}

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

    /** @see HWPFDocument */
    public static Matcher<InputStream> isDOC() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {}

            @Override
            protected boolean matchesSafely(InputStream is) {
                try {
                    new HWPFDocument(is);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        };
    }

    /** @see XWPFDocument */
    public static Matcher<InputStream> isDOCX() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {}

            @Override
            protected boolean matchesSafely(InputStream is) {
                try {
                    new XWPFDocument(is);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        };
    }

    /** @see Jsoup */
    public static Matcher<InputStream> isHTML() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {}

            @Override
            protected boolean matchesSafely(InputStream is) {
                try {
                    String html = IOUtils.toString(is);
                    // TODO Jsoup doesn't throw Exception
                    Jsoup.parse(html);
                    return true;
                } catch (IOException e) {
                    return false;
                }
            }
        };
    }

    /** @see ImageIO */
    public static Matcher<InputStream> isImage() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {}

            @Override
            protected boolean matchesSafely(InputStream is) {
                try {
                    ImageIO.read(is);
                    return true;
                } catch (IOException e) {
                    return false;
                }
            }
        };
    }

    /** @see OdfPresentationDocument */
    public static Matcher<InputStream> isODP() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {}

            @Override
            protected boolean matchesSafely(InputStream is) {
                try {
                    OdfPresentationDocument.loadDocument(is);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        };
    }

    /** @see OdfSpreadsheetDocument */
    public static Matcher<InputStream> isODS() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {}

            @Override
            protected boolean matchesSafely(InputStream is) {
                try {
                    OdfSpreadsheetDocument.loadDocument(is);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        };
    }

    /** @see OdfTextDocument */
    public static Matcher<InputStream> isODT() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {}

            @Override
            protected boolean matchesSafely(InputStream is) {
                try {
                    OdfTextDocument.loadDocument(is);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        };
    }

    /** @see PdfReader */
    public static Matcher<InputStream> isPDF() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {}

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

    /** @see HSLFSlideShow */
    public static Matcher<InputStream> isPPT() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {}

            @Override
            protected boolean matchesSafely(InputStream is) {
                try {
                    new HSLFSlideShow(is);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        };
    }

    /** @see XMLSlideShow */
    public static Matcher<InputStream> isPPTX() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {}

            @Override
            protected boolean matchesSafely(InputStream is) {
                try {
                    new XMLSlideShow(is);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        };
    }

    /** @see RTFEditorKit */
    public static Matcher<InputStream> isRTF() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {}

            @Override
            protected boolean matchesSafely(InputStream is) {
                RTFEditorKit rtfParser = new RTFEditorKit();
                Document document = rtfParser.createDefaultDocument();
                try {
                    rtfParser.read(is, document, 0);
                    return true;
                } catch (IOException | BadLocationException | NumberFormatException e) {
                    return false;
                }
            }
        };
    }

    /** @see Character#isISOControl(char) */
    public static Matcher<InputStream> isTXT() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {}

            @Override
            protected boolean matchesSafely(InputStream is) {
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
        };
    }

    /** @see HSSFWorkbook */
    public static Matcher<InputStream> isXLS() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {}

            @Override
            protected boolean matchesSafely(InputStream is) {
                try {
                    new HSSFWorkbook(is);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        };
    }

    /** @see XSSFWorkbook */
    public static Matcher<InputStream> isXLSX() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {}

            @Override
            protected boolean matchesSafely(InputStream is) {
                try {
                    new XSSFWorkbook(is);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        };
    }

    /** @see DocumentBuilder */
    public static Matcher<InputStream> isXML() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {}

            @Override
            protected boolean matchesSafely(InputStream is) {
                try {
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    builder.parse(is);
                    return true;
                } catch (ParserConfigurationException | SAXException | IOException e) {
                    return false;
                }
            }
        };
    }

    /** @see ZipInputStream */
    public static Matcher<InputStream> isZIP() {
        return new TypeSafeMatcher<InputStream>() {
            @Override
            public void describeTo(Description description) {}

            @Override
            protected boolean matchesSafely(InputStream is) {
                new ZipInputStream(is);
                return true;
            }
        };
    }

    // Util
    private FileFormatMatcher() {}

}