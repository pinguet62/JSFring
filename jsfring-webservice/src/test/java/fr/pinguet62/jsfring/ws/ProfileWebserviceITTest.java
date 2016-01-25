package fr.pinguet62.jsfring.ws;

import static fr.pinguet62.jsfring.test.Config.DATASET;
import static fr.pinguet62.jsfring.ws.Config.BASE_URL;
import static fr.pinguet62.jsfring.ws.ProfileWebservice.PATH;
import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.dao.sql.ProfileDao;
import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.ws.dto.ProfileDto;

/** @see ProfileWebservice */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringBootConfig.class)
@WebIntegrationTest
@DatabaseSetup(DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class ProfileWebserviceITTest {

    @Inject
    private ProfileDao profileDao;

    /** @see ProfileWebservice#get(int) */
    @Test
    public void test_get() {
        int id = profileDao.findAll().get(0).getId();

        ProfileDto actual = ClientBuilder.newClient().target(BASE_URL).path(PATH + "/{id}").resolveTemplate("id", id).request()
                .get(ProfileDto.class);

        Profile pojo = profileDao.findOne(id);
        ProfileDto expected = new ProfileDto();
        expected.setId(pojo.getId());
        expected.setTitle(pojo.getTitle());

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
    }

    /** @see ProfileWebservice#list() */
    @Test
    public void test_list() {
        List<ProfileDto> actual = ClientBuilder.newClient().target(BASE_URL).path(PATH + "/").request()
                .get(new GenericType<List<ProfileDto>>() {});

        List<Profile> expected = profileDao.findAll();

        assertEquals(expected.size(), actual.size());
    }

}