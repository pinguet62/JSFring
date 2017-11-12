package fr.pinguet62.jsfring.webservice;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import fr.pinguet62.jsfring.common.reflection.PropertyResolver;
import fr.pinguet62.jsfring.common.spring.GenericTypeDescriptor;
import fr.pinguet62.jsfring.service.AbstractService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;
import static org.springframework.core.ResolvableType.forClassWithGenerics;
import static org.springframework.core.convert.TypeDescriptor.valueOf;

public abstract class AbstractWebservice<T extends Serializable, PK extends Serializable> {

    private final ConversionService conversionService;

    public AbstractWebservice(ConversionService conversionService) {
        this.conversionService = requireNonNull(conversionService);
    }

    /**
     * @param service   The {@link AbstractService}.
     * @param path      The {@link EntityPath} of target {@link Entity}s.
     * @param pageIndex The current page.<br>
     *                  The first index is 0.
     * @param pageSize  The page size.
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