package fr.pinguet62.jsfring.ws;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.ws.Config.BASE_URL;
import static fr.pinguet62.jsfring.ws.UserWebservice.PATH;
import static fr.pinguet62.jsfring.ws.config.JerseyConfig.CONTEXT_PATH;
import static fr.pinguet62.jsfring.ws.config.Oauth2Helper.HEADER_AUTHORIZATION;
import static java.util.Calendar.SECOND;
import static javax.ws.rs.client.ClientBuilder.newClient;
import static org.apache.commons.lang3.time.DateUtils.truncate;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.GenericType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.dao.sql.UserDao;
import fr.pinguet62.jsfring.model.sql.User;
import fr.pinguet62.jsfring.ws.config.Oauth2Helper;
import fr.pinguet62.jsfring.ws.dto.UserDto;

/** @see UserWebservice */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@DatabaseSetup(DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class UserWebserviceITTest {

    @Inject
    private Oauth2Helper helper;

    @Inject
    private UserDao userDao;

    /** @see UserWebservice#get(int) */
    @Test
    public void test_get() {
        String login = userDao.findAll().get(0).getLogin();

        // @formatter:off
        UserDto actual = newClient()
            .target(BASE_URL)
                .path(CONTEXT_PATH)
                .path(PATH)
                .path("/{login}").resolveTemplate("login", login)
            .request()
                .header(HEADER_AUTHORIZATION, helper.getAuthorization())
            .get(UserDto.class);
        // @formatter:on

        User pojo = userDao.findOne(login);
        UserDto expected = new UserDto();
        expected.setLogin(pojo.getLogin());
        expected.setEmail(pojo.getEmail());
        expected.setLastConnection(pojo.getLastConnection());

        assertThat(actual.getLogin(), is(equalTo(expected.getLogin())));
        assertThat(actual.getEmail(), is(equalTo(expected.getEmail())));
        assertThat(truncate(actual.getLastConnection(), SECOND), is(equalTo(truncate(expected.getLastConnection(), SECOND))));
    }

    /** @see UserWebservice#list() */
    @Test
    public void test_list() {
        // @formatter:off
        List<UserDto> actual = newClient()
            .target(BASE_URL)
                .path(CONTEXT_PATH)
                .path(PATH)
                .path("/")
            .request()
                .header(HEADER_AUTHORIZATION, helper.getAuthorization())
            .get(new GenericType<List<UserDto>>() {});
        // @formatter:on

        List<User> expected = userDao.findAll();

        assertThat(actual, hasSize(expected.size()));
    }

}