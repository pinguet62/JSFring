package fr.pinguet62.jsfring.webservice.controller

import fr.pinguet62.jsfring.common.security.userdetails.UserDetailsUtils.getCurrent
import fr.pinguet62.jsfring.webservice.controller.OAuthController.Companion.PATH
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Webservice for OAuth additional utilities.
 */
@RestController
@RequestMapping(PATH)
class OAuthController {

    companion object {
        /**
         * @see OAuthController#getAutorities()
         */
        const val AUTORITIES_PATH = "/autorities"

        const val PATH = "/oauth"
    }

    /**
     * Get the {@link GrantedAuthority#getAuthority() authoritie}s of current connected user.
     *
     * @return The {@link GrantedAuthority#getAuthority() authoritie}s.
     */
    @GetMapping(AUTORITIES_PATH)
    fun getAutorities(): List<String> = getCurrent().authorities.map { it.authority }

}