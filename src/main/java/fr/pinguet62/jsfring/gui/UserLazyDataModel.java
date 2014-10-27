package fr.pinguet62.jsfring.gui;

import javax.faces.model.DataModel;

import fr.pinguet62.jsfring.model.User;
import fr.pinguet62.jsfring.service.UserService;

/** Lazy {@link DataModel} for {@link User}. */
public final class UserLazyDataModel extends QuerydslLazyDataModel<User> {

    /** Serial version UID. */
    private static final long serialVersionUID = 1;

    /**
     * Constructor.
     *
     * @param service
     *            The {@link UserService}.
     */
    public UserLazyDataModel(UserService service) {
        super(service);
    }

}
