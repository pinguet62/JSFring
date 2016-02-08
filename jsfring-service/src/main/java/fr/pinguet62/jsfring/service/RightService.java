package fr.pinguet62.jsfring.service;

import static fr.pinguet62.jsfring.service.config.CacheConfig.RIGHT_CACHE;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.pinguet62.jsfring.model.sql.Right;

/** The service for {@link Right}. */
@Service
public class RightService extends AbstractService<Right, String> {

    @Override
    @Cacheable(RIGHT_CACHE)
    @Transactional(readOnly = true)
    public Right get(String id) {
        return super.get(id);
    }

    @Override
    @Cacheable(RIGHT_CACHE)
    @Transactional(readOnly = true)
    public List<Right> getAll() {
        return super.getAll();
    }

    @Override
    @Cacheable(RIGHT_CACHE)
    @Transactional(readOnly = true)
    public Right update(Right object) {
        return super.update(object);
    }

}