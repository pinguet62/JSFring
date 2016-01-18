package fr.pinguet62.jsfring.dao;

import org.springframework.stereotype.Repository;

import fr.pinguet62.jsfring.dao.common.CommonRepository;
import fr.pinguet62.jsfring.model.Profile;

/** @see Profile */
@Repository
public interface ProfileDao extends CommonRepository<Profile, Integer> {}