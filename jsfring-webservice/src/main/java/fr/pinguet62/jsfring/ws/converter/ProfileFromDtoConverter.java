package fr.pinguet62.jsfring.ws.converter;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;
import com.mysema.query.types.Predicate;

import fr.pinguet62.jsfring.model.Profile;
import fr.pinguet62.jsfring.model.QRight;
import fr.pinguet62.jsfring.model.Right;
import fr.pinguet62.jsfring.service.RightService;
import fr.pinguet62.jsfring.ws.dto.ProfileDto;

@Component
public final class ProfileFromDtoConverter implements Converter<ProfileDto, Profile> {

    @Inject
    private RightService rightService;

    @Override
    public Profile convert(ProfileDto dto) {
        Profile profile = new Profile();

        profile.setId(dto.getId());
        profile.setTitle(dto.getTitle());

        Predicate query = QRight.right_.code.in(dto.getRights());
        Iterable<Right> rights = rightService.findAll(query);
        profile.setRights(Sets.newHashSet(rights));

        return profile;
    }

    @Override
    public String toString() {
        return ProfileDto.class.getName() + " -> " + Profile.class.getName() + " : " + super.toString();
    }

}