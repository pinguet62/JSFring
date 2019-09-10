package fr.pinguet62.jsfring.webservice.controller;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.dao.sql.RightDao;
import fr.pinguet62.jsfring.model.sql.Right;
import fr.pinguet62.jsfring.webservice.dto.RightDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.webservice.controller.RightController.PATH;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

/**
 * @see RightController
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT, properties = {
        "KAFKA_HOST=localhost:9092",
        "spring.kafka.properties.security.protocol=PLAINTEXT",
})
@EmbeddedKafka(brokerProperties = "listeners=PLAINTEXT://${KAFKA_HOST}")
// DbUnit
@TestExecutionListeners(mergeMode = MERGE_WITH_DEFAULTS, listeners = DbUnitTestExecutionListener.class)
@DatabaseSetup(DATASET)
public class RightControllerITTest {

    @Autowired
    private RightDao rightDao;

    @Autowired
    private RestTemplate authenticatedRestTemplate;

    /**
     * @see RightController#get(String)
     */
    @Test
    public void test_get() {
        String code = rightDao.findAll().get(0).getCode();

        RightDto actual = authenticatedRestTemplate.getForObject(PATH + "/{code}", RightDto.class, code);

        Right expected = rightDao.findById(code).get();

        assertThat(actual.getCode(), is(equalTo(expected.getCode())));
        assertThat(actual.getTitle(), is(equalTo(expected.getTitle())));
    }

    /**
     * @see RightController#list()
     */
    @Test
    public void test_list() {
        RightDto[] actual = authenticatedRestTemplate.getForObject(PATH, RightDto[].class);

        List<Right> expected = rightDao.findAll();

        assertThat(actual, arrayWithSize(expected.size()));
    }

}
