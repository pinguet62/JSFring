package fr.pinguet62.jsfring.ws.config;

import static fr.pinguet62.jsfring.util.UrlUtils.formatAuthorization;
import static fr.pinguet62.jsfring.ws.config.JaxrsClientConfig.BASE_URL;
import static javax.ws.rs.client.ClientBuilder.newClient;
import static org.springframework.security.oauth2.common.OAuth2AccessToken.BEARER_TYPE;
import static org.springframework.security.oauth2.common.util.OAuth2Utils.*;
import javax.inject.Inject;
import static org.springframework.security.oauth2.common.OAuth2AccessToken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.stereotype.Component;

import fr.pinguet62.jsfring.dao.sql.UserDao;
import fr.pinguet62.jsfring.model.sql.User;

/** Client used to get OAuth2 token. */
@Component
public class Oauth2Helper {

    /** Key of {@code Authorization} header. */
    public static final String HEADER_AUTHORIZATION = "Authorization";

    @Value("${security.oauth2.client.clientId}")
    private String clientId;

    @Value("${security.oauth2.client.clientSecret}")
    private String clientSecret;

    @Inject
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
     * @return The token.
     */
    public String getToken() {
        String authorization = "Basic " + formatAuthorization(clientId, clientSecret);
        User user = userDao.findAll().get(0);
        // @formatter:off
        String response = newClient().target(BASE_URL)
                .path("/oauth/token")
                    .queryParam(GRANT_TYPE, "password")
                    .queryParam("username", user.getEmail())
                    .queryParam("password", user.getPassword())
                .request()
                    .header(HEADER_AUTHORIZATION, authorization)
                .post(null, String.class);
        // @formatter:on
        return (String) new BasicJsonParser().parseMap(response).get(ACCESS_TOKEN);
    }

}