package fr.pinguet62.util.querydsl.converter;

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

/** @see FilterConverter */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
@DatabaseSetup("/dataset.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class })
@Transactional
public class FilterConverterTest {

    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private RightDao rightDao;

    @Autowired
    private UserDao userDao;

    /** @see FilterConverter#apply(java.util.Map) */
    @Test
    public void test_apply() {
        userDao.deleteAll();

        userDao.create(new User("User 12", "Password A", "Email1@domain.com"));
        userDao.create(new User("User 21", "Password B", "Email1@domain.com"));
        userDao.create(new User("User 33", "Password B", "Email1@domain.com"));

        QUser meta = QUser.user;
        FilterConverter filter = new FilterConverter(meta);
        Map<String, Object> filters = new HashMap<>();
        filters.put(meta.login.toString(), "2"); // 1 & 2
        filters.put(meta.password.toString(), "B"); // 2 & 3
        BooleanExpression condition = filter.apply(filters);

        assertEquals(1, userDao
                .find(new JPAQuery().from(meta).where(condition)).size());
    }

    /**
     * The {@link BooleanExpression} value must be {@code null}.
     *
     * @see FilterConverter#apply(java.util.Map)
     */
    @Test
    public void test_apply_empty() {
        FilterConverter filter = new FilterConverter(QUser.user);
        Map<String, Object> filters = new HashMap<>();

        assertNull(filter.apply(filters));
    }

    /**
     * @see FilterConverter#applyCriteria(com.mysema.query.types.expr.SimpleExpression,
     *      String)
     */
    @Test
    public void test_applyLike() {
        BooleanExpression begin = FilterConverter.applyCriteria(
                QRight.right.code, "RIGHT_");
        assertEquals(1,
                rightDao.find(new JPAQuery().from(QRight.right).where(begin))
                .size());

        BooleanExpression end = FilterConverter.applyCriteria(
                QRight.right.code, "_RO");
        assertEquals(3,
                rightDao.find(new JPAQuery().from(QRight.right).where(end))
                .size());
    }

    /**
     * Check that the search is correctly applied into number.
     *
     * @see FilterConverter#applyCriteria(com.mysema.query.types.expr.SimpleExpression,
     *      String)
     */
    @Test
    public void test_applyLike_number() {
        for (int i = 0; i <= 20; i++)
            profileDao.create(new Profile("Title"));
        long nbWith9 = profileDao.getAll().stream().map(Profile::getId)
                .filter(id -> String.valueOf(id).contains("9")).count();
        assertTrue(nbWith9 >= 2);

        BooleanExpression condition = FilterConverter.applyCriteria(
                QProfile.profile.id, "9");
        assertEquals(
                nbWith9,
                profileDao.find(
                        new JPAQuery().from(QProfile.profile).where(condition))
                        .size());
    }

    /**
     * No result found.
     *
     * @see FilterConverter#applyCriteria(com.mysema.query.types.expr.SimpleExpression,
     *      String)
     */
    @Test
    public void test_applyLike_resultNotFound() {
        BooleanExpression condition = FilterConverter.applyCriteria(
                QRight.right.code, "TOTO");
        assertEquals(
                0,
                rightDao.find(
                        new JPAQuery().from(QRight.right).where(condition))
                        .size());
    }

}
