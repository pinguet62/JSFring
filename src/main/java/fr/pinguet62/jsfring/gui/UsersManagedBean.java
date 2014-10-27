package fr.pinguet62.jsfring.gui;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import fr.pinguet62.jsfring.model.QUser;
import fr.pinguet62.jsfring.model.User;
import fr.pinguet62.jsfring.service.UserService;

@ManagedBean
@ViewScoped
public final class UsersManagedBean {

    private LazyDataModel<User> lazyDataModel;

    @ManagedProperty("#{userService}")
    private UserService userService;

    /**
     * Get the lazy list of {@link User}s to display.
     *
     * @return The {@link UserLazyDataModel} of {@link User}s.
     */
    public LazyDataModel<User> getLazyDataModel() {
        return lazyDataModel;
    }

    @PostConstruct
    private void init() {
        lazyDataModel = new QuerydslLazyDataModel<User>(userService, QUser.user);
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
