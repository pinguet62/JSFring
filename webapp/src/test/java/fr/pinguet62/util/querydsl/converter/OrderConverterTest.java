package fr.pinguet62.util.querydsl.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
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
import com.mysema.query.types.OrderSpecifier;

import fr.pinguet62.dictionary.dao.RightDao;
import fr.pinguet62.dictionary.model.QRight;
import fr.pinguet62.dictionary.model.Right;
import fr.pinguet62.util.querydsl.converter.OrderConverter;

/** @see OrderConverter */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
@DatabaseSetup("/dataset.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@Transactional
public class OrderConverterTest {

    @Autowired
    RightDao rightDao;

    /** @see OrderConverter#apply(String, org.primefaces.model.SortOrder) */
    @Test
    public void test_apply() {
        QRight meta = QRight.right;

        // Ascending
        List<String> codes = rightDao.getAll().stream().map(Right::getCode)
                .sorted().collect(Collectors.toList());
        JPAQuery queryAsc = new JPAQuery().from(meta).orderBy(
                new OrderConverter(meta).apply("code", SortOrder.ASCENDING));
        assertEquals(codes, rightDao.find(queryAsc).stream()
                .map(Right::getCode).collect(Collectors.toList()));

        // Descending
        Collections.reverse(codes);
        JPAQuery queryDesc = new JPAQuery().from(meta).orderBy(
                new OrderConverter(meta).apply("code", SortOrder.DESCENDING));
        assertEquals(
                codes,
                rightDao.find(queryDesc).stream().map(Right::getCode)
                        .collect(Collectors.toList()));
    }

    /**
     * The {@link OrderSpecifier} must be null.
     *
     * @see OrderConverter#apply(String, org.primefaces.model.SortOrder)
     * @see SortOrder#UNSORTED
     */
    @Test
    public void test_apply_unsorded() {
        assertNull(new OrderConverter(QRight.right).apply("code", SortOrder.UNSORTED));
    }

}
