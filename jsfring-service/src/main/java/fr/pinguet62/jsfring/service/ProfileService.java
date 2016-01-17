package fr.pinguet62.jsfring.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.SearchResults;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Predicate;

import fr.pinguet62.jsfring.model.Profile;
import fr.pinguet62.jsfring.service.config.CacheConfig;

/** The service for {@link Profile}. */
@Service
public class ProfileService extends AbstractService<Profile, Integer> {

    @Override
    @CacheEvict(value = CacheConfig.PROFILE_CACHE, allEntries = true)
    @Transactional
    public Profile create(Profile object) {
        return dao.save(object);
    }

    @Override
    @CacheEvict(value = CacheConfig.PROFILE_CACHE, allEntries = true)
    @Transactional
    public void delete(Profile object) {
        dao.delete(object);
    }

    @Override
    @Cacheable(CacheConfig.PROFILE_CACHE)
    @Transactional(readOnly = true)
    public Iterable<Profile> findAll(Predicate predicate) {
        return dao.findAll(predicate);
    }

    @Override
    @Cacheable(CacheConfig.PROFILE_CACHE)
    @Transactional(readOnly = true)
    public SearchResults<Profile> findPanginated(JPAQuery query) {
        return dao.findPanginated(query);
    }

    @Override
    @Cacheable(CacheConfig.PROFILE_CACHE)
    @Transactional(readOnly = true)
    public Profile get(Integer id) {
        return super.get(id);
    }

    @Override
    @Cacheable(CacheConfig.PROFILE_CACHE)
    @Transactional(readOnly = true)
    public List<Profile> getAll() {
        return super.getAll();
    }

    @Override
    @CacheEvict(value = CacheConfig.PROFILE_CACHE, allEntries = true)
    @Transactional
    public Profile update(Profile object) {
        return dao.save(object);
    }

}