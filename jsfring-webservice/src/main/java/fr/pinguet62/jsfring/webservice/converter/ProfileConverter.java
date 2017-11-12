package fr.pinguet62.jsfring.webservice.converter;

import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.model.sql.Right;
import fr.pinguet62.jsfring.webservice.dto.ProfileDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toSet;

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