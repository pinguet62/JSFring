package fr.pinguet62.jsfring.webapp.jsf.util;

import org.primefaces.model.SortOrder;
import org.springframework.data.domain.Sort.Direction;

import java.util.function.Function;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

public final class PrimefacesSortOrderConverter implements Function<SortOrder, Direction> {

    /**
     * @return if {@link SortOrder#UNSORTED} then {@code null}
     */
    @Override
    public Direction apply(SortOrder order) {
        switch (order) {
            case ASCENDING:
                return ASC;
            case DESCENDING:
                return DESC;
            case UNSORTED: // "Unsorted Function is unknown in Querydsl
                return null;
            default:
                throw new UnsupportedOperationException("Unknow sort: " + order);
        }
    }

}