package fr.pinguet62.jsfring.webservice.controller;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.dao.sql.ProfileDao;
import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.webservice.dto.ProfileDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.isJson;
import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.webservice.controller.ProfileController.PATH;
import static java.util.Comparator.comparing;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

/**
 * @see ProfileController
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
// DbUnit
@TestExecutionListeners(mergeMode = MERGE_WITH_DEFAULTS, listeners = DbUnitTestExecutionListener.class)
@DatabaseSetup(DATASET)
public class ProfileControllerITTest {

    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private RestTemplate authenticatedRestTemplate;

    /**
     * @see ProfileController#get(int)
     */
    @Test
    public void test_get() {
        int id = profileDao.findAll().get(0).getId();

        ProfileDto actual = authenticatedRestTemplate.getForObject(PATH + "/{id}", ProfileDto.class, id);

        Profile expected = profileDao.findById(id).get();

        assertThat(actual.getId(), is(equalTo(expected.getId())));
        assertThat(actual.getTitle(), is(equalTo(expected.getTitle())));
    }

    /**
     * @see ProfileController#list()
     */
    @Test
    public void test_list() {
        ProfileDto[] actual = authenticatedRestTemplate.getForObject(PATH, ProfileDto[].class);

        List<Profile> expected = profileDao.findAll();

        assertThat(actual, arrayWithSize(expected.size()));
    }

    /**
     * @see ProfileController#find(int, int, String, String)
     */
    @Test
    public void test_find() {
        int page = 0;
        int pageSize = 1;
        String sortField = "title";
        String sortOrder = "desc";

        String result = authenticatedRestTemplate.getForObject(
                PATH + "/find" + "?page={page}&pageSize={pageSize}&sortField={sortField}&sortOrder={sortOrder}",
                String.class,
                page, pageSize, sortField, sortOrder
        );

        Profile profile = profileDao.findAll().stream()
                .sorted(comparing(Profile::getTitle).reversed()) // sortField
                .findFirst().get();
        assertThat(result, allOf(
                hasJsonPath("total", equalTo((int) profileDao.count())), // no filter
                hasJsonPath("results", hasSize(pageSize)),
                hasJsonPath("results[0]", allOf(
                        isJson(),
                        hasJsonPath("id", equalTo(profile.getId())),
                        hasJsonPath("title", equalTo(profile.getTitle())),
                        hasJsonPath("rights", hasSize(profile.getRights().size()))
                ))
        ));
    }

}