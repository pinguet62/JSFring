package fr.pinguet62.jsfring.gui;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.MessageSource;

import fr.pinguet62.jsfring.gui.config.scope.SpringViewScoped;
import fr.pinguet62.jsfring.model.User;
import fr.pinguet62.jsfring.service.UserService;

/** Page to reset its {@link User#password} after an oversight. */
@Named
@SpringViewScoped
public final class ForgottenPasswordBean implements Serializable {

    private static final long serialVersionUID = 1;

    /** The {@link User#email user's email}. */
    private String email;

    @Inject
    private transient MessageSource messageSource;

    @Inject
    private transient UserService userService;

    public String getEmail() {
        return email;
    }

    /**
     * Reset the password.
     *
     * @see UserService#forgottenPassword(String)
     */
    public void reset() {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

        try {
            userService.forgottenPassword(email);
            String message = messageSource.getMessage("forgottenPassword.messages.informationsSent", null, locale);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
        } catch (IllegalArgumentException exception) {
            String message = messageSource.getMessage("forgottenPassword.messages.emailUnknown", null, locale);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        }
    }

    public void setEmail(String email) {
        this.email = email;
    }

}