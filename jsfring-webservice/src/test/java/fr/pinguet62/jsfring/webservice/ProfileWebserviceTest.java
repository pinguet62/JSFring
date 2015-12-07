package fr.pinguet62.jsfring.webservice;

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
import fr.pinguet62.jsfring.model.Profile;
import fr.pinguet62.jsfring.service.ProfileService;
import fr.pinguet62.jsfring.ws.ProfileWebservice;
import fr.pinguet62.jsfring.ws.dto.ProfileDto;

/** @see ProfileWebservice */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = Config.SPRING)
@DatabaseSetup(Config.DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public final class ProfileWebserviceTest {

    private static final String BASE_URL = "http://localhost:8080/webservice/rest";

    @Inject
    private ProfileService profileService;

    /** @see ProfileWebservice#get(int) */
    @Test
    public void test_get() {
        int id = profileService.getAll().get(0).getId();

        ProfileDto dto = ClientBuilder.newClient().target(BASE_URL).path("/{id}").resolveTemplate("id", id).request()
                .get(ProfileDto.class);

        Profile pojo = profileService.get(id);
        ProfileDto expected = new ProfileDto();
        expected.setTitle(pojo.getTitle());

        assertEquals(expected, dto);
    }
}