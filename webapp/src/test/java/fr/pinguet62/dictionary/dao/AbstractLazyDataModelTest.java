package fr.pinguet62.dictionary.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
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
import com.mysema.query.support.Expressions;
import com.mysema.query.types.expr.ComparableExpressionBase;

import fr.pinguet62.dictionary.model.Profile;
import fr.pinguet62.dictionary.model.QProfile;
import fr.pinguet62.dictionary.model.QRight;

/**
 * Test the pagination, the sort according to a field, and the filter according
 * to many fields.
 *
 * @see LazyDataModel#load(int, int, String, SortOrder, Map)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
@DatabaseSetup("/dataset.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@Transactional
public class AbstractLazyDataModelTest {

    @Autowired
    ProfileDao profileDao;

    @Autowired
    RightDao rightDao;

    /**
     * @see LazyDataModel#load(int, int, String, SortOrder, Map)
     */
    @Test
    public void LazyDataModelFilters() {
        // prepare
        for (int i = 2; i < 25; i++)
            profileDao.create(new Profile("title " + i));

        // Convert to Querydsl
        ComparableExpressionBase<?> path = Expressions.comparableTemplate(
                Comparable.class, "id");
        assertEquals(QRight.right.code.toString(), path.toString());
        assertEquals("code", QRight.right.code.toString());

        // Build query
        QProfile profile = QProfile.profile;
        JPAQuery query = new JPAQuery().from(profile).orderBy(path.asc());

        List<Profile> profiles = profileDao.find(query);
        assertEquals(1, profiles.size());
    }

}