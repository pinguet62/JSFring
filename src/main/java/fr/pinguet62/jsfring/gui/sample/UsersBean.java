package fr.pinguet62.jsfring.gui.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DualListModel;

import com.mysema.query.types.path.EntityPathBase;

import fr.pinguet62.jsfring.gui.AbstractCrudBean;
import fr.pinguet62.jsfring.model.Profile;
import fr.pinguet62.jsfring.model.QUser;
import fr.pinguet62.jsfring.model.User;
import fr.pinguet62.jsfring.service.AbstractService;
import fr.pinguet62.jsfring.service.ProfileService;
import fr.pinguet62.jsfring.service.UserService;
import fr.pinguet62.jsfring.util.cdi.scope.SpringViewScoped;

/** @see User */
@Named
@SpringViewScoped
public final class UsersBean extends AbstractCrudBean<User> {

    private static final long serialVersionUID = 1;

    /**
     * The {@link Profile} association (available/associated) of the
     * {@link #getSelectedValue() selected user}.
     *
     * @property.getter {@link #getProfilesAssociation()}
     * @property.setter {@link #setProfilesAssociation(DualListModel)}
     */
    private DualListModel<Profile> profilesAssociation;

    @Inject
    private transient ProfileService profileService;

    @Inject
    private transient UserService userService;

    /**
     * {@inheritDoc}
     * <p>
     * Call when the user click on "Submit" button into "Create" dialog.
     */
    @Override
    public void create() {
        // Profile association
        getSelectedValue().getProfiles().clear();
        getSelectedValue().getProfiles()
                .addAll(profilesAssociation.getTarget());

        super.create();
    }

    /** @return {@link QUser#user} */
    @Override
    protected EntityPathBase<User> getBaseExpression() {
        return QUser.user;
    }

    @Override
    protected User getNewValue() {
        return new User();
    }

    /** @property.attribute {@link #profilesAssociation} */
    public DualListModel<Profile> getProfilesAssociation() {
        return profilesAssociation;
    }

    @Override
    public AbstractService<User, ?> getService() {
        return userService;
    }

    /** @property.attribute {@link #profilesAssociation} */
    public void setProfilesAssociation(
            DualListModel<Profile> profilesAssociation) {
        this.profilesAssociation = profilesAssociation;
    }

    /**
     * Call when the user click on "Show" or "Edit" button.
     * <ul>
     * <li>Set the selected {@link User};</li>
     * <li>Initialize the {@link Profile} association.</li>
     * </ul>
     *
     * @param user The selected {@link User}.
     */
    @Override
    public void setSelectedValue(User user) {
        super.setSelectedValue(user);

        // Custom
        List<Profile> associatedProfiles = new ArrayList<>(getSelectedValue()
                .getProfiles());
        List<Profile> availableProfiles = profileService.getAll().stream()
                .filter(profile -> !associatedProfiles.contains(profile))
                .collect(Collectors.toList());
        profilesAssociation = new DualListModel<>(availableProfiles,
                associatedProfiles);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Call when the user click on "Submit" button into "Edit" dialog.
     */
    @Override
    public void update() {
        // Profile association
        getSelectedValue().getProfiles().clear();
        getSelectedValue().getProfiles()
                .addAll(profilesAssociation.getTarget());

        super.update();
    }

}