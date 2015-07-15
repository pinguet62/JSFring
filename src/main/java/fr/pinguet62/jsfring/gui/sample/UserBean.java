package fr.pinguet62.jsfring.gui.sample;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import fr.pinguet62.jsfring.model.User;

@Named
@SessionScoped
public final class UserBean implements Serializable {

    private static final long serialVersionUID = 1;

    /**
     * Get the user in session.
     *
     * @return The session user.
     * @see SecurityContextHolder
     */
    public static User get() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication == null || !authentication.isAuthenticated())
            return null;
        else
            return (User) SecurityContextHolder.getContext()
                    .getAuthentication().getDetails();
    }

    /**  */
    public User getDao() {
        return UserBean.get();
    }

    public String getEmail() {
        return "mylogin";
    }

}
