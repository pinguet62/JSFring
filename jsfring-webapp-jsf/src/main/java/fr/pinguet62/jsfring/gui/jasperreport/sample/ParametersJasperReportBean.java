package fr.pinguet62.jsfring.gui.jasperreport.sample;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import fr.pinguet62.jsfring.gui.config.scope.SpringViewScoped;
import fr.pinguet62.jsfring.gui.jasperreport.AbstractJasperReportBean;

@Named
@SpringViewScoped
public final class ParametersJasperReportBean extends AbstractJasperReportBean {

    private static final long serialVersionUID = 1;

    private Date dateParam;

    private Integer integerParam;

    private List<String> listParam;

    private String stringParam;

    public List<String> complete(String query) {
        List<String> values = asList("Avion", "Ballon", "Cadeau", "Dauphin", "Elephant", "Fusee", "Gateau", "Hibou", "Ile",
                "Jouet", "Kangourou", "Lune", "Maman", "Nuage", "Orange", "Papa", "Quille", "Roue", "Soleil", "Tortue",
                "Uniforme", "Velo", "Wagon", "Xylophono", "Yoyo", "Zebre");
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