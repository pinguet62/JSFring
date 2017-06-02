package fr.pinguet62.jsfring.batch.common;

import static java.util.Objects.requireNonNull;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.data.repository.CrudRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * An {@link ItemWriter} using {@link CrudRepository} to {@link CrudRepository#save(Object) create or update} items.
 * <p>
 * Like {@link RepositoryItemWriter}, but calling {@link CrudRepository#save(Iterable)} method:
 * <ul>
 * <li><i>method call</i>: doesn't use the {@link RepositoryItemWriter#setMethodName(String) method name}, but it's a safe java
 * code call;</li>
 * <li><i>mass writing</i>: doesn't call method on each of items, but write all items simultaneously. Depending to the
 * implementation, the treatment can be optimized.</li>
 * </ul>
 *
 * @see ItemWriter
 * @see CrudRepository#save(Object)
 * @see RepositoryItemWriter
 */
@Slf4j
public class CrudRepositoryItemWriter<T> implements ItemWriter<T> {

    private final CrudRepository<T, ?> repository;

    public CrudRepositoryItemWriter(CrudRepository<T, ?> repository) {
        this.repository = requireNonNull(repository);
    }

    @Override
    public void write(List<? extends T> items) throws Exception {
        log.debug("Writing to the repository with {0} items", items.size());
        for (T object : items)
            repository.save(object);
    }

}