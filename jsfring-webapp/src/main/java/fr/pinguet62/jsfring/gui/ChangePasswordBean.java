package fr.pinguet62.jsfring.gui;

import static fr.pinguet62.jsfring.model.User.EMAIL_VALIDATION_MESSAGE;
import static fr.pinguet62.jsfring.model.User.PASSWORD_REGEX;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Pattern;

import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;

import fr.pinguet62.jsfring.gui.sample.UserBean;
import fr.pinguet62.jsfring.gui.util.scope.SpringViewScoped;
import fr.pinguet62.jsfring.model.User;
import fr.pinguet62.jsfring.service.UserService;

/** Page to change its {@link User#password}. */
@Named
@SpringViewScoped
public final class ChangePasswordBean implements Serializable {

    private static final long serialVersionUID = 1;

    private String currentPassword;

    @Inject
    private transient MessageSource messageSource;

    @Pattern(regexp = PASSWORD_REGEX, message = EMAIL_VALIDATION_MESSAGE)
    private String newPassword;

    private String newPasswordConfirmation;

    @Inject
    private transient UserBean userBean;

    @Inject
    private transient UserService userService;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getNewPasswordConfirmation() {
        return newPasswordConfirmation;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setNewPasswordConfirmation(String newPasswordConfirmation) {
        this.newPasswordConfirmation = newPasswordConfirmation;
    }

    /**
     * <ul>
     * <li>Check the {@link #currentPassword};</li>
     * <li>Update the {@link User#password}.</li>
     * </ul>
     *
     * @see UserService#updatePassword(String, String)
     */
    public void submit() {
        UserDetails userDetails = userBean.get();

        if (!newPassword.equals(userDetails.getPassword())) {
            Locale locale = FacesContext.getCurrentInstance().getViewRoot()
                    .getLocale();
            String msg = messageSource.getMessage(
                    "changePassword.invalidCurrentPassword", null, locale);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        }

        userService.updatePassword(userDetails.getUsername(), newPassword);
    }

}