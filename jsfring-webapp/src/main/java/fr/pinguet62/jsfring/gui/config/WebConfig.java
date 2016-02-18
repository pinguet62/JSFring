package fr.pinguet62.jsfring.gui.config;

import static com.sun.faces.config.WebConfiguration.BooleanWebContextInitParameter.ForceLoadFacesConfigFiles;
import static java.lang.Boolean.TRUE;
import static javax.faces.application.ViewHandler.DEFAULT_FACELETS_SUFFIX;
import static javax.faces.application.ViewHandler.FACELETS_SKIP_COMMENTS_PARAM_NAME;
import static javax.faces.convert.Converter.DATETIMECONVERTER_DEFAULT_TIMEZONE_IS_SYSTEM_TIMEZONE_PARAM_NAME;
import static org.primefaces.util.Constants.ContextParams.THEME;

import javax.faces.context.FacesContextFactory;
import javax.faces.webapp.FacesServlet;
import javax.inject.Inject;

import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.sun.faces.config.ConfigureListener;

import fr.pinguet62.jsfring.gui.theme.ThemeSwitcherBean;

@Configuration
public class WebConfig {

    /**
     * Set the index page.
     * <p>
     * <code>
     * &lt;welcome-file-list&gt;<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;&lt;welcome-file&gt;index.jsp&lt;/welcome-file&gt;<br>
     * &lt;/welcome-file-list&gt;
     * </code>
     */
    @Configuration
    public static class IndexConfig extends WebMvcConfigurerAdapter {
        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/").setViewName("index.xhtml");
        }
    }

    @Inject
    private ThemeSwitcherBean themeSwitcherBean;

    /** JSF registration: {@link Servlet} & page extension. */
    @Bean
    public ServletRegistrationBean facesServletRegistration() {
        FacesServlet servlet = new FacesServlet();
        String extension = "*" + DEFAULT_FACELETS_SUFFIX;
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
            servletContext.setInitParameter(FACELETS_SKIP_COMMENTS_PARAM_NAME, TRUE.toString());
            // TimeZone: getDefault() instead of "GMT"
            servletContext.setInitParameter(DATETIMECONVERTER_DEFAULT_TIMEZONE_IS_SYSTEM_TIMEZONE_PARAM_NAME, TRUE.toString());

            // JSF without "web.xml" and "faces-config.xml"
            servletContext.setInitParameter(ForceLoadFacesConfigFiles.getQualifiedName(), TRUE.toString());

            // Primefaces
            servletContext.setInitParameter(THEME, themeSwitcherBean.getTheme().getKey());
        };
    }

}