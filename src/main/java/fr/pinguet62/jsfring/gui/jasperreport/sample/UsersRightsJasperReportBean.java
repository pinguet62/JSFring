package fr.pinguet62.jsfring.gui.jasperreport.sample;

import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.CSV;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.DOCX;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.GRAPHICS_2D;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.HTML;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.ODS;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.ODT;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.PDF;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.PPTX;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.RTF;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.TEXT;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.XLS;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.XLSX;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.XML;

import java.sql.SQLException;

import javax.inject.Named;

import net.sf.jasperreports.engine.JRException;

import org.primefaces.model.StreamedContent;

import fr.pinguet62.jsfring.gui.jasperreport.AbstractJasperReportBean;
import fr.pinguet62.jsfring.gui.jasperreport.ExportType;
import fr.pinguet62.jsfring.util.cdi.scope.SpringViewScoped;

@Named
@SpringViewScoped
public final class UsersRightsJasperReportBean extends AbstractJasperReportBean {

    private static final long serialVersionUID = 1;

    /**
     * @see #getStreamedContent(ExportType)
     * @see ExportType#CSV
     */
    public StreamedContent getCsvFile() throws JRException, SQLException {
        return getStreamedContent(CSV);
    }

    /**
     * @see #getStreamedContent(ExportType)
     * @see ExportType#DOCX
     */
    public StreamedContent getDocxFile() throws JRException, SQLException {
        return getStreamedContent(DOCX);
    }

    /**
     * @see #getStreamedContent(ExportType)
     * @see ExportType#GRAPHICS_2D
     */
    public StreamedContent getGraphics2dFile() throws JRException, SQLException {
        return getStreamedContent(GRAPHICS_2D);
    }

    /**
     * @see #getStreamedContent(ExportType)
     * @see ExportType#HTML
     */
    public StreamedContent getHtmlFile() throws JRException, SQLException {
        return getStreamedContent(HTML);
    }

    /**
     * @see #getStreamedContent(ExportType)
     * @see ExportType#ODS
     */
    public StreamedContent getOdsFile() throws JRException, SQLException {
        return getStreamedContent(ODS);
    }

    /**
     * @see #getStreamedContent(ExportType)
     * @see ExportType#ODT
     */
    public StreamedContent getOdtFile() throws JRException, SQLException {
        return getStreamedContent(ODT);
    }

    /**
     * @see #getStreamedContent(ExportType)
     * @see ExportType#PDF
     */
    public StreamedContent getPdfFile() throws JRException, SQLException {
        return getStreamedContent(PDF);
    }

    /**
     * @see #getStreamedContent(ExportType)
     * @see ExportType#PPTX
     */
    public StreamedContent getPptxFile() throws JRException, SQLException {
        return getStreamedContent(PPTX);
    }

    @Override
    protected String getReportPath() {
        return "/report/usersRights.jrxml";
    }

    /**
     * @see #getStreamedContent(ExportType)
     * @see ExportType#RTF
     */
    public StreamedContent getRtfFile() throws JRException, SQLException {
        return getStreamedContent(RTF);
    }

    /**
     * @see #getStreamedContent(ExportType)
     * @see ExportType#TEXT
     */
    public StreamedContent getTextFile() throws JRException, SQLException {
        return getStreamedContent(TEXT);
    }

    /**
     * @see #getStreamedContent(ExportType)
     * @see ExportType#XLSX
     */
    public StreamedContent getXlsFile() throws JRException, SQLException {
        return getStreamedContent(XLS);
    }

    /**
     * @see #getStreamedContent(ExportType)
     * @see ExportType#XLSX
     */
    public StreamedContent getXlsxFile() throws JRException, SQLException {
        return getStreamedContent(XLSX);
    }

    /**
     * @see #getStreamedContent(ExportType)
     * @see ExportType#XML
     */
    public StreamedContent getXmlFile() throws JRException, SQLException {
        return getStreamedContent(XML);
    }

}