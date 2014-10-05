package fr.pinguet62.dictionary.gui;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

import fr.pinguet62.dictionary.model.QUser;
import fr.pinguet62.dictionary.model.User;
import fr.pinguet62.dictionary.service.UserService;

@Named
@ViewScoped
public final class UsersManagedBean {

    private LazyDataModel<User> lazyDataModel;

    @Inject
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

}
