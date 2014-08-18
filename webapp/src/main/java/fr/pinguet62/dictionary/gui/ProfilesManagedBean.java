package fr.pinguet62.dictionary.gui;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;

import fr.pinguet62.dictionary.model.Profile;
import fr.pinguet62.dictionary.service.ProfileService;

@ManagedBean
@ViewScoped
public final class ProfilesManagedBean {

    @Autowired
    private ProfileService profileService;

    /**
     * Get the list of {@link Profile}s to display.
     *
     * @return The {@link Profile}s.
     */
    public List<Profile> getList() {
        return profileService.getAll();
    }

}
