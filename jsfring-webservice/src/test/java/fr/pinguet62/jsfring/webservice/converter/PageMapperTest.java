//package fr.pinguet62.jsfring.webservice.converter;
//
//import fr.pinguet62.jsfring.webservice.dto.PageDto;
//import org.junit.Test;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//
//import java.util.function.Function;
//
//import static fr.pinguet62.jsfring.webservice.converter.PageMapperKt.toDto;
//import static java.util.Arrays.asList;
//import static org.hamcrest.Matchers.*;
//import static org.junit.Assert.assertThat;
//import static org.springframework.data.domain.Pageable.unpaged;
//
///**
// * @see PageMapper
// */
//public class PageMapperTest {
//
//    @Test
//    public void test() {
//        Page<String> source = new PageImpl<>(asList("1", "2", "3"), unpaged(), 10L);
//        Function<String, Integer> elementMapper = Integer::parseInt;
//
//        PageDto<Integer> target = toDto(source, elementMapper);
//
//        assertThat(target, is(not(nullValue())));
//        assertThat(target.getTotal(), is(10L));
//        assertThat(target.getResults(), is(asList(1, 2, 3)));
//    }
//
//    @Test
//    public void test_null() {
//        Page<String> source = null;
//        Function<String, Integer> elementMapper = Integer::parseInt;
//
//        PageDto<Integer> target = toDto(source, elementMapper);
//
//        assertThat(target, is(nullValue()));
//    }
//
//}