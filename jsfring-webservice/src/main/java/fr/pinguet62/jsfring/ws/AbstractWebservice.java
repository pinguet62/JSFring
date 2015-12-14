package fr.pinguet62.jsfring.ws;

import static org.springframework.core.ResolvableType.forClassWithGenerics;
import static org.springframework.core.convert.TypeDescriptor.valueOf;

import java.io.Serializable;
import java.util.function.Function;

import javax.inject.Inject;

import org.springframework.core.convert.ConversionService;

import com.mysema.query.SearchResults;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.ComparableExpressionBase;
import com.mysema.query.types.path.EntityPathBase;

import fr.pinguet62.jsfring.service.AbstractService;
import fr.pinguet62.jsfring.util.reflection.PropertyResolver;
import fr.pinguet62.jsfring.util.spring.GenericTypeDescriptor;

public abstract class AbstractWebservice<T extends Serializable, PK extends Serializable> {

    @Inject
    private ConversionService conversionService;

    private AbstractService<T, PK> service;

    public SearchResults<T> find(EntityPathBase<T> base, int page, int pageSize, String sortField, String sortOrder) {
        JPAQuery query = new JPAQuery().from(base);

        // Pagination
        int first = page * pageSize;
        query.offset(first);
        query.limit(pageSize);

        // Sorting
        if (sortField != null) {
            ComparableExpressionBase<?> field = (ComparableExpressionBase<?>) new PropertyResolver(base).apply(sortField);
            @SuppressWarnings("unchecked")
            Function<ComparableExpressionBase<?>, OrderSpecifier<?>> orderer = (Function<ComparableExpressionBase<?>, OrderSpecifier<?>>) conversionService
                    .convert(sortOrder, valueOf(String.class), new GenericTypeDescriptor(
                            forClassWithGenerics(Function.class, ComparableExpressionBase.class, OrderSpecifier.class)));
            OrderSpecifier<?> order = orderer.apply(field);
            query.orderBy(order);
        }

        // Filtering

        return service.findPanginated(query);
    }

}