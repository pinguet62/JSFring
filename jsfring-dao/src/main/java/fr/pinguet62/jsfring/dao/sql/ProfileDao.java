package fr.pinguet62.jsfring.dao.sql;

import org.springframework.stereotype.Repository;

import fr.pinguet62.jsfring.dao.sql.common.CommonRepository;
import fr.pinguet62.jsfring.model.sql.Profile;

/** @see Profile */
@Repository
public interface ProfileDao extends CommonRepository<Profile, Integer> {}