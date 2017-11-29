package fr.pinguet62.jsfring.webservice.converter

import com.querydsl.core.types.Predicate
import fr.pinguet62.jsfring.model.sql.Profile
import fr.pinguet62.jsfring.model.sql.QRight
import fr.pinguet62.jsfring.model.sql.Right
import fr.pinguet62.jsfring.service.RightService
import fr.pinguet62.jsfring.webservice.config.MapstructConfig
import fr.pinguet62.jsfring.webservice.dto.ProfileDto
import org.mapstruct.Mapper
import org.mapstruct.MappingTarget
import org.springframework.beans.factory.annotation.Autowired
import java.util.stream.Collectors.toSet

@Mapper(config = MapstructConfig::class)
abstract class ProfileMapper {

    @Autowired
    lateinit var rightService: RightService

    protected fun keyToEntity(keys: Set<String>): Set<Right> {
        var predicate: Predicate = QRight.right_.code.`in`(keys)
        return rightService.findAll(predicate).collect(toSet()).block()
    }

    protected fun entityToKey(keys: Set<Right>): Set<String> =
            keys.map { it.code }.toSet()

    abstract fun fromDto(dto: ProfileDto): Profile

    abstract fun toDto(entity: Profile): ProfileDto

    abstract fun toDto(entities: List<Profile>): List<ProfileDto>

    abstract fun updateFromDto(entity: Profile, @MappingTarget dto: ProfileDto)

}
