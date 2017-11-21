package fr.pinguet62.jsfring.webservice.converter;

import fr.pinguet62.jsfring.webservice.dto.PageDto;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@UtilityClass
public class PageMapper {

    public <Entity, Dto> PageDto<Dto> toDto(Page<Entity> entity, Function<Entity, Dto> elementMapper) {
        if (entity == null)
            return null;
        List<Dto> dtos = entity.stream().map(elementMapper).collect(toList());
        return new PageDto<>(dtos, entity.getTotalElements());
    }

}