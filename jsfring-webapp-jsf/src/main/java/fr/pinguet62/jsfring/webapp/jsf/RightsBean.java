package fr.pinguet62.jsfring.webapp.jsf;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;

import fr.pinguet62.jsfring.model.sql.Right;
import fr.pinguet62.jsfring.service.AbstractService;
import fr.pinguet62.jsfring.service.RightService;
import fr.pinguet62.jsfring.webapp.jsf.config.scope.SpringViewScoped;

@Named
@SpringViewScoped
public class RightsBean extends AbstractSelectableBean<Right> {

    private static final long serialVersionUID = 1;

    @Inject
    private transient RightService rightService;

    @Override
    public AbstractService<Right, ? extends Serializable> getService() {
        return rightService;
    }

}