package fr.pinguet62.jsfring.webservice.controller;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import fr.pinguet62.jsfring.common.querydsl.SortConverter;
import fr.pinguet62.jsfring.service.AbstractService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;
import reactor.core.publisher.Mono;

import javax.persistence.Entity;
import java.io.Serializable;

import static org.springframework.data.domain.Sort.Direction.fromString;

public abstract class AbstractController<T extends Serializable, PK extends Serializable> {

    /**
     * @param path      The {@link EntityPath} of target {@link Entity}s.
     * @param pageIndex {@link Pageable#getPageNumber()}
     * @param pageSize  {@link Pageable#getPageSize()}
     * @param sortField The {@link ComparableExpressionBase path} on which sort, relative to {@code path}.
     * @param sortOrder {@link Direction#name()}
     * @return {@link AbstractService#findAll(Predicate, Pageable)}
     */
    public Mono<Page<T>> findAll(
            AbstractService<T, PK> service, EntityPath<T> path,
            int pageIndex, int pageSize, String sortField, String sortOrder
    ) {
        QSort sort = new SortConverter(path).apply(sortField, fromString(sortOrder));
        Predicate predicate = new BooleanBuilder(); // TODO enhancement: webservice filter
        Pageable pageable = new QPageRequest(pageIndex, pageSize, sort);
        return service.findAll(predicate, pageable);
    }

}