package fr.pinguet62.jsfring.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

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
public final class UsersManagedBean extends AbstractManagedBean<User> {

    private static final long serialVersionUID = 1;

    /**
     * The {@link Profile} association (available/associated) of the
     * {@link #selectedUser}.
     *
     * @property.getter {@link #getProfilesAssociation()}
     * @property.setter {@link #setProfilesAssociation(DualListModel)}
     */
    private DualListModel<Profile> profilesAssociation;

    /** @inject.setter {@link #setProfileService(ProfileService)} */
    @ManagedProperty("#{profileService}")
    private transient ProfileService profileService;

    /**
     * The selected {@link User} to display or update.
     *
     * @property.getter {@link #getSelectedUser()}
     * @property.setter {@link #setSelectedUser(User)}
     */
    private User selectedUser;

    /** @inject.setter {@link #setUserService(UserService)} */
    @ManagedProperty("#{userService}")
    private transient UserService userService;

    /** @property.attribute {@link #profilesAssociation} */
    public DualListModel<Profile> getProfilesAssociation() {
        return profilesAssociation;
    }

    @Override
    protected JPAQuery getQuery() {
        return new JPAQuery().from(QUser.user);
    }

    /** @property.attribute {@link #selectedUser} */
    public User getSelectedUser() {
        return selectedUser;
    }

    @Override
    public AbstractService<User, ?> getService() {
        return userService;
    }

    /**
     * Call when the user click on "Submit" button into "Edit" dialog.
     * <p>
     * Save the modified {@link User}.
     */
    public void save() {
        selectedUser.getProfiles().clear();
        selectedUser.getProfiles().addAll(profilesAssociation.getTarget());

        userService.update(selectedUser);
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "User updated",
                        selectedUser.getLogin()));
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
     * @property.attribute {@link #selectedUser}
     */
    public void setSelectedUser(User user) {
        selectedUser = user;

        List<Profile> associatedProfiles = new ArrayList<>(
                selectedUser.getProfiles());
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

}
