package fr.pinguet62.jsfring.ws;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.ws.Config.BASE_URL;
import static fr.pinguet62.jsfring.ws.ProfileWebservice.PATH;
import static fr.pinguet62.jsfring.ws.config.JerseyConfig.CONTEXT_PATH;
import static fr.pinguet62.jsfring.ws.config.Oauth2Helper.HEADER_AUTHORIZATION;
import static javax.ws.rs.client.ClientBuilder.newClient;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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
import fr.pinguet62.jsfring.dao.sql.ProfileDao;
import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.ws.config.Oauth2Helper;
import fr.pinguet62.jsfring.ws.dto.ProfileDto;

/** @see ProfileWebservice */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
// DbUnit
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup(DATASET)
public class ProfileWebserviceITTest {

    @Inject
    private Oauth2Helper helper;

    @Inject
    private ProfileDao profileDao;

    /** @see ProfileWebservice#get(int) */
    @Test
    public void test_get() {
        int id = profileDao.findAll().get(0).getId();

        // @formatter:off
        ProfileDto actual = newClient()
            .target(BASE_URL)
                .path(CONTEXT_PATH)
                .path(PATH)
                .path("/{id}").resolveTemplate("id", id)
            .request()
                .header(HEADER_AUTHORIZATION, helper.getAuthorization())
            .get(ProfileDto.class);
        // @formatter:on

        Profile pojo = profileDao.findOne(id);
        ProfileDto expected = new ProfileDto();
        expected.setId(pojo.getId());
        expected.setTitle(pojo.getTitle());

        assertThat(actual.getId(), is(equalTo(expected.getId())));
        assertThat(actual.getTitle(), is(equalTo(expected.getTitle())));
    }

    /** @see ProfileWebservice#list() */
    @Test
    public void test_list() {
        // @formatter:off
        List<ProfileDto> actual = newClient()
            .target(BASE_URL)
                .path(CONTEXT_PATH)
                .path(PATH)
                .path("/")
            .request()
                .header(HEADER_AUTHORIZATION, helper.getAuthorization())
            .get(new GenericType<List<ProfileDto>>() {});
        // @formatter:on

        List<Profile> expected = profileDao.findAll();

        assertThat(actual, hasSize(expected.size()));
    }

}