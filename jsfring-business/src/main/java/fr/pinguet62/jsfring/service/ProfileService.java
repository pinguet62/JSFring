package fr.pinguet62.jsfring.service;

import fr.pinguet62.jsfring.dao.sql.ProfileDao;
import fr.pinguet62.jsfring.model.sql.Profile;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static fr.pinguet62.jsfring.service.config.CacheConfig.PROFILE_CACHE;

/**
 * The service for {@link Profile}.
 */
@Service
public class ProfileService extends AbstractService<Profile, Integer> {

    public ProfileService(ProfileDao dao) {
        super(dao);
    }

    @CacheEvict(value = PROFILE_CACHE, allEntries = true)
    @Override
    @Transactional
    public Mono<Profile> create(Profile object) {
        return Mono.just(dao.save(object));
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
    public Mono<Profile> get(Integer id) {
        return super.get(id);
    }

    @Override
    @Cacheable(PROFILE_CACHE)
    @Transactional(readOnly = true)
    public Flux<Profile> getAll() {
        return super.getAll();
    }

    @Override
    @CacheEvict(value = PROFILE_CACHE, allEntries = true)
    @Transactional
    public Mono<Profile> update(Profile object) {
        return Mono.just(dao.save(object));
    }

}