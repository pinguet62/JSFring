package fr.pinguet62.jsfring.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.pinguet62.jsfring.dao.RightDao;
import fr.pinguet62.jsfring.model.Right;
import fr.pinguet62.jsfring.service.config.CacheConfig;

/** The service for {@link Right}. */
@Service
public class RightService extends AbstractService<Right, String> {

    @Inject
    protected RightService(RightDao dao) {
        super(dao);
    }

    @Override
    @Cacheable(CacheConfig.RIGHT_CACHE)
    @Transactional(readOnly = true)
    public Right get(String id) {
        return super.get(id);
    }

    @Override
    @Cacheable(CacheConfig.RIGHT_CACHE)
    @Transactional(readOnly = true)
    public List<Right> getAll() {
        return super.getAll();
    }

}