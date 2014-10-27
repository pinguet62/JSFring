package fr.pinguet62.jsfring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.pinguet62.jsfring.dao.ProfileDao;
import fr.pinguet62.jsfring.model.Profile;

/** The service for {@link Profile}. */
@Service
public class ProfileService extends AbstractService<Profile, Integer> {

    @Autowired
    protected ProfileService(ProfileDao dao) {
        super(dao);
    }

}