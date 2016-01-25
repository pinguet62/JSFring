package fr.pinguet62.jsfring.ws;

import static fr.pinguet62.jsfring.test.Config.DATASET;
import static fr.pinguet62.jsfring.ws.Config.BASE_URL;
import static fr.pinguet62.jsfring.ws.RightWebservice.PATH;
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
import fr.pinguet62.jsfring.dao.sql.RightDao;
import fr.pinguet62.jsfring.model.sql.Right;
import fr.pinguet62.jsfring.ws.dto.RightDto;

/** @see RightWebservice */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringBootConfig.class)
@WebIntegrationTest
@DatabaseSetup(DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class RightWebserviceITTest {

    @Inject
    private RightDao rightDao;

    /** @see RightWebservice#get(int) */
    @Test
    public void test_get() {
        String code = rightDao.findAll().get(0).getCode();

        RightDto actual = ClientBuilder.newClient().target(BASE_URL).path(PATH + "/{code}").resolveTemplate("code", code)
                .request().get(RightDto.class);

        Right pojo = rightDao.findOne(code);
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

        List<Right> expected = rightDao.findAll();

        assertEquals(expected.size(), actual.size());
    }

}