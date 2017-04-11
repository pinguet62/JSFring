package fr.pinguet62.jsfring.ws;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.ws.RightWebservice.PATH;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;
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
import fr.pinguet62.jsfring.ws.dto.RightDto;

/** @see RightWebservice */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
// DbUnit
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup(DATASET)
public class RightWebserviceITTest {

    @Inject
    private WebTarget restClient;

    @Inject
    private RightDao rightDao;

    /** @see RightWebservice#get(int) */
    @Test
    public void test_get() {
        String code = rightDao.findAll().get(0).getCode();

        // @formatter:off
        RightDto actual =
                restClient
                    .path(PATH)
                    .path("/{code}").resolveTemplate("code", code)
                .request()
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
        List<RightDto> actual =
                restClient
                    .path(PATH)
                .request()
                .get(new GenericType<List<RightDto>>() {});
        // @formatter:on

        List<Right> expected = rightDao.findAll();

        assertThat(actual.size(), is(equalTo(expected.size())));
    }

}