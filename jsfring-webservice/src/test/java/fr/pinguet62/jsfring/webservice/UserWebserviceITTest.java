package fr.pinguet62.jsfring.webservice;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.util.MatcherUtils.equalToTruncated;
import static fr.pinguet62.jsfring.webservice.UserWebservice.PATH;
import static java.time.temporal.ChronoUnit.SECONDS;
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
import fr.pinguet62.jsfring.dao.sql.UserDao;
import fr.pinguet62.jsfring.model.sql.QUser;
import fr.pinguet62.jsfring.model.sql.User;
import fr.pinguet62.jsfring.webservice.dto.UserDto;

/** @see UserWebservice */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
// DbUnit
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup(DATASET)
public class UserWebserviceITTest {

    @Inject
    private UserDao userDao;

    @Inject
    private RestTemplate authenticatedRestTemplate;

    /** @see UserWebservice#get(int) */
    @Test
    public void test_get() {
        String email = userDao.findOne(QUser.user.lastConnection.isNotNull()).get().getEmail();

        UserDto actual = authenticatedRestTemplate.getForObject(PATH + "/{email}", UserDto.class, email);

        User expected = userDao.findById(email).get();

        assertThat(actual.getEmail(), is(equalTo(expected.getEmail())));
        assertThat(actual.getLastConnection(), is(equalToTruncated(expected.getLastConnection(), SECONDS)));
    }

    /** @see UserWebservice#list() */
    @Test
    public void test_list() {
        UserDto[] actual = authenticatedRestTemplate.getForObject(PATH, UserDto[].class);

        List<User> expected = userDao.findAll();

        assertThat(actual, arrayWithSize(expected.size()));
    }

}