package fr.pinguet62.jsfring.gui;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import fr.pinguet62.jsfring.model.User;

@ManagedBean
@SessionScoped
public final class UserManagedBean implements Serializable {

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
        return UserManagedBean.get();
    }

    public String getEmail() {
        return "mylogin";
    }

}
