package fr.pinguet62.jsfring.gui.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.pinguet62.jsfring.model.Profile;
import fr.pinguet62.jsfring.service.ProfileService;

/**
 * Convert {@link Profile} to {@link String} value, and conversely, from the
 * primary key.
 */
@Component
@FacesConverter("profileConverter")
public class ProfileConverter implements Converter {

    @Autowired
    private ProfileService profileService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {
        return profileService.get(Integer.parseInt(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        Profile profile = (Profile) value;
        return profile.getId().toString();
    }

}
