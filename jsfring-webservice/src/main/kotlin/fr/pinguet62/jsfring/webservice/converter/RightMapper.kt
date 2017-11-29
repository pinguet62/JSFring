package fr.pinguet62.jsfring.webservice.converter

import fr.pinguet62.jsfring.model.sql.Right
import fr.pinguet62.jsfring.webservice.config.MapstructConfig
import fr.pinguet62.jsfring.webservice.dto.RightDto
import org.mapstruct.Mapper
import org.mapstruct.MappingTarget

@Mapper(config = MapstructConfig::class)
interface RightMapper {

    fun toDto(entity: Right): RightDto

    fun toDto(entities: List<Right>): List<RightDto>

    fun updateFromDto(entity: Right, @MappingTarget dto: RightDto)

}
