package fr.pinguet62.jsfring.ws.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.pinguet62.jsfring.model.User;
import fr.pinguet62.jsfring.ws.dto.UserDto;

@Component
public final class UserConverter implements Converter<User, UserDto> {

    @Override
    public UserDto convert(User user) {
        UserDto dto = new UserDto();
        dto.setLogin(user.getLogin());
        dto.setEmail(user.getEmail());
        dto.setLastConnection(user.getLastConnection());
        return dto;
    }

    @Override
    public String toString() {
        return User.class.getName() + " -> " + UserDto.class.getName() + " : " + super.toString();
    }

}