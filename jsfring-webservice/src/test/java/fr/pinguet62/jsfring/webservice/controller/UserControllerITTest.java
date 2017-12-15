package fr.pinguet62.jsfring.webservice.controller;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.dao.sql.UserDao;
import fr.pinguet62.jsfring.model.sql.QUser;
import fr.pinguet62.jsfring.model.sql.User;
import fr.pinguet62.jsfring.webservice.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.util.MatcherUtils.equalToTruncated;
import static fr.pinguet62.jsfring.webservice.controller.UserController.PATH;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

/**
 * @see UserController
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
// DbUnit
@TestExecutionListeners(mergeMode = MERGE_WITH_DEFAULTS, listeners = DbUnitTestExecutionListener.class)
@DatabaseSetup(DATASET)
public class UserControllerITTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RestTemplate authenticatedRestTemplate;

    /**
     * @see UserController#get(String)
     */
    @Test
    public void test_get() {
        String email = userDao.findOne(QUser.user.lastConnection.isNotNull()).get().getEmail();

        UserDto actual = authenticatedRestTemplate.getForObject(PATH + "/{email}", UserDto.class, email);

        User expected = userDao.findById(email).get();

        assertThat(actual.getEmail(), is(equalTo(expected.getEmail())));
        assertThat(actual.getLastConnection(), is(equalToTruncated(expected.getLastConnection(), SECONDS)));
    }

    /**
     * @see UserController#list()
     */
    @Test
    public void test_list() {
        UserDto[] actual = authenticatedRestTemplate.getForObject(PATH, UserDto[].class);

        List<User> expected = userDao.findAll();

        assertThat(actual, arrayWithSize(expected.size()));
    }

}