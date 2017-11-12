package fr.pinguet62.jsfring.dao.sql;

import fr.pinguet62.jsfring.dao.sql.common.CommonRepository;
import fr.pinguet62.jsfring.model.sql.Right;
import org.springframework.stereotype.Repository;

/**
 * @see Right
 */
@Repository
public interface RightDao extends CommonRepository<Right, String> {
}