package fr.pinguet62.jsfring.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DualListModel;

import com.mysema.query.types.path.EntityPathBase;

import fr.pinguet62.jsfring.model.Profile;
import fr.pinguet62.jsfring.model.QProfile;
import fr.pinguet62.jsfring.model.Right;
import fr.pinguet62.jsfring.service.AbstractService;
import fr.pinguet62.jsfring.service.ProfileService;
import fr.pinguet62.jsfring.service.RightService;
import fr.pinguet62.jsfring.util.cdi.scope.SpringViewScoped;

/** @see Profile */
@Named
@SpringViewScoped
public final class ProfilesBean extends AbstractCrudBean<Profile> {

    private static final long serialVersionUID = 1;

    @Inject
    private transient ProfileService profileService;

    /**
     * The {@link Right} association (available/associated) of the
     * {@link #getSelectedValue() selected profile}.
     *
     * @property.getter {@link #getRightsAssociation()}
     * @property.setter {@link #setRightsAssociation(DualListModel)}
     */
    private DualListModel<Right> rightsAssociation = new DualListModel<Right>();

    @Inject
    private transient RightService rightService;

    /**
     * {@inheritDoc}
     * <p>
     * Initialize the associated {@link Right}s.
     */
    @Override
    public void create() {
        getSelectedValue().getRights().clear();
        getSelectedValue().getRights().addAll(rightsAssociation.getTarget());

        super.create();
    }

    /** @return {@link QProfile#profile} */
    @Override
    protected EntityPathBase<Profile> getBaseExpression() {
        return QProfile.profile;
    }

    @Override
    protected Profile getNewValue() {
        return new Profile();
    }

    /** @property.attribute {@link #rightsAssociation} */
    public DualListModel<Right> getRightsAssociation() {
        return rightsAssociation;
    }

    @Override
    public AbstractService<Profile, ? extends Serializable> getService() {
        return profileService;
    }

    /** @property.attribute {@link #rightsAssociation} */
    public void setRightsAssociation(DualListModel<Right> rightsAssociation) {
        this.rightsAssociation = rightsAssociation;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Initialize the associated {@link Right}s.
     */
    @Override
    public void setSelectedValue(Profile profile) {
        super.setSelectedValue(profile);

        List<Right> associatedRights = new ArrayList<>(getSelectedValue().getRights());
        List<Right> availableRights = rightService.getAll().stream().filter(right -> !associatedRights.contains(right))
                .collect(Collectors.toList());
        rightsAssociation = new DualListModel<Right>(availableRights, associatedRights);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Initialize the associated {@link Right}s.
     */
    @Override
    public void update() {
        // Right association
        getSelectedValue().getRights().clear();
        getSelectedValue().getRights().addAll(rightsAssociation.getTarget());

        super.update();
    }

}