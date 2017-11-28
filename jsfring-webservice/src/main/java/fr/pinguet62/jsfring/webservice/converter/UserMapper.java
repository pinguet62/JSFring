package fr.pinguet62.jsfring.webservice.converter;

import com.querydsl.core.types.Predicate;
import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.model.sql.QProfile;
import fr.pinguet62.jsfring.model.sql.User;
import fr.pinguet62.jsfring.service.ProfileService;
import fr.pinguet62.jsfring.webservice.config.MapstructConfig;
import fr.pinguet62.jsfring.webservice.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Mapper(config = MapstructConfig.class)
public abstract class UserMapper {

    @Autowired
    private ProfileService profileService;

    protected Set<Profile> keyToEntity(Set<Integer> keys) {
        Predicate predicate = QProfile.profile.id.in(keys);
        return profileService.findAll(predicate).collect(toSet()).block();
    }

    protected Set<Integer> entityToKey(Set<Profile> keys) {
        return keys.stream().map(Profile::getId).collect(toSet());
    }

    public abstract User fromDto(UserDto dto);

    public abstract UserDto toDto(User entity);

    public abstract List<UserDto> toDto(List<User> entities);

    public abstract void updateFromDto(User entity, @MappingTarget UserDto dto);

}
