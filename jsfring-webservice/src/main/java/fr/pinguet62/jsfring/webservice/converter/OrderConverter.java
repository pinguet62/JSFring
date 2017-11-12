package fr.pinguet62.jsfring.webservice.converter;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class OrderConverter implements Converter<String, Function<ComparableExpressionBase<?>, OrderSpecifier<?>>> {

    public static final String ASC = "asc";

    public static final String DESC = "desc";

    @Override
    public Function<ComparableExpressionBase<?>, OrderSpecifier<?>> convert(String order) {
        switch (order) {
            case ASC:
                return x -> x.asc();
            case DESC:
                return x -> x.desc();
            default:
                throw new UnsupportedOperationException("Unknow sort: " + order);
        }
    }

}