package fr.pinguet62.jsfring.service;

import fr.pinguet62.jsfring.dao.sql.RightDao;
import fr.pinguet62.jsfring.model.sql.Right;
import org.springframework.stereotype.Service;

/**
 * The service for {@link Right}.
 */
@Service
public class RightService extends AbstractService<Right, String> {

    public RightService(RightDao dao) {
        super(dao);
    }

}