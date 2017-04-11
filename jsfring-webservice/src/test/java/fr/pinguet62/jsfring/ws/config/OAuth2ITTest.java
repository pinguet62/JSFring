package fr.pinguet62.jsfring.ws.config;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.util.MatcherUtils.parameter;
import static fr.pinguet62.jsfring.util.UrlUtils.formatAuthorization;
import static fr.pinguet62.jsfring.ws.Config.BASE_URL;
import static java.util.Arrays.asList;
import static org.apache.commons.io.Charsets.UTF_8;
import static org.apache.http.client.utils.URLEncodedUtils.format;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import javax.inject.Inject;
import javax.servlet.Filter;

import org.apache.http.message.BasicNameValuePair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.dao.sql.UserDao;
import fr.pinguet62.jsfring.model.sql.User;

/** Test several scenarios of OAuth2 server. */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
// DbUnit
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup(DATASET)
public class OAuth2ITTest {

    @Value("${security.oauth2.client.clientId}")
    private String clientId;

    @Value("${security.oauth2.client.clientSecret}")
    private String clientSecret;

    @Value("${security.oauth2.client.authorized-grant-types}")
    private String grantTypes;

    private MockMvc mockMvc;

    private final String redirectUri = "http://example.com";

    @Inject
    private Filter springSecurityFilterChain;

    private User user;

    @Inject
    private UserDao userDao;

    @Inject
    private WebApplicationContext webApplicationContext;

    @Before
    public void before() {
        user = userDao.findAll().get(0);
    }

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(springSecurityFilterChain).build();
    }

    private URL test_authorize(String responseType) throws IOException {
        String authorization = "Basic " + formatAuthorization(user.getLogin(), user.getPassword());
        // @formatter:off
        String url = BASE_URL + "/oauth/authorize?"
                + format(
                        asList(
                                new BasicNameValuePair("client_id", clientId),
                                new BasicNameValuePair("response_type", responseType),
                                new BasicNameValuePair("scope", "read"), // TODO injection config
                                new BasicNameValuePair("redirect_uri", redirectUri)),
                        UTF_8);
        // @formatter:on

        WebClient webClient = new WebClient();
        webClient.addRequestHeader("Authorization", authorization);
        HtmlPage page = webClient.getPage(url);

        // TODO Disable Authorization cache
        if (!page.getUrl().toString().startsWith(redirectUri)) {
            assertThat(page.getUrl().toString(), containsString("/oauth/authorize"));

            HtmlInput approuve = page.getFirstByXPath("/html/body/form/ul/li/div/input[@value='true']");
            approuve.click();
            HtmlInput submit = page.getFirstByXPath("/html/body/form/label/input[@type='submit']");
            page = submit.click();
        }

        return page.getUrl();
    }

    /**
     * <b>Request:</b>
     * <ul>
     * <li><u>URL:</u> {@code "/oauth/authorize"}</li>
     * <li><u>Method:</u> {@code GET}</li>
     * <li><u>Header:</u> {@code Authorization} = {@code "Basic " +} base64 encoded {@code "username:password"}</li>
     * <li><u>Parameter:</u> {@code "client_id"}</li>
     * <li><u>Parameter:</u> {@code "response_type"} = {@code "code"}</li>
     * <li><u>Parameter:</u> {@code "scope"} = {@code "read"}</li>
     * <li><u>Parameter:</u> {@code "redirect_uri"}</li>
     * </ul>
     *
     * <b>Response:</b> values in {@link URI#getQuery() URL query}:
     *
     * <ul>
     * <li><u>Parameter:</u> {@code "code"}</li>
     * </ul>
     *
     * @see AuthorizationEndpoint
     */
    @Test
    public void test_authorize_code() throws Exception {
        URL targetUrl = test_authorize("code");
        assertThat(targetUrl.toString(), startsWith(redirectUri));
        String fragment = targetUrl.toURI().getQuery();
        assertThat(fragment, parameter("code", is(not(nullValue()))));
    }

    /**
     * Use {@code "token"} response type, who requires <i>grant type</i> {@code "implicit"}.
     *
     * <b>Request:</b>
     *
     * <ul>
     * <li><u>URL:</u> {@code "/oauth/authorize"}</li>
     * <li><u>Method:</u> {@code GET}</li>
     * <li><u>Header:</u> {@code Authorization} = {@code "Basic " +} base64 encoded {@code "username:password"}</li>
     * <li><u>Parameter:</u> {@code "client_id"}</li>
     * <li><u>Parameter:</u> {@code "response_type"} = {@code "token"}</li>
     * <li><u>Parameter:</u> {@code "scope"} = {@code "read"}</li>
     * <li><u>Parameter:</u> {@code "redirect_uri"}</li>
     * </ul>
     *
     * <b>Response:</b> values in {@link URI#getFragment() URL fragment}:
     *
     * <ul>
     * <li><u>Parameter:</u> {@code "access_token"}</li>
     * <li><u>Parameter:</u> {@code "token_type"} = {@code "bearer"}</li>
     * <li><u>Parameter:</u> {@code "expires_in"}</li>
     * </ul>
     *
     *
     * @see AuthorizationEndpoint
     */
    @Test
    public void test_authorize_token() throws Exception {
        URL targetUrl = test_authorize("token");
        assertThat(targetUrl.toString(), startsWith(redirectUri));
        String fragment = targetUrl.toURI().getFragment();
        assertThat(fragment, parameter("access_token", is(not(nullValue()))));
        assertThat(fragment, parameter("token_type", is(equalTo("bearer"))));
        assertThat(fragment, parameter("expires_in", is(not(nullValue()))));
    }

    /**
     * <b>Request:</b>
     *
     * <ul>
     * <li><u>URL:</u> {@code "/oauth/token"}</li>
     * <li><u>Method:</u> {@code POST}</li>
     * <li><u>Header:</u> {@code Authorization} = {@code "Basic " +} base64 encoded {@code "clientId:clientSecret"}</li>
     * <li><u>Parameter:</u> {@code "grant_type"} = {@code "password"}</li>
     * <li><u>Parameter:</u> {@code "username"}</li>
     * <li><u>Parameter:</u> {@code "password"}</li>
     * </ul>
     *
     * <b>Response:</b> JSON with values:
     *
     * <ul>
     * <li><u>Value:</u> {@code "access_token"}</li>
     * <li><u>Value:</u> {@code "token_type"} = {@code "bearer"}</li>
     * <li><u>Value:</u> {@code "refresh_token"} if <i>grant type</i> {@code "refresh_token"}</li>
     * <li><u>Value:</u> {@code "expires_in"}</li>
     * <li><u>Value:</u> {@code "scope"} = {@code "read"}</li>
     * </ul>
     *
     * @see TokenEndpoint
     */
    @Test
    public void test_oauthToken() throws Exception {
        String authorization = "Basic " + formatAuthorization(clientId, clientSecret);
        // @formatter:off
        ResultActions result =
                mockMvc.perform(
                        post("/oauth/token")
                            .header("Authorization", authorization)
                            .param("grant_type", "password")
                            .param("username", user.getLogin())
                            .param("password", user.getPassword()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token", is(notNullValue())))
                .andExpect(jsonPath("$.token_type", is(equalTo("bearer"))))
                .andExpect(jsonPath("$.expires_in", is(greaterThan(4000))))
                .andExpect(jsonPath("$.scope", is(notNullValue())));
        // @formatter:on
        if (grantTypes.contains("refresh_token"))
            result.andExpect(jsonPath("$.refresh_token", is(notNullValue())));
    }

}