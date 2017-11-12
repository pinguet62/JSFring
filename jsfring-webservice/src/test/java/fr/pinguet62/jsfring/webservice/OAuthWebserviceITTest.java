package fr.pinguet62.jsfring.webservice;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import fr.pinguet62.jsfring.SpringBootConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.web.client.RestTemplate;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.webservice.OAuthWebservice.AUTORITIES_PATH;
import static fr.pinguet62.jsfring.webservice.OAuthWebservice.PATH;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

/**
 * @see OAuthWebservice
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
// DbUnit
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup(DATASET)
public class OAuthWebserviceITTest {

    @Autowired
    private RestTemplate authenticatedRestTemplate;

    /**
     * @see OAuthWebservice#getAutorities()
     */
    @Test
    public void test_get() {
        String[] authorities = authenticatedRestTemplate.getForObject(PATH + AUTORITIES_PATH, String[].class);

        assertThat(authorities, arrayWithSize(not(equalTo(0))));
    }

}