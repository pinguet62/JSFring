package fr.pinguet62.jsfring.gui.jasperreport.sample;

import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.CSV;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.DOCX;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.ODT;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.PDF;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.PPTX;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.XLSX;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import net.sf.jasperreports.engine.JRException;

import org.primefaces.model.StreamedContent;

import fr.pinguet62.jsfring.gui.jasperreport.AbstractJasperReportBean;
import fr.pinguet62.jsfring.gui.jasperreport.ExportType;
import fr.pinguet62.jsfring.util.cdi.scope.SpringViewScoped;

@Named
@SpringViewScoped
public class UserRightsJasperReportBean extends AbstractJasperReportBean {

    private static final long serialVersionUID = 1;

    private String title;

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
     * @see ExportType#ODT
     */
    public StreamedContent getOdtFile() throws JRException, SQLException {
        return getStreamedContent(ODT);
    }

    @Override
    protected Map<String, Object> getParameters() {
        Map<String, Object> params = new HashMap<>();
        params.put("Title", title);
        return params;
    }

    public String getParamTitle() {
        return title;
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
        return "/report/userRights.jrxml";
    }

    /**
     * @see #getStreamedContent(ExportType)
     * @see ExportType#XLSX
     */
    public StreamedContent getXlsxFile() throws JRException, SQLException {
        return getStreamedContent(XLSX);
    }

    public void setParamTitle(String title) {
        this.title = title;
    }

}