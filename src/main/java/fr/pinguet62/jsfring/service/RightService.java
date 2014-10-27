package fr.pinguet62.jsfring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.pinguet62.jsfring.dao.RightDao;
import fr.pinguet62.jsfring.model.Right;

/** The service for {@link Right}. */
@Service
public class RightService extends AbstractService<Right, String> {

    @Autowired
    protected RightService(RightDao dao) {
        super(dao);
    }

}