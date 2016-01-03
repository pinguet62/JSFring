package fr.pinguet62.jsfring.ws;

import static fr.pinguet62.jsfring.Config.BASE_URL;
import static fr.pinguet62.jsfring.Config.DATASET;
import static fr.pinguet62.jsfring.ws.UserWebservice.PATH;
import static java.util.Calendar.SECOND;
import static org.apache.commons.lang3.time.DateUtils.truncate;
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

import fr.pinguet62.jsfring.dao.UserDao;
import fr.pinguet62.jsfring.model.User;
import fr.pinguet62.jsfring.ws.config.Application;
import fr.pinguet62.jsfring.ws.dto.UserDto;

/** @see UserWebservice */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest
@DatabaseSetup(DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class UserWebserviceTest {

    @Inject
    private UserDao userDao;

    /** @see UserWebservice#get(int) */
    @Test
    public void test_get() {
        String login = userDao.getAll().get(0).getLogin();

        UserDto actual = ClientBuilder.newClient().target(BASE_URL).path(PATH + "/{login}").resolveTemplate("login", login)
                .request().get(UserDto.class);

        User pojo = userDao.get(login);
        UserDto expected = new UserDto();
        expected.setLogin(pojo.getLogin());
        expected.setEmail(pojo.getEmail());
        expected.setLastConnection(pojo.getLastConnection());

        assertEquals(expected.getLogin(), actual.getLogin());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(truncate(expected.getLastConnection(), SECOND), truncate(actual.getLastConnection(), SECOND));
    }

    /** @see UserWebservice#list() */
    @Test
    public void test_list() {
        List<UserDto> actual = ClientBuilder.newClient().target(BASE_URL).path(PATH + "/").request()
                .get(new GenericType<List<UserDto>>() {});

        List<User> expected = userDao.getAll();

        assertEquals(expected.size(), actual.size());
    }

}