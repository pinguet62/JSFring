package fr.pinguet62.jsfring.webservice.dto

import fr.pinguet62.jsfring.model.sql.Right

/**
 * @see Right
 */
data class RightDto(
        var code: String?,
        var title: String?
) {
    constructor() : this(null, null)
}
