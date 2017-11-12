package fr.pinguet62.jsfring.webservice.config;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.dao.sql.UserDao;
import fr.pinguet62.jsfring.model.sql.User;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import static fr.pinguet62.jsfring.common.UrlUtils.formatAuthorization;
import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.util.MatcherUtils.parameter;
import static fr.pinguet62.jsfring.webservice.config.RestTemplateConfig.BASE_URL;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.asList;
import static org.apache.http.client.utils.URLEncodedUtils.format;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.security.oauth2.common.OAuth2AccessToken.*;
import static org.springframework.security.oauth2.common.util.OAuth2Utils.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test several scenarios of OAuth2 server.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
// DbUnit
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup(DATASET)
public class OAuth2ITTest {

    @Autowired
    private ClientDetails details;

    private MockMvc mockMvc;

    private final String redirectUri = "http://example.com";

    @Autowired
    private Filter springSecurityFilterChain;

    private User user;

    @Autowired
    private UserDao userDao;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void initMock() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(springSecurityFilterChain).build();
    }

    @Before
    public void initUser() {
        user = userDao.findAll().get(0);
    }

    /**
     * Call the OAuth2 server with specific {@code "response_type"}, click on submit button.
     *
     * @return The target url.
     */
    private URL test_authorize(String responseType) throws IOException {
        try (WebClient webClient = new WebClient()) {
            // Auto-fill native dialog (not supported by HtmlUnit)
            String authorization = "Basic " + formatAuthorization(user.getEmail(), user.getPassword().replace("{noop}", ""));
            webClient.addRequestHeader("Authorization", authorization);

            // @formatter:off
            String url = BASE_URL + "/oauth/authorize?"
                    + format(
                            asList(
                                    new BasicNameValuePair(CLIENT_ID, details.getClientId()),
                                    new BasicNameValuePair(RESPONSE_TYPE, responseType),
                                    new BasicNameValuePair(OAuth2Utils.SCOPE, "read"),
                                    new BasicNameValuePair(REDIRECT_URI, redirectUri)),
                            UTF_8);
            // @formatter:on

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
     * <p>
     * <b>Response:</b> values in {@link URI#getQuery() URL query}:
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
     * <p>
     * <b>Request:</b>
     * <ul>
     * <li><u>URL:</u> {@code "/oauth/authorize"}</li>
     * <li><u>Method:</u> {@code GET}</li>
     * <li><u>Header:</u> {@code Authorization} = {@code "Basic " +} base64 encoded {@code "username:password"}</li>
     * <li><u>Parameter:</u> {@code "client_id"}</li>
     * <li><u>Parameter:</u> {@code "response_type"} = {@code "token"}</li>
     * <li><u>Parameter:</u> {@code "scope"} = {@code "read"}</li>
     * <li><u>Parameter:</u> {@code "redirect_uri"}</li>
     * </ul>
     * <p>
     * <b>Response:</b> values in {@link URI#getFragment() URL fragment}:
     * <ul>
     * <li><u>Parameter:</u> {@code "access_token"}</li>
     * <li><u>Parameter:</u> {@code "token_type"} = {@code "bearer"}</li>
     * <li><u>Parameter:</u> {@code "expires_in"}</li>
     * </ul>
     *
     * @see AuthorizationEndpoint
     */
    @Test
    public void test_authorize_token() throws Exception {
        URL targetUrl = test_authorize("token");
        assertThat(targetUrl.toString(), startsWith(redirectUri));
        String fragment = targetUrl.toURI().getFragment();
        assertThat(fragment, parameter(ACCESS_TOKEN, is(not(nullValue()))));
        assertThat(fragment, parameter(TOKEN_TYPE, is(equalTo("bearer"))));
        assertThat(fragment, parameter(EXPIRES_IN, is(not(nullValue()))));
    }

    /**
     * <b>Request:</b>
     * <ul>
     * <li><u>URL:</u> {@code "/oauth/token"}</li>
     * <li><u>Method:</u> {@code POST}</li>
     * <li><u>Header:</u> {@code Authorization} = {@code "Basic " +} base64 encoded {@code "clientId:clientSecret"}</li>
     * <li><u>Parameter:</u> {@code "grant_type"} = {@code "password"}</li>
     * <li><u>Parameter:</u> {@code "username"}</li>
     * <li><u>Parameter:</u> {@code "password"}</li>
     * </ul>
     * <p>
     * <b>Response:</b> JSON with values:
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
        String authorization = "Basic " + formatAuthorization(details.getClientId(), details.getClientSecret().replace("{noop}", ""));
        // @formatter:off
        ResultActions result =
                mockMvc.perform(
                        post("/oauth/token")
                        .header("Authorization", authorization)
                        .param(GRANT_TYPE, "password")
                        .param("username", user.getEmail())
                        .param("password", user.getPassword().replace("{noop}", "")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$." + ACCESS_TOKEN, is(notNullValue())))
                .andExpect(jsonPath("$." + TOKEN_TYPE, is(equalTo("bearer"))))
                .andExpect(jsonPath("$." + EXPIRES_IN, is(greaterThan(4000))))
                .andExpect(jsonPath("$." + OAuth2AccessToken.SCOPE, is(notNullValue())));
        // @formatter:on
        if (details.getAuthorizedGrantTypes().contains(REFRESH_TOKEN))
            result.andExpect(jsonPath("$." + REFRESH_TOKEN, is(notNullValue())));
    }

}