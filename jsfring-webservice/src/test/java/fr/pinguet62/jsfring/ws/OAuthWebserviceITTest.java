package fr.pinguet62.jsfring.ws;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.ws.Config.BASE_URL;
import static fr.pinguet62.jsfring.ws.OAuthWebservice.AUTORITIES_PATH;
import static fr.pinguet62.jsfring.ws.OAuthWebservice.PATH;
import static fr.pinguet62.jsfring.ws.config.JerseyConfig.CONTEXT_PATH;
import static fr.pinguet62.jsfring.ws.config.Oauth2Helper.HEADER_AUTHORIZATION;
import static javax.ws.rs.client.ClientBuilder.newClient;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.GenericType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.ws.config.Oauth2Helper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
//DbUnit
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup(DATASET)
public class OAuthWebserviceITTest {

    @Inject
    private Oauth2Helper helper;

    /** @see OAuthWebservice#getAutorities() */
    @Test
    public void test_get() {
        // @formatter:off
        List<String> authorities = newClient()
            .target(BASE_URL)
                .path(CONTEXT_PATH)
                .path(PATH)
                .path(AUTORITIES_PATH)
            .request()
                .header(HEADER_AUTHORIZATION, helper.getAuthorization())
            .get(new GenericType<List<String>>() {});
        // @formatter:on

        assertThat(authorities, is(not(empty())));
    }

}