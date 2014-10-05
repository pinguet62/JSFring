package fr.pinguet62.dictionary.gui;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/** {@link ManagedBean} for user login. */
@Named
@RequestScoped
public final class LoginManagedBean implements Serializable {

    /** Serial version UID. */
    private static final long serialVersionUID = 1;

    @ManagedProperty("#{param.error}")
    private String error;

    /** The password. */
    private String password;

    /** The username. */
    private String username;

    public String getError() {
        return error;
    }

    /**
     * Get the password
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get the username.
     *
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * If the URL is the redirect of Spring authentication fail: show a message.
     */
    @PostConstruct
    public void init() {
        if (error != null)
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Username or password invalid.", null));
    }

    /** Redirect the request to the default Spring {@code login-processing-url}. */
    public void login() throws ServletException, IOException {
        ExternalContext context = FacesContext.getCurrentInstance()
                .getExternalContext();
        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
                .getRequestDispatcher("/j_spring_security_check");
        dispatcher.forward((ServletRequest) context.getRequest(),
                (ServletResponse) context.getResponse());
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void setError(String value) {
        error = value;
    }

    /**
     * Set the password.
     *
     * @param password
     *            The password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set the username.
     *
     * @param username
     *            The username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

}
