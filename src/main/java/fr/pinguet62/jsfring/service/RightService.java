package fr.pinguet62.jsfring.service;

import javax.inject.Inject;
import javax.inject.Named;

import fr.pinguet62.jsfring.dao.RightDao;
import fr.pinguet62.jsfring.model.Right;

/** The service for {@link Right}. */
@Named
public class RightService extends AbstractService<Right, String> {

    @Inject
    protected RightService(RightDao dao) {
        super(dao);
    }

}