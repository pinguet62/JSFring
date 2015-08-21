package fr.pinguet62.jsfring.gui;

import javax.inject.Inject;
import javax.inject.Named;

import com.mysema.query.types.path.EntityPathBase;

import fr.pinguet62.jsfring.model.QRight;
import fr.pinguet62.jsfring.model.Right;
import fr.pinguet62.jsfring.service.AbstractService;
import fr.pinguet62.jsfring.service.RightService;
import fr.pinguet62.jsfring.util.cdi.scope.SpringViewScoped;

@Named
@SpringViewScoped
public class RightsBean extends AbstractSelectableBean<Right> {

    private static final long serialVersionUID = 1;

    @Inject
    private RightService rightService;

    @Override
    protected EntityPathBase<Right> getBaseExpression() {
        return QRight.right;
    }

    @Override
    public AbstractService<Right, ?> getService() {
        return rightService;
    }

}