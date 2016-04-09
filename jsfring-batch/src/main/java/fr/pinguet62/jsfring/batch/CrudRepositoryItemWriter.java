package fr.pinguet62.jsfring.batch;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.data.repository.CrudRepository;

/**
 * An {@link ItemWriter} using {@link CrudRepository} to {@link CrudRepository#save(Object) create or update} items.
 * <p>
 * Like {@link RepositoryItemWriter}, but calling {@link CrudRepository#save(Iterable)} method:
 * <ul>
 * <li><i>method call</i>: doesn't use the {@link RepositoryItemWriter#setMethodName(String) method name}, but it's a
 * safe java code call;</li>
 * <li><i>mass writing</i>: doesn't call method on each of items, but write all items simultaneously. Depending to the
 * implementation, the treatment can be optimized.</li>
 * </ul>
 *
 * @see ItemWriter
 * @see CrudRepository#save(Object)
 * @see RepositoryItemWriter
 */
public class CrudRepositoryItemWriter<T> implements ItemWriter<T> {

    private static final Logger LOGGER = getLogger(CrudRepositoryItemWriterTest.class);

    @Inject
    private CrudRepository<T, ?> repository;

    @Override
    public void write(List<? extends T> items) throws Exception {
        LOGGER.debug("Writing to the repository with {0} items.", items.size());
        for (T object : items)
            repository.save(object);
    }

}