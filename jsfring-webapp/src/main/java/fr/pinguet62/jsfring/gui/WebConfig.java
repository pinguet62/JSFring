package fr.pinguet62.jsfring.gui;

import javax.faces.application.ProjectStage;
import javax.faces.application.ViewHandler;
import javax.faces.context.FacesContextFactory;
import javax.faces.convert.Converter;
import javax.faces.webapp.FacesServlet;
import javax.inject.Inject;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.primefaces.util.Constants.ContextParams;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sun.faces.config.ConfigureListener;

import fr.pinguet62.jsfring.gui.theme.ThemeSwitcherBean;

/** Java configuration for {@code web.xml} file. */
@Configuration
public class WebConfig implements ServletContextInitializer {

    @Inject
    private ThemeSwitcherBean themeSwitcherBean;

    @Bean
    public FacesServlet facesServlet() {
        return new FacesServlet();
    }

    /**
     * Configure {@link FacesContextFactory} for Spring-Boot.
     *
     * @see FacesContextFactory
     */
    @Bean
    public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
        return new ServletListenerRegistrationBean<ConfigureListener>(new ConfigureListener());
    }

    /** Configure {@code <context-param>} key/values. */
    @Override
    public void onStartup(ServletContext sc) throws ServletException {
        sc.setInitParameter(ProjectStage.PROJECT_STAGE_PARAM_NAME, ProjectStage.Development.toString());
        sc.setInitParameter(ViewHandler.FACELETS_SKIP_COMMENTS_PARAM_NAME, Boolean.TRUE.toString());
        // TimeZone: getDefault() instead of "GMT"
        sc.setInitParameter(Converter.DATETIMECONVERTER_DEFAULT_TIMEZONE_IS_SYSTEM_TIMEZONE_PARAM_NAME, Boolean.TRUE.toString());

        // JSF without "web.xml" and "faces-config.xml"
        sc.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());

        // Primefaces
        sc.setInitParameter(ContextParams.THEME, themeSwitcherBean.getTheme().getKey());
    }

    /** JSF registration: {@link Servlet} & page extension. */
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        FacesServlet servlet = new FacesServlet();
        String extension = "*" + ViewHandler.DEFAULT_FACELETS_SUFFIX;
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(servlet, extension);
        servletRegistrationBean.setLoadOnStartup(1);
        return servletRegistrationBean;
    }

}