package fr.pinguet62.jsfring.gui.config;

import javax.faces.application.ViewHandler;
import javax.faces.context.FacesContextFactory;
import javax.faces.convert.Converter;
import javax.faces.webapp.FacesServlet;
import javax.inject.Inject;

import org.primefaces.util.Constants.ContextParams;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sun.faces.config.ConfigureListener;

import fr.pinguet62.jsfring.gui.theme.ThemeSwitcherBean;

@Configuration
public class WebConfig {

    @Inject
    private ThemeSwitcherBean themeSwitcherBean;

    /** JSF registration: {@link Servlet} & page extension. */
    @Bean
    public ServletRegistrationBean facesServletRegistration() {
        FacesServlet servlet = new FacesServlet();
        String extension = "*" + ViewHandler.DEFAULT_FACELETS_SUFFIX;
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(servlet, extension);
        servletRegistrationBean.setLoadOnStartup(1);
        return servletRegistrationBean;
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
    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return servletContext -> {
            // servletContext.setInitParameter(ProjectStage.PROJECT_STAGE_PARAM_NAME,
            // ProjectStage.Development.toString());
            servletContext.setInitParameter(ViewHandler.FACELETS_SKIP_COMMENTS_PARAM_NAME, Boolean.TRUE.toString());
            // TimeZone: getDefault() instead of "GMT"
            servletContext.setInitParameter(Converter.DATETIMECONVERTER_DEFAULT_TIMEZONE_IS_SYSTEM_TIMEZONE_PARAM_NAME,
                    Boolean.TRUE.toString());

            // JSF without "web.xml" and "faces-config.xml"
            servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());

            // Primefaces
            servletContext.setInitParameter(ContextParams.THEME, themeSwitcherBean.getTheme().getKey());
        };
    }

}