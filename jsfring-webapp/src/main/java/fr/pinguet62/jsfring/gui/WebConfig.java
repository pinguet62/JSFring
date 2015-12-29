package fr.pinguet62.jsfring.gui;

import javax.faces.application.ProjectStage;
import javax.faces.application.ViewHandler;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

import fr.pinguet62.jsfring.gui.theme.ThemeSwitcherBean;

/** Java configuration for {@code web.xml} file. */
@Configuration
public class WebConfig implements ServletContextInitializer {

    @Inject
    private ThemeSwitcherBean themeSwitcherBean;

    @Override
    public void onStartup(ServletContext sc) throws ServletException {
        sc.setInitParameter(ProjectStage.PROJECT_STAGE_PARAM_NAME, ProjectStage.Development.toString());
        sc.setInitParameter(ViewHandler.FACELETS_SKIP_COMMENTS_PARAM_NAME, Boolean.TRUE.toString());
        // TimeZone: getDefault() instead of "GMT"
        sc.setInitParameter(Converter.DATETIMECONVERTER_DEFAULT_TIMEZONE_IS_SYSTEM_TIMEZONE_PARAM_NAME, Boolean.TRUE.toString());

        // Primefaces
        sc.setInitParameter("primefaces.THEME", themeSwitcherBean.getTheme().getKey());
    }

}