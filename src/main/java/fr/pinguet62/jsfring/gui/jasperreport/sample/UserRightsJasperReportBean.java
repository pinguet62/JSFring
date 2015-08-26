package fr.pinguet62.jsfring.gui.jasperreport.sample;

import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.CSV;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.DOCX;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.ODT;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.PDF;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.PPTX;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.XLSX;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import net.sf.jasperreports.engine.JRException;
import fr.pinguet62.jsfring.gui.jasperreport.AbstractJasperReportBean;
import fr.pinguet62.jsfring.gui.jasperreport.ExportType;
import fr.pinguet62.jsfring.util.cdi.scope.SpringViewScoped;

@Named
@SpringViewScoped
public class UserRightsJasperReportBean extends AbstractJasperReportBean {

    private static final long serialVersionUID = 1;

    /**
     * @see #export(ExportType)
     * @see ExportType#DOCX
     */
    public void exportToCsv() throws JRException, SQLException, IOException {
        export(CSV);
    }

    /**
     * @see #export(ExportType)
     * @see ExportType#DOCX
     */
    public void exportToDocx() throws JRException, SQLException, IOException {
        export(DOCX);
    }

    /**
     * @see #export(ExportType)
     * @see ExportType#ODT
     */
    public void exportToOdt() throws JRException, SQLException, IOException {
        export(ODT);
    }

    /**
     * @see #export(ExportType)
     * @see ExportType#PDF
     */
    public void exportToPdf() throws JRException, SQLException, IOException {
        export(PDF);
    }

    /**
     * @see #export(ExportType)
     * @see ExportType#PPTX
     */
    public void exportToPptx() throws JRException, SQLException, IOException {
        export(PPTX);
    }

    /**
     * @see #export(ExportType)
     * @see ExportType#XLSX
     */
    public void exportToXlsx() throws JRException, SQLException, IOException {
        export(XLSX);
    }

    @Override
    protected Map<String, Object> getParameters() {
        Map<String, Object> params = new HashMap<>();
        params.put("Title", "toto");
        return params;
    }

    @Override
    protected String getReportPath() {
        return "/report/userRights.jrxml";
    }

}