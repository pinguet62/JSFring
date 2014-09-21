package fr.pinguet62.dictionary.gui;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.pinguet62.dictionary.model.User;
import fr.pinguet62.dictionary.service.UserService;

@Component
@ManagedBean
@ViewScoped
public final class UsersManagedBean {

    private LazyDataModel<User> lazyDataModel;

    @Autowired
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
        lazyDataModel = new AbstractLazyDataModel<User>(userService, User.class);
    }

}
