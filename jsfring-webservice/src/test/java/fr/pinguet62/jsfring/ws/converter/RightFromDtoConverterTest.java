package fr.pinguet62.jsfring.ws.converter;

import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.model.sql.Right;
import fr.pinguet62.jsfring.ws.dto.RightDto;

/** @see RightFromDtoConverter */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SpringBootConfig.class)
public class RightFromDtoConverterTest {

    @Inject
    private ConversionService conversionService;

    /** Check simple fields. */
    @Test
    public void test_convert() {
        String code = randomUUID().toString();
        String title = randomUUID().toString();

        RightDto dto = new RightDto();
        dto.setCode(code);
        dto.setTitle(title);

        Right entity = conversionService.convert(dto, Right.class);

        assertThat(entity, is(not(nullValue())));
        assertThat(entity.getCode(), is(equalTo(code)));
        assertThat(entity.getTitle(), is(equalTo(title)));
    }

}