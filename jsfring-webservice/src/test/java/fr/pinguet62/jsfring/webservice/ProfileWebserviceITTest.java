package fr.pinguet62.jsfring.webservice;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.webservice.ProfileWebservice.PATH;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.web.client.RestTemplate;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.dao.sql.ProfileDao;
import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.webservice.dto.ProfileDto;

/** @see ProfileWebservice */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
// DbUnit
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup(DATASET)
public class ProfileWebserviceITTest {

    @Inject
    private ProfileDao profileDao;

    @Inject
    private RestTemplate authenticatedRestTemplate;

    /** @see ProfileWebservice#get(int) */
    @Test
    public void test_get() {
        int id = profileDao.findAll().get(0).getId();

        ProfileDto actual = authenticatedRestTemplate.getForObject(PATH + "/{id}", ProfileDto.class, id);

        Profile expected = profileDao.findById(id).get();

        assertThat(actual.getId(), is(equalTo(expected.getId())));
        assertThat(actual.getTitle(), is(equalTo(expected.getTitle())));
    }

    /** @see ProfileWebservice#list() */
    @Test
    public void test_list() {
        ProfileDto[] actual = authenticatedRestTemplate.getForObject(PATH, ProfileDto[].class);

        List<Profile> expected = profileDao.findAll();

        assertThat(actual, arrayWithSize(expected.size()));
    }

}