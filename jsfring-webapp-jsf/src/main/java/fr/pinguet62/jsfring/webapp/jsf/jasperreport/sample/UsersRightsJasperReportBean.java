package fr.pinguet62.jsfring.webapp.jsf.jasperreport.sample;

import fr.pinguet62.jsfring.webapp.jsf.config.scope.SpringViewScoped;
import fr.pinguet62.jsfring.webapp.jsf.jasperreport.AbstractJasperReportBean;
import org.springframework.stereotype.Component;

@Component
@SpringViewScoped
public final class UsersRightsJasperReportBean extends AbstractJasperReportBean {

    private static final long serialVersionUID = 1;

    @Override
    protected String getReportPath() {
        return "/report/usersRights.jrxml";
    }

}