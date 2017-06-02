package fr.pinguet62.jsfring.webapp.jsf;

import static fr.pinguet62.jsfring.webapp.jsf.config.WebSecurityConfig.LOGIN_PROCESSING_URL;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.context.FacesContext.getCurrentInstance;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Value;

import fr.pinguet62.jsfring.webapp.jsf.config.scope.SpringRequestScoped;
import lombok.Getter;
import lombok.Setter;

/** Bean for user login. */
@Named
@SpringRequestScoped
public final class LoginBean implements Serializable {

    private static final long serialVersionUID = 1;

    /** Contains the error message returned by Spring Security in the URL. */
    @Getter
    @Setter
    @Value("#{request.getParameter('error')}")
    private String error;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private boolean rememberMe;

    @Getter
    @Setter
    private String username;

    /**
     * If the URL is the redirect of Spring authentication fail: show a message.
     */
    @PostConstruct
    public void init() {
        if (error != null)
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR, "Username or password invalid.", null));
    }

    /**
     * Redirect the request to the default Spring-Security {@code login-processing-url}.<br>
     * Spring-Security will manage the authentication.
     *
     * @throws ServletException
     *             Error with {@link RequestDispatcher#forward(ServletRequest, ServletResponse) forward}.
     * @throws IOException
     *             Error with {@link RequestDispatcher#forward(ServletRequest, ServletResponse) forward}.
     */
    public void login() throws ServletException, IOException {
        ExternalContext context = getCurrentInstance().getExternalContext();
        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher(LOGIN_PROCESSING_URL);
        dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());
        getCurrentInstance().responseComplete();
    }

}