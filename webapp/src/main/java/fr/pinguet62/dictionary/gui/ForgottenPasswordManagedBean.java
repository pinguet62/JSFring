package fr.pinguet62.dictionary.gui;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.pinguet62.dictionary.model.User;
import fr.pinguet62.dictionary.service.UserService;

/** {@link ManagedBean} for reset the password of an {@link User}. */
@Component
@ManagedBean
@RequestScoped
public final class ForgottenPasswordManagedBean {

    /** The email. */
    private String email;

    @Autowired
    private UserService userService;

    public String getEmail() {
        return email;
    }

    /** Reset the password. */
    public void reset() {
        try {
            userService.forgottenPassword(email);
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Your login information was sent to you by email.",
                            null));
        } catch (IllegalArgumentException exception) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Email unknown.", null));
        }
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
