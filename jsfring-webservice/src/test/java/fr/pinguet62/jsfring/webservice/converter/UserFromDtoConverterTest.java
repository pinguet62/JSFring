package fr.pinguet62.jsfring.webservice.converter;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.dao.sql.ProfileDao;
import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.model.sql.User;
import fr.pinguet62.jsfring.webservice.dto.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.random;
import static java.time.LocalDateTime.of;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

/**
 * @see UserFromDtoConverter
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
public class UserFromDtoConverterTest {

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private ProfileDao profileDao;

    /**
     * Check simple fields.
     */
    @Test
    public void test_convert() {
        String email = randomUUID().toString();
        LocalDateTime lastConnection = of(2017, 12, 25, 23, 5, 44, 145);
        boolean active = random() < 0.5;

        UserDto dto = new UserDto();
        dto.setEmail(email);
        dto.setLastConnection(lastConnection);
        dto.setActive(active);

        User entity = conversionService.convert(dto, User.class);

        assertThat(entity, is(not(nullValue())));
        assertThat(entity.getEmail(), is(equalTo(email)));
        assertThat(entity.getLastConnection(), is(equalTo(lastConnection)));
        assertThat(entity.getActive(), is(active));
    }

    @Test
    public void test_convert_profiles() {
        Set<Integer> profileIds = profileDao.findAll().stream().limit(2).map(Profile::getId).collect(toSet());
        UserDto dto = new UserDto();
        dto.setProfiles(profileIds);

        User entity = conversionService.convert(dto, User.class);

        assertThat(entity.getProfiles(), is(not(nullValue())));
        Collection<Integer> ids = entity.getProfiles().stream().map(Profile::getId).collect(toSet());
        assertThat(ids, is(equalTo(profileIds)));
    }

    /**
     * If {@link UserDto#getProfiles()} is {@link Collection#isEmpty() empty}, the converted {@link User#getProfiles()} must
     * also be {@link Collection#isEmpty() empty}.
     */
    @Test
    public void test_convert_profiles_empty() {
        UserDto dto = new UserDto();
        dto.setProfiles(new HashSet<>());

        User entity = conversionService.convert(dto, User.class);

        assertThat(entity.getProfiles(), is(empty()));
    }

    /**
     * If {@link UserDto#getProfiles()} is {@code null}, the converted {@link User#getProfiles()} must also be {@code null}.
     */
    @Test
    public void test_convert_profiles_null() {
        UserDto dto = new UserDto();
        dto.setProfiles(null);

        User entity = conversionService.convert(dto, User.class);

        assertThat(entity.getProfiles(), is(nullValue()));
    }

}