package fr.pinguet62.jsfring.ws.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.pinguet62.jsfring.model.Profile;
import fr.pinguet62.jsfring.ws.dto.ProfileDto;

@Component
public final class ProfileConverter implements Converter<Profile, ProfileDto> {

    @Override
    public ProfileDto convert(Profile profile) {
        ProfileDto dto = new ProfileDto();
        dto.setId(profile.getId());
        dto.setTitle(profile.getTitle());
        return dto;
    }

    @Override
    public String toString() {
        return Profile.class.getName() + " -> " + ProfileDto.class.getName() + " : " + super.toString();
    }

}