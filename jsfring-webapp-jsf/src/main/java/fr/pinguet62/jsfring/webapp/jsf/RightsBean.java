package fr.pinguet62.jsfring.webapp.jsf;

import fr.pinguet62.jsfring.model.sql.Right;
import fr.pinguet62.jsfring.service.AbstractService;
import fr.pinguet62.jsfring.service.RightService;
import fr.pinguet62.jsfring.webapp.jsf.config.scope.SpringViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@SpringViewScoped
public class RightsBean extends AbstractSelectableBean<Right> {

    private static final long serialVersionUID = 1;

    @Autowired
    private transient RightService rightService;

    @Override
    public AbstractService<Right, ? extends Serializable> getService() {
        return rightService;
    }

}