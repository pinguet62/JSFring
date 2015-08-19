package fr.pinguet62.jsfring.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.pinguet62.jsfring.dao.RightDao;
import fr.pinguet62.jsfring.model.Right;

/** The service for {@link Right}. */
@Service
public class RightService extends AbstractService<Right, String> {

    private static final String CACHE = "right";

    @Inject
    protected RightService(RightDao dao) {
        super(dao);
    }

    @Override
    @Cacheable(CACHE)
    @Transactional(readOnly = true)
    public Right get(String id) {
        return super.get(id);
    }

    @Override
    @Cacheable(CACHE)
    @Transactional(readOnly = true)
    public List<Right> getAll() {
        return super.getAll();
    }

}