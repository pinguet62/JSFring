package fr.pinguet62.jsfring.gui.jasperreport.sample;

import javax.inject.Named;

import fr.pinguet62.jsfring.gui.jasperreport.AbstractJasperReportBean;
import fr.pinguet62.jsfring.util.cdi.scope.SpringViewScoped;

@Named
@SpringViewScoped
public final class UsersRightsJasperReportBean extends AbstractJasperReportBean {

    private static final long serialVersionUID = 1;

    @Override
    protected String getReportPath() {
        return "/report/usersRights.jrxml";
    }

}