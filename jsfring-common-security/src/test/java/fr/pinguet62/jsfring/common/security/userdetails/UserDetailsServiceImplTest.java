package fr.pinguet62.jsfring.common.security.userdetails;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.querydsl.core.BooleanBuilder;
import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.model.sql.User;
import fr.pinguet62.jsfring.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.exparity.hamcrest.date.LocalDateTimeMatchers.within;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

/**
 * @see UserDetailsServiceImpl
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringBootConfig.class)
// DbUnit
@TestExecutionListeners(mergeMode = MERGE_WITH_DEFAULTS, listeners = DbUnitTestExecutionListener.class)
@DatabaseSetup(DATASET)
public class UserDetailsServiceImplTest {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    /**
     * Check that {@link User#lastConnection last connection date} was updated to current time.
     *
     * @see UserDetailsServiceImpl#loadUserByUsername(String)
     */
    @Test
    public void test_ok() {
        String email = userService.findAll(new BooleanBuilder()).blockFirst().getEmail();

        // login
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        assertThat(userDetails, is(not(nullValue())));

        // Test: last connection date
        LocalDateTime lastConnection = userService.get(email).block().getLastConnection();
        assertThat(lastConnection, within(30, SECONDS, now()));
    }

    /**
     * When the {@link User#email} is unknown, the login fails.
     *
     * @see UserDetailsServiceImpl#loadUserByUsername(String)
     */
    @Test
    public void test_unknownLogin() {
        String email = "unknown login";
        assertThat(userService.get(email).block(), is(nullValue()));

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(email));
    }

}