package fr.pinguet62.jsfring.gui;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.util.MatcherUtils.isSorted;
import static java.lang.Integer.MAX_VALUE;
import static java.util.Comparator.comparing;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.primefaces.model.SortOrder.ASCENDING;
import static org.primefaces.model.SortOrder.DESCENDING;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.primefaces.model.SortOrder;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.mysema.query.BooleanBuilder;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.model.sql.QRight;
import fr.pinguet62.jsfring.model.sql.Right;
import fr.pinguet62.jsfring.service.AbstractService;
import fr.pinguet62.jsfring.service.RightService;

/**
 * @see AbstractLazyDataModel
 * @see AbstractLazyDataModel#load(int, int, String,
 *      org.primefaces.model.SortOrder, java.util.Map)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringBootConfig.class)
@DatabaseSetup(DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class AbstractLazyDataModelTest {

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
        assertEquals(service.findAll(new BooleanBuilder()).size(), model.getRowCount());

        // User input
        final String input = "P";
        final long nb = service.getAll().stream().map(Right::getCode).filter(c -> c.startsWith(input)).count();
        assertTrue(nb > 1); // useful test case
        Map<String, Object> filters = new HashMap<>();
        filters.put(COLUMN, input);
        model.load(FIRST_PAGE, NO_LIMITE, NO_SORT, DEFAULT_ORDER, filters);
        assertEquals(nb, model.getRowCount());
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
        assertThat(resultsAsc, isSorted(asc));

        List<Right> resultsDesc = model.load(FIRST_PAGE, NO_LIMITE, COLUMN, DESCENDING, NO_FILTER);
        Comparator<Right> desc = asc.reversed();
        assertThat(resultsDesc, isSorted(desc));
    }

    /** Check that pagination is correct. */
    @Test
    public void test_load_pagination() {
        List<Right> resultsP1 = model.load(0, 2, NO_SORT, DEFAULT_ORDER, NO_FILTER);
        assertEquals(2, resultsP1.size());
        assertEquals(5, model.getRowCount());

        List<Right> resultsP2 = model.load(2, 2, NO_SORT, DEFAULT_ORDER, NO_FILTER);
        assertEquals(2, resultsP2.size());
        assertEquals(5, model.getRowCount());

        List<Right> resultsP3 = model.load(4, 2, NO_SORT, DEFAULT_ORDER, NO_FILTER);
        assertEquals(1, resultsP3.size());
        assertEquals(5, model.getRowCount());
    }

}