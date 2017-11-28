package fr.pinguet62.jsfring.webapp.jsf.converter;

import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import static java.lang.Integer.parseInt;

/**
 * Convert {@link Profile} to {@link String} value, and conversely, from the primary key.
 */
@Component
@FacesConverter("profileConverter")
public class ProfileConverter implements Converter<Profile> {

    @Autowired
    private ProfileService profileService;

    /**
     * @param id The {@link Profile#id id} as {@link String}.
     * @return The {@link Profile}.
     */
    @Override
    public Profile getAsObject(FacesContext context, UIComponent component, String id) {
        return profileService.get(parseInt(id)).block();
    }

    /**
     * @return The {@link Profile#id id} as {@link String}.
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Profile profile) {
        return profile.getId().toString();
    }

}