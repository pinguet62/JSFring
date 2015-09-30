package fr.pinguet62.jsfring.gui.jasperreport.sample;

import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.PDF;
import static java.util.stream.Collectors.toList;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    private Date dateParam;

    private Integer integerParam;

    private List<String> listParam;

    private String stringParam;

    public List<String> complete(String query) {
        List<String> values = Arrays.asList("Avion", "Ballon", "Cadeau", "Dauphin", "Eléphant", "Fusée", "Gateau", "Hibou",
                "Ile", "Jouet", "Kangourou", "Lune", "Maman", "Nuage", "Orange", "Papa", "Quille", "Roue", "Soleil", "Tortue",
                "Uniforme", "Vélo", "Wagon", "Xylophono", "Yoyo", "Zèbre");
        return values.stream().map(String::toLowerCase).filter(it -> it.contains(query.toLowerCase())).collect(toList());
    }

    public Date getDateParam() {
        return dateParam;
    }

    public Integer getIntegerParam() {
        return integerParam;
    }

    public List<String> getListParam() {
        return listParam;
    }

    @Override
    protected Map<String, Object> getParameters() {
        Map<String, Object> params = new HashMap<>();
        params.put("stringParam", stringParam);
        params.put("integerParam", integerParam);
        params.put("dateParam", dateParam);
        params.put("listParam", listParam);
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

    public void setDateParam(Date dateParam) {
        this.dateParam = dateParam;
    }

    public void setIntegerParam(Integer integerParam) {
        this.integerParam = integerParam;
    }

    public void setListParam(List<String> listParam) {
        this.listParam = listParam;
    }

    public void setStringParam(String stringParam) {
        this.stringParam = stringParam;
    }

}