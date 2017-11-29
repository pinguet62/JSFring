package fr.pinguet62.jsfring.webservice.converter

import fr.pinguet62.jsfring.webservice.dto.PageDto
import org.springframework.data.domain.Page
import java.util.stream.Collectors.toList

fun <Entity, Dto> toDto(entities: Page<Entity>, elementMapper: (Entity) -> Dto): PageDto<Dto> {
//    if (entities == null)
//        return null
    var dtos: List<Dto> = entities.stream().map(elementMapper).collect(toList()) // TODO Kotlin
    return PageDto(dtos, entities.totalElements)
}
