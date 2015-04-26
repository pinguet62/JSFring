package fr.pinguet62.jsfring.gui;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.mysema.query.SearchResults;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

import fr.pinguet62.jsfring.gui.util.querydsl.converter.FilterConverter;
import fr.pinguet62.jsfring.gui.util.querydsl.converter.OrderConverter;
import fr.pinguet62.jsfring.model.QUser;

public class AbstractLazyDataModel<T> extends LazyDataModel<T> {

    private static final long serialVersionUID = 1;

    private final AbstractManagedBean<T> managedBean;

    public AbstractLazyDataModel(AbstractManagedBean<T> managedBean) {
        this.managedBean = managedBean;
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField,
            SortOrder sortOrder, Map<String, Object> filters) {
        JPAQuery query = managedBean.getQuery();

        // Pagination
        query.offset(first);
        query.limit(pageSize);
        // Order
        if (sortField != null) {
            OrderSpecifier<?> order = new OrderConverter(QUser.user).apply(
                    sortField, sortOrder);
            if (order != null)
                query.orderBy(order);
        }
        // Filter
        BooleanExpression condition = new FilterConverter(QUser.user)
                .apply(filters);
        if (condition != null)
            query.where(condition);

        SearchResults<T> results = managedBean.getService().findPanginated(
                query);

        setRowCount((int) results.getTotal());
        return results.getResults();
    }

}