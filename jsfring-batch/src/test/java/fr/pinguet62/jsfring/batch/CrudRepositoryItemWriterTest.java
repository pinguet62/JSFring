package fr.pinguet62.jsfring.batch;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.Entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.batch.CrudRepositoryItemWriterTest.ProfileCrudRepositoryItemWriter;
import fr.pinguet62.jsfring.dao.sql.ProfileDao;
import fr.pinguet62.jsfring.model.sql.Profile;

/** @see CrudRepositoryItemWriter */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { SpringBootConfig.class, ProfileCrudRepositoryItemWriter.class })
@DatabaseSetup(DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class CrudRepositoryItemWriterTest {

    @Component
    public static class ProfileCrudRepositoryItemWriter extends CrudRepositoryItemWriter<Profile> {}

    @Inject
    private ProfileDao dao;

    @Inject
    private ProfileCrudRepositoryItemWriter writer;

    /**
     * Not existing {@link Entity} must be inserted.
     *
     * @see CrudRepositoryItemWriter#write(List)
     */
    @Test
    public void test_create() throws Exception {
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
    public void test_multiple() throws Exception {
        long initialCount = dao.count();

        int nb = 5;
        List<Profile> items = new ArrayList<>(nb);
        for (int i = 0; i < nb; i++) {
            Profile entity = new Profile();
            entity.setTitle("new title " + i);
            items.add(entity);
        }

        writer.write(items);

        assertThat(dao.count(), is(equalTo(initialCount + items.size())));
    }

    /**
     * Existing {@link Entity} must be updated.
     *
     * @see CrudRepositoryItemWriter#write(List)
     */
    @Test
    public void test_update() throws Exception {
        long initialCount = dao.count();

        final String newValue = "new title";
        List<Profile> entities = dao.findAll();
        Profile entity = entities.get(0);
        assertThat(entity.getTitle(), is(not(equalTo(newValue))));
        entity.setTitle(newValue);

        List<Profile> items = asList(entity);
        writer.write(items);

        assertThat(dao.count(), is(equalTo(initialCount))); // no new
        assertThat(dao.findOne(entity.getId()).getTitle(), is(equalTo(newValue))); // updated
    }

}