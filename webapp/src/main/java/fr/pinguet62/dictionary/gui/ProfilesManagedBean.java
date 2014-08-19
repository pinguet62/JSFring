package fr.pinguet62.dictionary.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.pinguet62.dictionary.model.Profile;
import fr.pinguet62.dictionary.model.Right;
import fr.pinguet62.dictionary.service.ProfileService;
import fr.pinguet62.dictionary.service.RightService;

@Component
@ManagedBean
@ViewScoped
public final class ProfilesManagedBean {

    @Autowired
    private ProfileService profileService;

    /**
     * The {@link Right} association (available/associated) of the selected
     * {@link Profile}.
     */
    private DualListModel<Right> rightsAssociation = new DualListModel<>();

    @Autowired
    private RightService rightService;

    /** The {@link Profile} to display or update. */
    private Profile selectedProfile;

    /**
     * Get the list of {@link Profile}s to display.
     *
     * @return The {@link Profile}s.
     */
    public List<Profile> getList() {
        return profileService.getAll();
    }

    public DualListModel<Right> getRightsAssociation() {
        return rightsAssociation;
    }

    public Profile getSelectedProfile() {
        return selectedProfile;
    }

    public void setRightsAssociation(DualListModel<Right> rightsAssociation) {
        this.rightsAssociation = rightsAssociation;
    }

    /**
     * Set the selected {@link Profile}.<br/>
     * Call when the user click on "Show" button.<br/>
     * Init the {@link Right} association.
     *
     * @param profile
     *            The selected {@link Profile}.
     */
    public void setSelectedProfile(Profile profile) {
        selectedProfile = profile;

        List<Right> associatedRights = new ArrayList<>(
                selectedProfile.getRights());
        List<Right> availableRights = rightService.getAll().stream()
                .filter(right -> !selectedProfile.getRights().contains(right))
                .collect(Collectors.toList());
        rightsAssociation = new DualListModel<Right>(associatedRights,
                availableRights);
    }

}
