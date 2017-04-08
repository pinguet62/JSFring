package fr.pinguet62.jsfring.ws;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.ws.Config.BASE_URL;
import static fr.pinguet62.jsfring.ws.RightWebservice.PATH;
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
import fr.pinguet62.jsfring.dao.sql.RightDao;
import fr.pinguet62.jsfring.model.sql.Right;
import fr.pinguet62.jsfring.ws.config.Oauth2Helper;
import fr.pinguet62.jsfring.ws.dto.RightDto;

/** @see RightWebservice */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
// DbUnit
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup(DATASET)
public class RightWebserviceITTest {

    @Inject
    private Oauth2Helper helper;

    @Inject
    private RightDao rightDao;

    /** @see RightWebservice#get(int) */
    @Test
    public void test_get() {
        String code = rightDao.findAll().get(0).getCode();

        // @formatter:off
        RightDto actual = newClient()
            .target(BASE_URL)
                .path(CONTEXT_PATH)
                .path(PATH)
                .path("/{code}").resolveTemplate("code", code)
            .request()
                .header(HEADER_AUTHORIZATION, helper.getAuthorization())
            .get(RightDto.class);
        // @formatter:on

        Right pojo = rightDao.findOne(code);
        RightDto expected = new RightDto();
        expected.setCode(pojo.getCode());
        expected.setTitle(pojo.getTitle());

        assertThat(actual.getCode(), is(equalTo(expected.getCode())));
        assertThat(actual.getTitle(), is(equalTo(expected.getTitle())));
    }

    /** @see RightWebservice#list() */
    @Test
    public void test_list() {
        // @formatter:off
        List<RightDto> actual = newClient()
            .target(BASE_URL)
                .path(CONTEXT_PATH)
                .path(PATH)
                .path("/")
            .request()
                .header(HEADER_AUTHORIZATION, helper.getAuthorization())
            .get(new GenericType<List<RightDto>>() {});
        // @formatter:on

        List<Right> expected = rightDao.findAll();

        assertThat(actual, hasSize(expected.size()));
    }

}