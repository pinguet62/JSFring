package fr.pinguet62.jsfring.dao.nosql;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.pinguet62.jsfring.SpringBootConfig;

/** @see UserDao */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringBootConfig.class)
public class UserDaoTest {

    @Inject
    private UserDao dao;

    @Test
    public void test_list() {
        assertThat(dao.findAll(), is(not(empty())));
    }

}