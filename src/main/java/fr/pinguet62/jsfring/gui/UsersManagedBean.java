package fr.pinguet62.jsfring.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DualListModel;

import com.mysema.query.jpa.impl.JPAQuery;

import fr.pinguet62.jsfring.model.Profile;
import fr.pinguet62.jsfring.model.QUser;
import fr.pinguet62.jsfring.model.User;
import fr.pinguet62.jsfring.service.AbstractService;
import fr.pinguet62.jsfring.service.ProfileService;
import fr.pinguet62.jsfring.service.UserService;

/** @see User */
@ManagedBean
@ViewScoped
public final class UsersManagedBean extends AbstractCrudManagedBean<User> {

    private static final long serialVersionUID = 1;

    /**
     * The {@link Profile} association (available/associated) of the
     * {@link #getSelectedValue() selected user}.
     *
     * @property.getter {@link #getProfilesAssociation()}
     * @property.setter {@link #setProfilesAssociation(DualListModel)}
     */
    private DualListModel<Profile> profilesAssociation;

    /** @inject.setter {@link #setProfileService(ProfileService)} */
    @ManagedProperty("#{profileService}")
    private transient ProfileService profileService;

    /** @inject.setter {@link #setUserService(UserService)} */
    @ManagedProperty("#{userService}")
    private transient UserService userService;

    @Override
    protected User getNewValue() {
        return new User();
    }

    /** @property.attribute {@link #profilesAssociation} */
    public DualListModel<Profile> getProfilesAssociation() {
        return profilesAssociation;
    }

    @Override
    protected JPAQuery getQuery() {
        return new JPAQuery().from(QUser.user);
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

    /** @inject.attribute {@link #profileService} */
    public void setProfileService(ProfileService profileService) {
        this.profileService = profileService;
    }

    /**
     * Call when the user click on "Show" or "Edit" button.
     * <ul>
     * <li>Set the selected {@link User};</li>
     * <li>Initialize the {@link Profile} association.</li>
     * </ul>
     *
     * @param user
     *            The selected {@link User}.
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

    /** @inject.attribute {@link #userService} */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Call when the user click on "Submit" button into "Edit" dialog.
     * <p>
     * Save the modified {@link User}.
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
