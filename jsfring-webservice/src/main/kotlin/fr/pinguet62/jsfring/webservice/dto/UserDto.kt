package fr.pinguet62.jsfring.webservice.dto

import fr.pinguet62.jsfring.model.sql.User
import java.time.LocalDateTime

/**
 * @see User
 */
data class UserDto(
        var active: Boolean?,
        var email: String?,
        var lastConnection: LocalDateTime?,
        var profiles: Set<Int>?
) {
    constructor() : this(null, null, null, null)
}
