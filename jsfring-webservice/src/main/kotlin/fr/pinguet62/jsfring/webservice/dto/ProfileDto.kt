package fr.pinguet62.jsfring.webservice.dto

import fr.pinguet62.jsfring.model.sql.Profile

/**
 * @see Profile
 */
data class ProfileDto(
        var id: Int?,
        var rights: Set<String>?,
        var title: String?
) {
    constructor() : this(null, null, null)
}
