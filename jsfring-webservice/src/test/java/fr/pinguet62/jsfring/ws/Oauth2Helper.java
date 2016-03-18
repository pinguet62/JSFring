package fr.pinguet62.jsfring.ws;

import static fr.pinguet62.jsfring.util.UrlUtils.formatAuthorization;
import static fr.pinguet62.jsfring.ws.Config.BASE_URL;
import static javax.ws.rs.client.ClientBuilder.newClient;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.stereotype.Component;

import fr.pinguet62.jsfring.dao.sql.UserDao;
import fr.pinguet62.jsfring.model.sql.User;

@Component
public class Oauth2Helper {

    @Value("${security.oauth2.client.clientId}")
    private String clientId;

    @Value("${security.oauth2.client.clientSecret}")
    private String clientSecret;

    @Inject
    private UserDao userDao;

    /**
     * Request new token, and build the {@code "Authorization"} header value.
     *
     * @return The {@code "Authorization"}.
     */
    public String getAuthorization() {
        return "Bearer " + getToken();
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
                .queryParam("grant_type", "password")
                .queryParam("username", user.getLogin())
                .queryParam("password", user.getPassword())
            .request()
                .header("Authorization", authorization)
            .post(null, String.class);
        // @formatter:on
        return (String) new BasicJsonParser().parseMap(response).get("access_token");
    }

}