package fr.pinguet62.jsfring.gui.jasperreport.sample;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import fr.pinguet62.jsfring.gui.jasperreport.AbstractJasperReportBean;
import fr.pinguet62.jsfring.util.cdi.scope.SpringViewScoped;

@Named
@SpringViewScoped
public class UserRightsJasperReportBean extends AbstractJasperReportBean {

    private static final long serialVersionUID = 1;

    @Override
    protected Map<String, Object> getParameters() {
        return new HashMap<>();
    }

    @Override
    protected String getReportPath() {
        return "/report/userRights.jrxml";
    }

}