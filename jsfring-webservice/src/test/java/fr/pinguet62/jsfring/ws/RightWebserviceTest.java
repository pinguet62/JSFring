package fr.pinguet62.jsfring.ws;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.jsfring.Config;
import fr.pinguet62.jsfring.dao.RightDao;
import fr.pinguet62.jsfring.model.Right;
import fr.pinguet62.jsfring.ws.dto.RightDto;

/** @see RightWebservice */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = Config.SPRING)
@DatabaseSetup(Config.DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class RightWebserviceTest {

    private static final String BASE_URL = "http://localhost:8080/webservice/rest";

    private static final String PATH = "/right";

    @Inject
    private RightDao rightDao;

    /** @see RightWebservice#get(int) */
    @Test
    public void test_get() {
        String code = rightDao.getAll().get(0).getCode();

        RightDto actual = ClientBuilder.newClient().target(BASE_URL).path(PATH + "/{code}").resolveTemplate("code", code)
                .request().get(RightDto.class);

        Right pojo = rightDao.get(code);
        RightDto expected = new RightDto();
        expected.setCode(pojo.getCode());
        expected.setTitle(pojo.getTitle());

        assertEquals(expected.getCode(), actual.getCode());
        assertEquals(expected.getTitle(), actual.getTitle());
    }

    /** @see RightWebservice#list() */
    @Test
    public void test_list() {
        List<RightDto> actual = ClientBuilder.newClient().target(BASE_URL).path(PATH + "/").request()
                .get(new GenericType<List<RightDto>>() {});

        List<Right> expected = rightDao.getAll();

        assertEquals(expected.size(), actual.size());
    }

}