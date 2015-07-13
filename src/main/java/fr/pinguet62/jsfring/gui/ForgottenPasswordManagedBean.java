package fr.pinguet62.jsfring.gui;

import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.MessageSource;

import fr.pinguet62.jsfring.model.User;
import fr.pinguet62.jsfring.service.UserService;
import fr.pinguet62.jsfring.util.cdi.SpringViewScoped;

/** {@link ManagedBean} for reset the password of an {@link User}. */
@Named
@SpringViewScoped
public final class ForgottenPasswordManagedBean {

    /**
     * The email.
     *
     * @property.getter {@link #getEmail()}
     * @property.setter {@link #setEmail(String)}
     */
    private String email;

    @Inject
    private MessageSource messageSource;

    @Inject
    private UserService userService;

    /** @property.attribute {@link #email} */
    public String getEmail() {
        return email;
    }

    /** Reset the password. */
    public void reset() {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot()
                .getLocale();

        try {
            userService.forgottenPassword(email);
            String message = messageSource
                    .getMessage("forgottenPassword.messages.informationsSent",
                            null, locale);
            FacesContext.getCurrentInstance()
                    .addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    message, null));
        } catch (IllegalArgumentException exception) {
            String message = messageSource.getMessage(
                    "forgottenPassword.messages.emailUnknown", null, locale);
            FacesContext.getCurrentInstance()
                    .addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    message, null));
        }
    }

    /** @property.attribute {@link #email} */
    public void setEmail(String email) {
        this.email = email;
    }

}