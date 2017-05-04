package fr.pinguet62.jsfring.webservice.converter;

import static java.util.Collections.singleton;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.core.convert.TypeDescriptor.valueOf;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import fr.pinguet62.jsfring.webservice.dto.PageDto;

/**
 * {@link GenericConverter} from {@link Page} to {@link PageDto}.
 * <p>
 * Each value of {@link Page} are converted using corresponding converter.<br>
 * For example, to convert {@code Page<String>} to {@code Page<Integer>}, the {@code Converter<String, Integer>} must exists.
 */
@Component
public class PageGenericConverter implements GenericConverter {

    private final GenericConversionService conversionService;

    public PageGenericConverter(GenericConversionService conversionService) {
        this.conversionService = requireNonNull(conversionService);
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return convertTyped(source, sourceType, targetType);
    }

    /** Generic method used to cast objects to correct type. */
    @SuppressWarnings("unchecked")
    private <Pojo, Dto> Object convertTyped(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        Page<Pojo> page = (Page<Pojo>) source;

        TypeDescriptor srcType = valueOf((Class<?>) sourceType.getResolvableType().getGenerics()[0].getType());
        TypeDescriptor tgtType = valueOf((Class<?>) targetType.getResolvableType().getGenerics()[0].getType());
        Function<Pojo, Dto> mapper = x -> (Dto) conversionService.convert(x, srcType, tgtType);

        List<Dto> convertedResults = StreamSupport.stream(page.spliterator(), false).map(mapper).collect(toList());
        return new PageDto<Dto>(convertedResults, page.getTotalElements());
    }

    /**
     * Define source and target {@link Class} of {@link Converter}.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return singleton(new ConvertiblePair(Page.class, PageDto.class));
    }

}