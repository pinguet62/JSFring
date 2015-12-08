package fr.pinguet62.jsfring.ws;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.jsfring.Config;
import fr.pinguet62.jsfring.dao.ProfileDao;
import fr.pinguet62.jsfring.model.Profile;
import fr.pinguet62.jsfring.ws.dto.ProfileDto;

/** @see ProfileWebservice */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = Config.SPRING)
@DatabaseSetup(Config.DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class ProfileWebserviceTest {

    private static final String BASE_URL = "http://localhost:8080/webservice/rest";

    private static final String PATH = "/profile";

    @Inject
    private ProfileDao profileDao;

    /** @see ProfileWebservice#get(int) */
    @Test
    public void test_get() {
        int id = profileDao.getAll().get(0).getId();

        ProfileDto actual = ClientBuilder.newClient().target(BASE_URL).path(PATH + "/{id}").resolveTemplate("id", id).request()
                .get(ProfileDto.class);

        Profile pojo = profileDao.get(id);
        ProfileDto expected = new ProfileDto();
        expected.setId(pojo.getId());
        expected.setTitle(pojo.getTitle());

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
    }

}