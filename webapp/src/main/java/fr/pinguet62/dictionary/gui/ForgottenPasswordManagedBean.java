package fr.pinguet62.dictionary.gui;

import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;

import fr.pinguet62.dictionary.model.User;
import fr.pinguet62.dictionary.service.UserService;

/** {@link ManagedBean} for reset the password of an {@link User}. */
@Named
@Scope("request")
// TODO ViewScoped
public final class ForgottenPasswordManagedBean {

    /** The email. */
    private String email;

    @Inject
    private MessageSource messageSource;

    @Inject
    private UserService userService;

    public ForgottenPasswordManagedBean() {
        System.out.println("ForgottenPasswordManagedBean");
    }

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

    public void setEmail(String email) {
        this.email = email;
    }

}
