package fr.pinguet62.dictionary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.pinguet62.dictionary.dao.RightDao;
import fr.pinguet62.dictionary.model.Right;

/** The service for {@link Right}. */
@Service
public class RightService extends AbstractService<Right, String> {

    @Autowired
    protected RightService(RightDao dao) {
        super(dao);
    }

}