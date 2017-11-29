package fr.pinguet62.jsfring.webservice.config

import fr.pinguet62.jsfring.common.UrlUtils.formatAuthorization
import fr.pinguet62.jsfring.dao.sql.UserDao
import fr.pinguet62.jsfring.model.sql.User
import fr.pinguet62.jsfring.webservice.config.RestTemplateConfig.Companion.BASE_URL
import org.springframework.boot.json.BasicJsonParser
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod.POST
import org.springframework.security.oauth2.common.OAuth2AccessToken.ACCESS_TOKEN
import org.springframework.security.oauth2.common.OAuth2AccessToken.BEARER_TYPE
import org.springframework.security.oauth2.common.util.OAuth2Utils.GRANT_TYPE
import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder.fromHttpUrl

/**
 * Client used to get OAuth2 token.
 */
@Component
open class Oauth2Helper(
        val details: ClientDetails,
        val userDao: UserDao
) {

    companion object {
        /**
         * Key of {@code Authorization} header.
         */
        val HEADER_AUTHORIZATION = "Authorization"
    }

    /**
     * Request new token, and build the {@code "Authorization"} header.
     *
     * @return The {@code "Authorization"} value.
     */
    fun getAuthorization() = BEARER_TYPE + " " + getToken()

    /**
     * Request OAuth server to get new token.<br>
     * Choose any {@link User}.
     *
     * @return The OAuth2 token.
     */
    fun getToken(): String {
        // OAuth2 authorization
        var authorization: String = "Basic " + formatAuthorization(details.clientId, details.clientSecret.replace("{noop}", ""))
        var headers = HttpHeaders()
        headers.set(HEADER_AUTHORIZATION, authorization)

        var user: User = userDao.findAll()[0]

        var response: HttpEntity<String> = RestTemplate().exchange(
                fromHttpUrl(BASE_URL)
                        .path("/oauth/token")
                        .queryParam(GRANT_TYPE, "password")
                        .queryParam("username", user.email)
                        .queryParam("password", user.password.replace("{noop}", ""))
                        .build().encode().toUri(),
                POST,
                HttpEntity<String>(headers),
                String::class.java
        )
        return BasicJsonParser().parseMap(response.body)[ACCESS_TOKEN] as String
    }

}