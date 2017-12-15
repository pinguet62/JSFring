package fr.pinguet62.jsfring.batch.common;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.dao.sql.ProfileDao;
import fr.pinguet62.jsfring.model.sql.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.Entity;
import java.util.List;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

/**
 * @see CrudRepositoryItemWriter
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SpringBootConfig.class})
// DbUnit
@TestExecutionListeners(mergeMode = MERGE_WITH_DEFAULTS, listeners = DbUnitTestExecutionListener.class)
@DatabaseSetup(DATASET)
public class CrudRepositoryItemWriterTest {

    @Autowired
    private ProfileDao dao;

    private CrudRepositoryItemWriter<Profile> writer;

    @BeforeEach
    public void initItemWriter() {
        writer = new CrudRepositoryItemWriter<>(dao);
    }

    /**
     * Not existing {@link Entity} must be inserted.
     *
     * @see CrudRepositoryItemWriter#write(List)
     */
    @Test
    public void test_create() {
        long initialCount = dao.count();

        Profile entity = new Profile();
        entity.setTitle("new title");

        List<Profile> items = asList(entity);
        writer.write(items);

        assertThat(dao.count(), is(equalTo(initialCount + items.size())));
    }

    /**
     * Check the mass treatment.
     *
     * @see CrudRepositoryItemWriter#write(List)
     */
    @Test
    public void test_multiple() {
        long initialCount = dao.count();

        final int nb = 5;
        List<Profile> items = range(0, nb)
                .mapToObj(i -> "new title " + i)
                .map(title -> Profile.builder().title(title).build())
                .collect(toList());

        writer.write(items);

        assertThat(dao.count(), is(equalTo(initialCount + items.size())));
    }

    /**
     * Existing {@link Entity} must be updated.
     *
     * @see CrudRepositoryItemWriter#write(List)
     */
    @Test
    public void test_update() {
        long initialCount = dao.count();

        final String newValue = "new title";
        List<Profile> entities = dao.findAll();
        Profile entity = entities.get(0);
        assertThat(entity.getTitle(), is(not(equalTo(newValue))));
        entity.setTitle(newValue);

        List<Profile> items = asList(entity);
        writer.write(items);

        assertThat(dao.count(), is(equalTo(initialCount))); // no new
        assertThat(dao.findById(entity.getId()).get().getTitle(), is(equalTo(newValue))); // updated
    }

}