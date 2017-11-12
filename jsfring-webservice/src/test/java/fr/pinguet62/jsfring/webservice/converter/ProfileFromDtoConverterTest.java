package fr.pinguet62.jsfring.webservice.converter;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.dao.sql.RightDao;
import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.model.sql.Right;
import fr.pinguet62.jsfring.webservice.dto.ProfileDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.random;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

/**
 * @see ProfileFromDtoConverter
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
public class ProfileFromDtoConverterTest {

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private RightDao rightDao;

    /**
     * Check simple fields.
     */
    @Test
    public void test_convert() {
        int id = (int) (MAX_VALUE * random());
        String title = randomUUID().toString();

        ProfileDto dto = new ProfileDto();
        dto.setId(id);
        dto.setTitle(title);

        Profile entity = conversionService.convert(dto, Profile.class);

        assertThat(entity, is(not(nullValue())));
        assertThat(entity.getId(), is(equalTo(id)));
        assertThat(entity.getTitle(), is(equalTo(title)));
    }

    @Test
    public void test_convert_profiles() {
        Set<String> rightIds = rightDao.findAll().stream().limit(2).map(Right::getCode).collect(toSet());
        ProfileDto dto = new ProfileDto();
        dto.setRights(rightIds);

        Profile entity = conversionService.convert(dto, Profile.class);

        assertThat(entity.getRights(), is(not(nullValue())));
        Collection<String> ids = entity.getRights().stream().map(Right::getCode).collect(toSet());
        assertThat(ids, is(equalTo(rightIds)));
    }

    /**
     * If {@link ProfileDto#getRights()} is {@link Collection#isEmpty() empty}, the converted {@link Profile#getRights()} must
     * also be {@link Collection#isEmpty() empty}
     */
    @Test
    public void test_convert_rights_empty() {
        ProfileDto dto = new ProfileDto();
        dto.setRights(new HashSet<>());

        Profile entity = conversionService.convert(dto, Profile.class);

        assertThat(entity.getRights(), is(empty()));
    }

    /**
     * If {@link ProfileDto#getRights()} is {@code null}, the converted {@link Profile#getRights()} must also be {@code null}.
     */
    @Test
    public void test_convert_rights_null() {
        ProfileDto dto = new ProfileDto();
        dto.setRights(null);

        Profile entity = conversionService.convert(dto, Profile.class);

        assertThat(entity.getRights(), is(nullValue()));
    }

}