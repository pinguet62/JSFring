package fr.pinguet62.util.querydsl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;

import fr.pinguet62.dictionary.dao.ProfileDao;
import fr.pinguet62.dictionary.dao.RightDao;
import fr.pinguet62.dictionary.dao.UserDao;
import fr.pinguet62.dictionary.model.Profile;
import fr.pinguet62.dictionary.model.QProfile;
import fr.pinguet62.dictionary.model.QRight;
import fr.pinguet62.dictionary.model.QUser;
import fr.pinguet62.dictionary.model.User;

/** @see Filter */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
@DatabaseSetup("/dataset.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class })
@Transactional
public class FilterTest {

    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private RightDao rightDao;

    @Autowired
    private UserDao userDao;

    /** @see Filter#apply(java.util.Map) */
    @Test
    public void test_apply() {
        userDao.deleteAll();

        userDao.create(new User("User 1", "Password 1"));
        userDao.create(new User("User 2", "Password 2"));
        userDao.create(new User("User 3", "Password 3"));

        QUser meta = QUser.user;
        Filter filter = new Filter(meta);
        Map<String, Object> filters = new HashMap<>();
        filters.put(meta.login.toString(), "ser");
        filters.put(meta.password.toString(), "2");
        BooleanExpression condition = filter.apply(filters);

        assertEquals(1, userDao
                .find(new JPAQuery().from(meta).where(condition)).size());
    }

    /**
     * The {@link BooleanExpression} value must be {@code null}.
     *
     * @see Filter#apply(java.util.Map)
     */
    @Test
    public void test_apply_empty() {
        Filter filter = new Filter(QUser.user);
        Map<String, Object> filters = new HashMap<>();

        assertNull(filter.apply(filters));
    }

    /**
     * @see Filter#applyLike(com.mysema.query.types.expr.SimpleExpression,
     *      String)
     */
    @Test
    public void test_applyLike() {
        BooleanExpression begin = Filter.applyLike(QRight.right.code, "RIGHT_");
        assertEquals(1,
                rightDao.find(new JPAQuery().from(QRight.right).where(begin))
                .size());

        BooleanExpression end = Filter.applyLike(QRight.right.code, "_RO");
        assertEquals(3,
                rightDao.find(new JPAQuery().from(QRight.right).where(end))
                .size());
    }

    /**
     * No result found.
     *
     * @see Filter#applyLike(com.mysema.query.types.expr.SimpleExpression,
     *      String)
     */
    @Test
    public void test_applyLike_notFound() {
        BooleanExpression condition = Filter.applyLike(QRight.right.code,
                "TOTO");
        assertEquals(
                0,
                rightDao.find(
                        new JPAQuery().from(QRight.right).where(condition))
                        .size());
    }

    /**
     * Check that the search is correctly applied into number.
     *
     * @see Filter#applyLike(com.mysema.query.types.expr.SimpleExpression,
     *      String)
     */
    @Test
    public void test_applyLike_number() {
        for (int i = 0; i <= 20; i++)
            profileDao.create(new Profile("Title"));
        long nbWith9 = profileDao.getAll().stream().map(Profile::getId)
                .filter(id -> String.valueOf(id).contains("9")).count();
        assertTrue(nbWith9 >= 2);

        BooleanExpression condition = Filter
                .applyLike(QProfile.profile.id, "9");
        assertEquals(
                nbWith9,
                profileDao.find(
                        new JPAQuery().from(QProfile.profile).where(condition))
                        .size());
    }

}
