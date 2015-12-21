package fr.pinguet62.jsfring.ws.converter;

import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.mysema.query.jpa.impl.JPAQuery;

import fr.pinguet62.jsfring.model.Profile;
import fr.pinguet62.jsfring.model.QProfile;
import fr.pinguet62.jsfring.model.User;
import fr.pinguet62.jsfring.service.ProfileService;
import fr.pinguet62.jsfring.ws.dto.UserDto;

@Component
public final class UserFromDtoConverter implements Converter<UserDto, User> {

    @Inject
    private ProfileService profileService;

    @Override
    public User convert(UserDto dto) {
        User user = new User();
        user.setLogin(dto.getLogin());
        user.setEmail(dto.getEmail());
        user.setActive(dto.getActive());
        user.setLastConnection(dto.getLastConnection());

        QProfile p = QProfile.profile;
        JPAQuery query = new JPAQuery().from(p).where(p.id.in(dto.getProfiles()));
        List<Profile> profiles = profileService.find(query);
        user.setProfiles(new HashSet<>(profiles));

        return user;
    }

    @Override
    public String toString() {
        return UserDto.class.getName() + " -> " + User.class.getName() + " : " + super.toString();
    }

}