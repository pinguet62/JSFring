package fr.pinguet62.jsfring.dao;

import org.springframework.stereotype.Repository;

import fr.pinguet62.jsfring.dao.common.CommonRepository;
import fr.pinguet62.jsfring.model.Right;

/** @see Right */
@Repository
public interface RightDao extends CommonRepository<Right, String> {}