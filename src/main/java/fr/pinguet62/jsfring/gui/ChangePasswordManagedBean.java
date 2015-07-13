package fr.pinguet62.jsfring.gui;

import javax.inject.Named;

import fr.pinguet62.jsfring.util.cdi.SpringViewScoped;

@Named
@SpringViewScoped
public final class ChangePasswordManagedBean {

    /**
     * @property.getter {@link #getNewPassword()}
     * @property.setter {@link #setNewPassword(String)}
     */
    private String newPassword;

    /**
     * @property.getter {@link #getNewPasswordConfirmation()}
     * @property.setter {@link #setNewPasswordConfirmation(String)}
     */
    private String newPasswordConfirmation;

    /**
     * @property.getter {@link #getOldPassword()}
     * @property.setter {@link #setOldPassword(String)}
     */
    private String oldPassword;

    /** @property.attribute {@link #newPassword} */
    public String getNewPassword() {
        return newPassword;
    }

    /** @property.attribute {@link #newPasswordConfirmation} */
    public String getNewPasswordConfirmation() {
        return newPasswordConfirmation;
    }

    /** @property.attribute {@link #oldPassword} */
    public String getOldPassword() {
        return oldPassword;
    }

    /** @property.attribute {@link #newPassword} */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /** @property.attribute {@link #newPasswordConfirmation} */
    public void setNewPasswordConfirmation(String newPasswordConfirmation) {
        this.newPasswordConfirmation = newPasswordConfirmation;
    }

    /** @property.attribute {@link #oldPassword} */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void submit() {
        System.out.println("TODO");
    }

}