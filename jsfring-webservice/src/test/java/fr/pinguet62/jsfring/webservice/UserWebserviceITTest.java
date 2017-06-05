package fr.pinguet62.jsfring.webservice;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.webservice.UserWebservice.PATH;
import static java.util.Calendar.SECOND;
import static org.apache.commons.lang3.time.DateUtils.truncate;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
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
import fr.pinguet62.jsfring.dao.sql.UserDao;
import fr.pinguet62.jsfring.model.sql.User;
import fr.pinguet62.jsfring.webservice.UserWebservice;
import fr.pinguet62.jsfring.webservice.dto.UserDto;

/** @see UserWebservice */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
// DbUnit
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup(DATASET)
public class UserWebserviceITTest {

    @Inject
    private WebTarget restClient;

    @Inject
    private UserDao userDao;

    /** @see UserWebservice#get(int) */
    @Test
    public void test_get() {
        String email = userDao.findAll().get(0).getEmail();

        // @formatter:off
        UserDto actual =
                restClient
                    .path(PATH)
                    .path("/{email}").resolveTemplate("email", email)
                .request()
                .get(UserDto.class);
        // @formatter:on

        User pojo = userDao.findById(email).get();
        UserDto expected = new UserDto();
        expected.setEmail(pojo.getEmail());
        expected.setLastConnection(pojo.getLastConnection());

        assertThat(actual.getEmail(), is(equalTo(expected.getEmail())));
        assertThat(truncate(actual.getLastConnection(), SECOND), is(equalTo(truncate(expected.getLastConnection(), SECOND))));
    }

    /** @see UserWebservice#list() */
    @Test
    public void test_list() {
        // @formatter:off
        List<UserDto> actual =
                restClient
                    .path(PATH)
                    .path("/")
                .request()
                .get(new GenericType<List<UserDto>>() {});
        // @formatter:on

        List<User> expected = userDao.findAll();

        assertThat(actual, hasSize(expected.size()));
    }

}