package fr.pinguet62.jsfring.webservice;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.dao.sql.ProfileDao;
import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.webservice.dto.ProfileDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.isJson;
import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.webservice.ProfileWebservice.PATH;
import static java.util.Comparator.comparing;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

/**
 * @see ProfileWebservice
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
// DbUnit
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup(DATASET)
public class ProfileWebserviceITTest {

    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private RestTemplate authenticatedRestTemplate;

    /**
     * @see ProfileWebservice#get(int)
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
     * @see ProfileWebservice#list()
     */
    @Test
    public void test_list() {
        ProfileDto[] actual = authenticatedRestTemplate.getForObject(PATH, ProfileDto[].class);

        List<Profile> expected = profileDao.findAll();

        assertThat(actual, arrayWithSize(expected.size()));
    }

    /**
     * @see ProfileWebservice#find(int, int, String, String)
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