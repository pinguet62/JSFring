package fr.pinguet62.dictionary.gui;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.pinguet62.dictionary.service.LanguageService;

/** {@link ManagedBean} for user login. */
@Component
@ManagedBean
@ViewScoped
public final class LoginManagedBean implements Serializable {

    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The password. */
    private String password;

    @Autowired
    private transient LanguageService service;

    /** The username. */
    private String username;

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
