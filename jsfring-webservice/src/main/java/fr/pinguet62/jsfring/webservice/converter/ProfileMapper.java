package fr.pinguet62.jsfring.webservice.converter;

import com.querydsl.core.types.Predicate;
import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.model.sql.QRight;
import fr.pinguet62.jsfring.model.sql.Right;
import fr.pinguet62.jsfring.service.RightService;
import fr.pinguet62.jsfring.webservice.config.MapstructConfig;
import fr.pinguet62.jsfring.webservice.dto.ProfileDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Mapper(config = MapstructConfig.class)
public abstract class ProfileMapper {

    @Autowired
    private RightService rightService;

    protected Set<Right> keyToEntity(Set<String> keys) {
        Predicate predicate = QRight.right_.code.in(keys);
        return rightService.findAll(predicate).collect(toSet()).block();
    }

    protected Set<String> entityToKey(Set<Right> keys) {
        return keys.stream().map(Right::getCode).collect(toSet());
    }

    public abstract Profile fromDto(ProfileDto dto);

    public abstract ProfileDto toDto(Profile entity);

    public abstract List<ProfileDto> toDto(List<Profile> entities);

    public abstract void updateFromDto(Profile entity, @MappingTarget ProfileDto dto);

}
