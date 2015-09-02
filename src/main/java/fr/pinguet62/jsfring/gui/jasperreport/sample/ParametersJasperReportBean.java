package fr.pinguet62.jsfring.gui.jasperreport.sample;

import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.PDF;

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
public final class ParametersJasperReportBean extends AbstractJasperReportBean {

    private static final long serialVersionUID = 1;

    private Integer integerParam;

    private String stringParam;

    public Integer getIntegerParam() {
        return integerParam;
    }

    @Override
    protected Map<String, Object> getParameters() {
        Map<String, Object> params = new HashMap<>();
        params.put("integerParam", integerParam);
        return params;
    }

    /**
     * @see #getStreamedContent(ExportType)
     * @see ExportType#PDF
     */
    public StreamedContent getPdfFile() throws JRException, SQLException {
        return getStreamedContent(PDF);
    }

    @Override
    protected String getReportPath() {
        return "/report/parameters.jrxml";
    }

    public String getStringParam() {
        return stringParam;
    }

    public void setIntegerParam(Integer integerParam) {
        this.integerParam = integerParam;
    }

    public void setStringParam(String stringParam) {
        this.stringParam = stringParam;
    }

}