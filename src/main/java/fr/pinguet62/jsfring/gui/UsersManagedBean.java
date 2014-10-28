package fr.pinguet62.jsfring.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;

import fr.pinguet62.jsfring.model.Profile;
import fr.pinguet62.jsfring.model.QUser;
import fr.pinguet62.jsfring.model.User;
import fr.pinguet62.jsfring.service.ProfileService;
import fr.pinguet62.jsfring.service.UserService;

@ManagedBean
@ViewScoped
public final class UsersManagedBean {

    private LazyDataModel<User> lazyDataModel;

    /**
     * The {@link Profile} association (available/associated) of the selected
     * {@link User}.
     */
    private DualListModel<Profile> profilesAssociation;

    @ManagedProperty("#{profileService}")
    private ProfileService profileService;

    /** The {@link User} to display or update. */
    private User selectedUser;

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

    public DualListModel<Profile> getProfilesAssociation() {
        return profilesAssociation;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    @PostConstruct
    private void init() {
        lazyDataModel = new QuerydslLazyDataModel<User>(userService, QUser.user);
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

    public void setProfilesAssociation(
            DualListModel<Profile> profilesAssociation) {
        this.profilesAssociation = profilesAssociation;
    }

    // Inject
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
     * @param profile
     *            The selected {@link User}.
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

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
