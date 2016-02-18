package fr.pinguet62.jsfring.service;

import static fr.pinguet62.jsfring.service.config.CacheConfig.PROFILE_CACHE;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.pinguet62.jsfring.model.sql.Profile;

/** The service for {@link Profile}. */
@Service
public class ProfileService extends AbstractService<Profile, Integer> {

    @Override
    @CacheEvict(value = PROFILE_CACHE, allEntries = true)
    @Transactional
    public Profile create(Profile object) {
        return dao.save(object);
    }

    @Override
    @CacheEvict(value = PROFILE_CACHE, allEntries = true)
    @Transactional
    public void delete(Profile object) {
        dao.delete(object);
    }

    @Override
    @Cacheable(PROFILE_CACHE)
    @Transactional(readOnly = true)
    public Profile get(Integer id) {
        return super.get(id);
    }

    @Override
    @Cacheable(PROFILE_CACHE)
    @Transactional(readOnly = true)
    public List<Profile> getAll() {
        return super.getAll();
    }

    @Override
    @CacheEvict(value = PROFILE_CACHE, allEntries = true)
    @Transactional
    public Profile update(Profile object) {
        return dao.save(object);
    }

}