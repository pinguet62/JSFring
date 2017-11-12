package fr.pinguet62.jsfring.webapp.jsf;

import fr.pinguet62.jsfring.model.sql.User;
import fr.pinguet62.jsfring.service.UserService;
import fr.pinguet62.jsfring.webapp.jsf.config.scope.SpringViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Locale;

import static fr.pinguet62.jsfring.common.security.userdetails.UserDetailsUtils.getCurrent;
import static fr.pinguet62.jsfring.model.sql.User.PASSWORD_REGEX;
import static fr.pinguet62.jsfring.model.sql.User.PASSWORD_VALIDATION_MESSAGE;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.context.FacesContext.getCurrentInstance;

/**
 * Page to change its {@link User#password}.
 */
@Component
@SpringViewScoped
public final class ChangePasswordBean implements Serializable {

    private static final long serialVersionUID = 1;

    @Getter
    @Setter
    private String currentPassword;

    @Autowired
    private transient MessageSource messageSource;

    @Getter
    @Setter
    @Pattern(regexp = PASSWORD_REGEX, message = PASSWORD_VALIDATION_MESSAGE)
    private String newPassword;

    @Getter
    @Setter
    private String newPasswordConfirmation;

    @Autowired
    private transient UserService userService;

    /**
     * Display message.
     *
     * @param severity The {@link FacesMessage} severity.
     * @param code     Code of i18n message.
     */
    private void showMessage(Severity severity, String code) {
        Locale locale = getCurrentInstance().getViewRoot().getLocale();
        String msg = messageSource.getMessage(code, null, locale);
        getCurrentInstance().addMessage(null, new FacesMessage(severity, msg, null));
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
        UserDetails currentUserDetails = getCurrent();

        if (!currentPassword.equals(currentUserDetails.getPassword())) {
            showMessage(SEVERITY_ERROR, "changePassword.invalidCurrentPassword");
            return;
        }

        userService.updatePassword(currentUserDetails.getUsername(), newPassword);
        showMessage(SEVERITY_INFO, "changePassword.confirmation");
    }

}