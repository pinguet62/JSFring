package fr.pinguet62.jsfring.webservice.controller;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import fr.pinguet62.jsfring.SpringBootConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.webservice.controller.OAuthController.AUTORITIES_PATH;
import static fr.pinguet62.jsfring.webservice.controller.OAuthController.PATH;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

/**
 * @see OAuthController
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
@EmbeddedKafka
// DbUnit
@TestExecutionListeners(mergeMode = MERGE_WITH_DEFAULTS, listeners = DbUnitTestExecutionListener.class)
@DatabaseSetup(DATASET)
public class OAuthControllerITTest {

    @Autowired
    private RestTemplate authenticatedRestTemplate;

    /**
     * @see OAuthController#getAutorities()
     */
    @Test
    public void test_get() {
        String[] authorities = authenticatedRestTemplate.getForObject(PATH + AUTORITIES_PATH, String[].class);

        assertThat(authorities, arrayWithSize(not(equalTo(0))));
    }

}
