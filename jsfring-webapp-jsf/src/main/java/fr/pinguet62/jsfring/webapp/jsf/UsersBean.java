package fr.pinguet62.jsfring.webapp.jsf;

import static java.util.stream.Collectors.toList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DualListModel;

import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.model.sql.User;
import fr.pinguet62.jsfring.service.AbstractService;
import fr.pinguet62.jsfring.service.ProfileService;
import fr.pinguet62.jsfring.service.UserService;
import fr.pinguet62.jsfring.webapp.jsf.config.scope.SpringViewScoped;
import lombok.Getter;
import lombok.Setter;

/** @see User */
@Named
@SpringViewScoped
public final class UsersBean extends AbstractCrudBean<User> {

    private static final long serialVersionUID = 1;

    /** The {@link Profile} association (available/associated) of the {@link #getSelectedValue() selected user}. */
    @Getter
    @Setter
    private DualListModel<Profile> profilesAssociation;

    @Inject
    private transient ProfileService profileService;

    @Inject
    private transient UserService userService;

    /**
     * {@inheritDoc}
     * <p>
     * Initialize the associated {@link Profile}s.
     */
    @Override
    public void create() {
        getSelectedValue().getProfiles().clear();
        getSelectedValue().getProfiles().addAll(profilesAssociation.getTarget());

        super.create();
    }

    @Override
    protected User getNewValue() {
        User newEntity = new User();
        newEntity.setProfiles(new HashSet<>());
        return newEntity;
    }

    @Override
    public AbstractService<User, ? extends Serializable> getService() {
        return userService;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Initialize the associated {@link Profile}s.
     */
    @Override
    public void setSelectedValue(User user) {
        super.setSelectedValue(user);

        // Custom
        List<Profile> associatedProfiles = new ArrayList<>(getSelectedValue().getProfiles());
        List<Profile> availableProfiles = profileService.getAll().stream()
                .filter(profile -> !associatedProfiles.contains(profile)).collect(toList());
        profilesAssociation = new DualListModel<>(availableProfiles, associatedProfiles);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Initialize the associated {@link Profile}s.
     */
    @Override
    public void update() {
        // Profile association
        getSelectedValue().getProfiles().clear();
        getSelectedValue().getProfiles().addAll(profilesAssociation.getTarget());

        super.update();
    }

}