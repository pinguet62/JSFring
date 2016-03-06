package fr.pinguet62.jsfring.gui.converter;

import static java.lang.Integer.parseInt;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.service.ProfileService;

/**
 * Convert {@link Profile} to {@link String} value, and conversely, from the
 * primary key.
 */
@Named
@FacesConverter("profileConverter")
public class ProfileConverter implements Converter {

    @Inject
    private ProfileService profileService;

    /**
     * @param id The {@link Profile#id id} as {@link String}.
     * @return The {@link Profile}.
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String id) {
        return profileService.get(parseInt(id));
    }

    /**
     * @param object The {@link Profile}.
     * @return The {@link Profile#id id} as {@link String}.
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
        Profile profile = (Profile) object;
        return profile.getId().toString();
    }

}