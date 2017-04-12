package fr.pinguet62.jsfring.ws.converter;

import static java.util.stream.Collectors.toSet;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.model.sql.User;
import fr.pinguet62.jsfring.ws.dto.UserDto;

@Component
public final class UserConverter implements Converter<User, UserDto> {

    @Override
    public UserDto convert(User user) {
        UserDto dto = new UserDto();
        dto.setEmail(user.getEmail());
        dto.setActive(user.getActive());
        dto.setLastConnection(user.getLastConnection());
        dto.setProfiles(user.getProfiles().stream().map(Profile::getId).collect(toSet()));
        return dto;
    }

    @Override
    public String toString() {
        return User.class.getName() + " -> " + UserDto.class.getName() + " : " + super.toString();
    }

}