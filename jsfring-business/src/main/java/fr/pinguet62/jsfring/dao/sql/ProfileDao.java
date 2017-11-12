package fr.pinguet62.jsfring.dao.sql;

import fr.pinguet62.jsfring.dao.sql.common.CommonRepository;
import fr.pinguet62.jsfring.model.sql.Profile;
import org.springframework.stereotype.Repository;

/**
 * @see Profile
 */
@Repository
public interface ProfileDao extends CommonRepository<Profile, Integer> {
}