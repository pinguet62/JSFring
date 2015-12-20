package fr.pinguet62.jsfring.ws.converter;

import static java.util.stream.Collectors.toSet;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.pinguet62.jsfring.model.Profile;
import fr.pinguet62.jsfring.model.Right;
import fr.pinguet62.jsfring.ws.dto.ProfileDto;

@Component
public final class ProfileConverter implements Converter<Profile, ProfileDto> {

    @Override
    public ProfileDto convert(Profile profile) {
        ProfileDto dto = new ProfileDto();
        dto.setId(profile.getId());
        dto.setTitle(profile.getTitle());
        dto.setRights(profile.getRights().stream().map(Right::getCode).collect(toSet()));
        return dto;
    }

    @Override
    public String toString() {
        return Profile.class.getName() + " -> " + ProfileDto.class.getName() + " : " + super.toString();
    }

}