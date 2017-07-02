package fr.pinguet62.jsfring.webservice;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.webservice.RightWebservice.PATH;
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
import fr.pinguet62.jsfring.dao.sql.RightDao;
import fr.pinguet62.jsfring.model.sql.Right;
import fr.pinguet62.jsfring.webservice.dto.RightDto;

/** @see RightWebservice */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
// DbUnit
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup(DATASET)
public class RightWebserviceITTest {

    @Inject
    private RightDao rightDao;

    @Inject
    private RestTemplate authenticatedRestTemplate;

    /** @see RightWebservice#get(int) */
    @Test
    public void test_get() {
        String code = rightDao.findAll().get(0).getCode();

        RightDto actual = authenticatedRestTemplate.getForObject(PATH + "/{code}", RightDto.class, code);

        Right expected = rightDao.findById(code).get();

        assertThat(actual.getCode(), is(equalTo(expected.getCode())));
        assertThat(actual.getTitle(), is(equalTo(expected.getTitle())));
    }

    /** @see RightWebservice#list() */
    @Test
    public void test_list() {
        RightDto[] actual = authenticatedRestTemplate.getForObject(PATH, RightDto[].class);

        List<Right> expected = rightDao.findAll();

        assertThat(actual, arrayWithSize(expected.size()));
    }

}