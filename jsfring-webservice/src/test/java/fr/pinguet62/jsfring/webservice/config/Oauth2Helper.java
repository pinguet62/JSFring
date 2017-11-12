package fr.pinguet62.jsfring.webservice.config;

import fr.pinguet62.jsfring.dao.sql.UserDao;
import fr.pinguet62.jsfring.model.sql.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static fr.pinguet62.jsfring.common.UrlUtils.formatAuthorization;
import static fr.pinguet62.jsfring.webservice.config.RestTemplateConfig.BASE_URL;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.oauth2.common.OAuth2AccessToken.ACCESS_TOKEN;
import static org.springframework.security.oauth2.common.OAuth2AccessToken.BEARER_TYPE;
import static org.springframework.security.oauth2.common.util.OAuth2Utils.GRANT_TYPE;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

/**
 * Client used to get OAuth2 token.
 */
@Component
public class Oauth2Helper {

    /**
     * Key of {@code Authorization} header.
     */
    public static final String HEADER_AUTHORIZATION = "Authorization";

    @Autowired
    private ClientDetails details;

    @Autowired
    private UserDao userDao;

    /**
     * Request new token, and build the {@code "Authorization"} header.
     *
     * @return The {@code "Authorization"} value.
     */
    public String getAuthorization() {
        return BEARER_TYPE + " " + getToken();
    }

    /**
     * Request OAuth server to get new token.<br>
     * Choose any {@link User}.
     *
     * @return The OAuth2 token.
     */
    public String getToken() {
        // OAuth2 authorization
        String authorization = "Basic " + formatAuthorization(details.getClientId(), details.getClientSecret().replace("{noop}", ""));
        HttpHeaders headers = new HttpHeaders();
        headers.set(HEADER_AUTHORIZATION, authorization);

        User user = userDao.findAll().get(0);

        // @formatter:off
        HttpEntity<String> response = new RestTemplate()
                .exchange(
                        fromHttpUrl(BASE_URL)
                            .path("/oauth/token")
                            .queryParam(GRANT_TYPE, "password")
                            .queryParam("username", user.getEmail())
                            .queryParam("password", user.getPassword().replace("{noop}", ""))
                            .build().encode().toUri(),
                        POST,
                        new HttpEntity<>(headers),
                        String.class
                    );
        // @formatter:on
        return (String) new BasicJsonParser().parseMap(response.getBody()).get(ACCESS_TOKEN);
    }

}