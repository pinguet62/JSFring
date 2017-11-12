package fr.pinguet62.jsfring.webservice.converter;

import com.querydsl.core.types.Predicate;
import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.model.sql.QRight;
import fr.pinguet62.jsfring.model.sql.Right;
import fr.pinguet62.jsfring.service.RightService;
import fr.pinguet62.jsfring.webservice.dto.ProfileDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Component
public final class ProfileFromDtoConverter implements Converter<ProfileDto, Profile> {

    private final RightService rightService;

    public ProfileFromDtoConverter(RightService rightService) {
        this.rightService = requireNonNull(rightService);
    }

    @Override
    public Profile convert(ProfileDto dto) {
        Profile profile = new Profile();

        profile.setId(dto.getId());
        profile.setTitle(dto.getTitle());

        if (dto.getRights() != null) {
            Predicate predicate = QRight.right_.code.in(dto.getRights());
            List<Right> rights = rightService.findAll(predicate);
            profile.setRights(new HashSet<>(rights));
        }

        return profile;
    }

    @Override
    public String toString() {
        return ProfileDto.class.getName() + " -> " + Profile.class.getName() + " : " + super.toString();
    }

}