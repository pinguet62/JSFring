package fr.pinguet62.jsfring.webapp.jsf.jasperreport.sample;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import fr.pinguet62.jsfring.webapp.jsf.config.scope.SpringViewScoped;
import fr.pinguet62.jsfring.webapp.jsf.jasperreport.AbstractJasperReportBean;
import lombok.Getter;
import lombok.Setter;

@Named
@SpringViewScoped
public final class ParametersJasperReportBean extends AbstractJasperReportBean {

    private static final long serialVersionUID = 1;

    @Getter
    @Setter
    private LocalDateTime dateParam;

    @Getter
    @Setter
    private Integer integerParam;

    @Getter
    @Setter
    private List<String> listParam;

    @Getter
    @Setter
    private String stringParam;

    public List<String> complete(String query) {
        List<String> values = asList("Avion", "Ballon", "Cadeau", "Dauphin", "Elephant", "Fusee", "Gateau", "Hibou", "Ile",
                "Jouet", "Kangourou", "Lune", "Maman", "Nuage", "Orange", "Papa", "Quille", "Roue", "Soleil", "Tortue",
                "Uniforme", "Velo", "Wagon", "Xylophono", "Yoyo", "Zebre");
        return values.stream().map(String::toLowerCase).filter(it -> it.contains(query.toLowerCase())).collect(toList());
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

}