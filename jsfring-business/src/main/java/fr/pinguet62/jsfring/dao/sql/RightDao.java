package fr.pinguet62.jsfring.dao.sql;

import org.springframework.stereotype.Repository;

import fr.pinguet62.jsfring.dao.sql.common.CommonRepository;
import fr.pinguet62.jsfring.model.sql.Right;

/** @see Right */
@Repository
public interface RightDao extends CommonRepository<Right, String> {
}