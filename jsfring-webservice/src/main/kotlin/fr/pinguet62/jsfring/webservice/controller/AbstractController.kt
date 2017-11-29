package fr.pinguet62.jsfring.webservice.controller

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.EntityPath
import com.querydsl.core.types.Predicate
import fr.pinguet62.jsfring.common.querydsl.SortConverter
import fr.pinguet62.jsfring.service.AbstractService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort.Direction.fromString
import org.springframework.data.querydsl.QPageRequest
import org.springframework.data.querydsl.QSort
import reactor.core.publisher.Mono
import java.io.Serializable

abstract class AbstractController<T, PK>
        where T : Serializable,
              PK : Serializable {

    /**
     * @param path      The {@link EntityPath} of target {@link Entity}s.
     * @param pageIndex {@link Pageable#getPageNumber()}
     * @param pageSize  {@link Pageable#getPageSize()}
     * @param sortField The {@link ComparableExpressionBase path} on which sort, relative to {@code path}.
     * @param sortOrder {@link Direction#name()}
     * @return {@link AbstractService#findAll(Predicate, Pageable)}
     */
    fun findAll(
            service: AbstractService<T, PK>, path: EntityPath<T>,
            pageIndex: Int, pageSize: Int, sortField: String, sortOrder: String
    ): Mono<Page<T>> {
        var sort: QSort = SortConverter(path).apply(sortField, fromString(sortOrder))
        var predicate: Predicate = BooleanBuilder() // TODO enhancement: webservice filter
        var pageable: Pageable = QPageRequest(pageIndex, pageSize, sort)
        return service.findAll(predicate, pageable)
    }

}