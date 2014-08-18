package fr.pinguet62.dictionary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.pinguet62.dictionary.dao.ProfileDao;
import fr.pinguet62.dictionary.model.Profile;

/** The service for {@link Profile}. */
@Service
public final class ProfileService extends AbstractService<Profile, Integer> {

    @Autowired
    protected ProfileService(ProfileDao dao) {
        super(dao);
    }

}