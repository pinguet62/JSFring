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
import fr.pinguet62.jsfring.model.QProfile;
import fr.pinguet62.jsfring.model.Right;
import fr.pinguet62.jsfring.service.AbstractService;
import fr.pinguet62.jsfring.service.ProfileService;
import fr.pinguet62.jsfring.service.RightService;

/** @see Profile */
@ManagedBean
@ViewScoped
public final class ProfilesManagedBean extends AbstractManagedBean<Profile> {

    private static final long serialVersionUID = 1;

    /** @inject.setter {@link #setProfileService(ProfileService)} */
    @ManagedProperty("#{profileService}")
    private transient ProfileService profileService;

    /**
     * The {@link Right} association (available/associated) of the
     * {@link #selectedProfile}.
     *
     * @property.getter {@link #getRightsAssociation()}
     * @property.setter {@link #setRightsAssociation(DualListModel)}
     */
    private DualListModel<Right> rightsAssociation;

    /** @inject.setter {@link #setRightService(RightService)} */
    @ManagedProperty("#{rightService}")
    private transient RightService rightService;

    /**
     * The {@link Profile} to display or update.
     *
     * @property.getter {@link #getSelectedProfile()}
     * @property.setter {@link #setSelectedProfile(Profile)}
     */
    private Profile selectedProfile;

    @Override
    protected JPAQuery getQuery() {
        return new JPAQuery().from(QProfile.profile);
    }

    /** @property.attribute {@link #rightsAssociation} */
    public DualListModel<Right> getRightsAssociation() {
        return rightsAssociation;
    }

    /** @property.attribute {@link #selectedProfile} */
    public Profile getSelectedProfile() {
        return selectedProfile;
    }

    @Override
    public AbstractService<Profile, ?> getService() {
        return profileService;
    }

    /**
     * Call when the user click on "Submit" button into "Edit" dialog.
     * <p>
     * Save the modified {@link Profile}.
     */
    public void save() {
        selectedProfile.getRights().clear();
        selectedProfile.getRights().addAll(rightsAssociation.getTarget());

        profileService.update(selectedProfile);
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile updated",
                        selectedProfile.getTitle()));
    }

    /** @inject.attribute {@link #profileService} */
    public void setProfileService(ProfileService profileService) {
        this.profileService = profileService;
    }

    /** @property.attribute {@link #rightsAssociation} */
    public void setRightsAssociation(DualListModel<Right> rightsAssociation) {
        this.rightsAssociation = rightsAssociation;
    }

    /** @inject.attribute {@link #rightService} */
    public void setRightService(RightService rightService) {
        this.rightService = rightService;
    }

    // Property
    /**
     * Call when the user click on "Show" or "Edit" button.
     * <ul>
     * <li>Set the selected {@link Profile};</li>
     * <li>Initialize the {@link Right} association.</li>
     * </ul>
     *
     * @param profile
     *            The selected {@link Profile}.
     * @property.attribute {@link #selectedProfile}
     */
    public void setSelectedProfile(Profile profile) {
        selectedProfile = profile;

        List<Right> availableRights = rightService.getAll().stream()
                .filter(right -> !selectedProfile.getRights().contains(right))
                .collect(Collectors.toList());
        List<Right> associatedRights = new ArrayList<>(
                selectedProfile.getRights());
        rightsAssociation = new DualListModel<Right>(availableRights,
                associatedRights);
    }

}
