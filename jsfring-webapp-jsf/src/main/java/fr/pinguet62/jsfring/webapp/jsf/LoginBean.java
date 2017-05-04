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

/** Bean for user login. */
@Named
@SpringRequestScoped
public final class LoginBean implements Serializable {

    private static final long serialVersionUID = 1;

    /** Contains the error message returned by Spring Security in the URL. */
    // TODO CDI
    @Value("#{request.getParameter('error')}")
    private String error;

    private String password;

    private boolean rememberMe;

    private String username;

    public String getError() {
        return error;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    /**
     * If the URL is the redirect of Spring authentication fail: show a message.
     */
    @PostConstruct
    public void init() {
        if (error != null)
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR, "Username or password invalid.", null));
    }

    public boolean isRememberMe() {
        return rememberMe;
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

    public void setError(String error) {
        this.error = error;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}