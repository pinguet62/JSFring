package fr.pinguet62.jsfring.ws;

import static org.springframework.core.ResolvableType.forClassWithGenerics;
import static org.springframework.core.convert.TypeDescriptor.valueOf;

import java.io.Serializable;
import java.util.function.Function;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.EntityPath;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.ComparableExpressionBase;

import fr.pinguet62.jsfring.service.AbstractService;
import fr.pinguet62.jsfring.util.reflection.PropertyResolver;
import fr.pinguet62.jsfring.util.spring.GenericTypeDescriptor;

public abstract class AbstractWebservice<T extends Serializable, PK extends Serializable> {

    @Autowired
    private ConversionService conversionService;

    /**
     * @param service The {@link AbstractService}.
     * @param path The {@link EntityPath} of target {@link Entity}s.
     * @param pageIndex The current page.<br>
     *            The first index is 0.
     * @param pageSize The page size.
     * @param sortField The {@link ComparableExpressionBase path} on which sort.
     * @param sortOrder The sort order.
     * @return The {@link Page paginated results}.
     */
    public Page<T> findAll(AbstractService<T, PK> service, EntityPath<T> path, int pageIndex, int pageSize, String sortField,
            String sortOrder) {
        // Pagination
        Pageable pageable = new QPageRequest(pageIndex, pageSize);

        // Sorting
        if (sortField != null) {
            ComparableExpressionBase<?> field = (ComparableExpressionBase<?>) new PropertyResolver(path).apply(sortField);
            @SuppressWarnings("unchecked")
            Function<ComparableExpressionBase<?>, OrderSpecifier<?>> orderer = (Function<ComparableExpressionBase<?>, OrderSpecifier<?>>) conversionService
                    .convert(sortOrder, valueOf(String.class), new GenericTypeDescriptor(
                            forClassWithGenerics(Function.class, ComparableExpressionBase.class, OrderSpecifier.class)));
            OrderSpecifier<?> order = orderer.apply(field);
            pageable.getSort().and(new QSort(order));
        }
        // Filter
        // TODO Evolution: webservice filter
        Predicate predicate = new BooleanBuilder();

        return service.findAll(predicate, pageable);
    }

}