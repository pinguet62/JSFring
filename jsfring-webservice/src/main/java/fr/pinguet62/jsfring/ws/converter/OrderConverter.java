package fr.pinguet62.jsfring.ws.converter;

import java.util.function.Function;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.ComparableExpressionBase;

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