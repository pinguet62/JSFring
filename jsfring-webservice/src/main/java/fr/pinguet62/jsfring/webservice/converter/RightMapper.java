package fr.pinguet62.jsfring.webservice.converter;

import fr.pinguet62.jsfring.model.sql.Right;
import fr.pinguet62.jsfring.webservice.config.MapstructConfig;
import fr.pinguet62.jsfring.webservice.dto.RightDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = MapstructConfig.class)
public interface RightMapper {

    RightDto toDto(Right entity);

    List<RightDto> toDto(List<Right> entities);

    void updateFromDto(Right entity, @MappingTarget RightDto dto);

}
