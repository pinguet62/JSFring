package fr.pinguet62.jsfring.gui;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.util.MatcherUtils.sorted;
import static java.lang.Integer.MAX_VALUE;
import static java.util.Comparator.comparing;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.primefaces.model.SortOrder.ASCENDING;
import static org.primefaces.model.SortOrder.DESCENDING;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.primefaces.model.SortOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.querydsl.core.BooleanBuilder;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.model.sql.QRight;
import fr.pinguet62.jsfring.model.sql.Right;
import fr.pinguet62.jsfring.service.AbstractService;
import fr.pinguet62.jsfring.service.RightService;

/**
 * @see AbstractLazyDataModel
 * @see AbstractLazyDataModel#load(int, int, String, org.primefaces.model.SortOrder, java.util.Map)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
@DatabaseSetup(DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class AbstractLazyDataModelTTest {

    private static final String COLUMN = QRight.right_.code.toString().split("\\.")[1];

    private static final SortOrder DEFAULT_ORDER = ASCENDING;

    private static final int FIRST_PAGE = 0;

    private static final Map<String, Object> NO_FILTER = new HashMap<String, Object>();

    private static final int NO_LIMITE = MAX_VALUE;

    private static final String NO_SORT = null;

    // Implementation test
    private final AbstractBean<Right> bean = new AbstractBean<Right>() {
        private static final long serialVersionUID = 1;

        @Override
        public AbstractService<Right, ? extends Serializable> getService() {
            return service;
        }
    };

    // The model to test
    private final AbstractLazyDataModel<Right> model = new AbstractLazyDataModel<Right>(bean);

    @Inject
    private RightService service;

    /** Check that results are filtered. */
    @Test
    public void test_load_filter() {
        // Default
        model.load(FIRST_PAGE, NO_LIMITE, NO_SORT, DEFAULT_ORDER, NO_FILTER);
        assertThat(model.getRowCount(), is(equalTo(service.findAll(new BooleanBuilder()).size())));

        // User input
        final String input = "P";
        final long nb = service.getAll().stream().map(Right::getCode).filter(c -> c.startsWith(input)).count();
        assertThat(nb, is(greaterThan(1L))); // useful test case
        Map<String, Object> filters = new HashMap<>();
        filters.put(COLUMN, input);
        model.load(FIRST_PAGE, NO_LIMITE, NO_SORT, DEFAULT_ORDER, filters);
        assertThat(model.getRowCount(), is(equalTo((int) nb)));
    }

    /**
     * Check that results are ordered.
     *
     * @see SortOrder
     */
    @Test
    public void test_load_order() {
        List<Right> resultsAsc = model.load(FIRST_PAGE, NO_LIMITE, COLUMN, ASCENDING, NO_FILTER);
        Comparator<Right> asc = comparing(Right::getCode);
        assertThat(resultsAsc, is(sorted(asc)));

        List<Right> resultsDesc = model.load(FIRST_PAGE, NO_LIMITE, COLUMN, DESCENDING, NO_FILTER);
        Comparator<Right> desc = asc.reversed();
        assertThat(resultsDesc, is(sorted(desc)));
    }

    /** Check that pagination is correct. */
    @Test
    public void test_load_pagination() {
        List<Right> resultsP1 = model.load(0, 2, NO_SORT, DEFAULT_ORDER, NO_FILTER);
        assertThat(resultsP1, hasSize(2));
        assertThat(model.getRowCount(), is(equalTo(5)));

        List<Right> resultsP2 = model.load(2, 2, NO_SORT, DEFAULT_ORDER, NO_FILTER);
        assertThat(resultsP2, hasSize(2));
        assertThat(model.getRowCount(), is(equalTo(5)));

        List<Right> resultsP3 = model.load(4, 2, NO_SORT, DEFAULT_ORDER, NO_FILTER);
        assertThat(resultsP3, hasSize(1));
        assertThat(model.getRowCount(), is(equalTo(5)));
    }

}