package fr.pinguet62.jsfring.gui;

import javax.faces.application.ViewHandler;
import javax.faces.webapp.FacesServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;

import com.sun.faces.config.ConfigureListener;

//@SpringBootApplication(scanBasePackages = "fr.pinguet62.jsfring")
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = { "", "fr.pinguet62.jsfring" })
public class Application extends SpringBootServletInitializer implements ServletContextAware {

    public static class Toto extends SpringBootServletInitializer {
        @Override
        protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
            return application.sources(Application.class, WebConfig.class);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class, WebConfig.class);
    }

    /** JSF registration: {@link Servlet} & page extension. */
    @Bean
    public ServletRegistrationBean facesServletRegistration() {
        FacesServlet servlet = new FacesServlet();
        // TODO check requiered "*"
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(servlet, "*"
                + ViewHandler.DEFAULT_FACELETS_SUFFIX);
        servletRegistrationBean.setLoadOnStartup(1);
        return servletRegistrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
        return new ServletListenerRegistrationBean<ConfigureListener>(new ConfigureListener());
    }

    /** JSF registration: {@link Servlet} & page extension. */
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        FacesServlet servlet = new FacesServlet();
        // TODO check requiered "*"
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(servlet, "*"
                + ViewHandler.DEFAULT_FACELETS_SUFFIX);
        servletRegistrationBean.setLoadOnStartup(1);
        return servletRegistrationBean;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
    }

}