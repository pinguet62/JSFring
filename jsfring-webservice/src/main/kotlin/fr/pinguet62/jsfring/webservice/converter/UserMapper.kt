package fr.pinguet62.jsfring.webservice.converter

import com.querydsl.core.types.Predicate
import fr.pinguet62.jsfring.model.sql.Profile
import fr.pinguet62.jsfring.model.sql.QProfile
import fr.pinguet62.jsfring.model.sql.User
import fr.pinguet62.jsfring.service.ProfileService
import fr.pinguet62.jsfring.webservice.config.MapstructConfig
import fr.pinguet62.jsfring.webservice.dto.UserDto
import org.mapstruct.Mapper
import org.mapstruct.MappingTarget
import org.springframework.beans.factory.annotation.Autowired
import java.util.stream.Collectors.toSet

@Mapper(config = MapstructConfig::class)
abstract class UserMapper {

    @Autowired
    lateinit var profileService: ProfileService

    protected fun keyToEntity(keys: Set<Int>): Set<Profile> {
        var predicate: Predicate = QProfile.profile.id.`in`(keys)
        return profileService.findAll(predicate).collect(toSet()).block()
    }

    protected fun entityToKey(keys: Set<Profile>): Set<Int> =
            keys.map { it.id }.toSet()

    abstract fun fromDto(dto: UserDto): User

    abstract fun toDto(entity: User): UserDto

    abstract fun toDto(entities: List<User>): List<UserDto>

    abstract fun updateFromDto(entity: User, @MappingTarget dto: UserDto)

}
