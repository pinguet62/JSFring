package fr.pinguet62.jsfring.ws.dto.converter.profile;

import org.springframework.core.convert.converter.Converter;

import fr.pinguet62.jsfring.model.Profile;
import fr.pinguet62.jsfring.ws.dto.ProfileDto;

public final class ProfileConverter implements Converter<Profile, ProfileDto> {

    @Override
    public ProfileDto convert(Profile profile) {
        return new ProfileDto(profile);
    }

}