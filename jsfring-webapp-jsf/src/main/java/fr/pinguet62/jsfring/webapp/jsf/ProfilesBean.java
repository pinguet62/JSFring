package fr.pinguet62.jsfring.webapp.jsf;

import static java.util.stream.Collectors.toList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DualListModel;

import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.model.sql.Right;
import fr.pinguet62.jsfring.service.AbstractService;
import fr.pinguet62.jsfring.service.ProfileService;
import fr.pinguet62.jsfring.service.RightService;
import fr.pinguet62.jsfring.webapp.jsf.config.scope.SpringViewScoped;

/** @see Profile */
@Named
@SpringViewScoped
public final class ProfilesBean extends AbstractCrudBean<Profile> {

    private static final long serialVersionUID = 1;

    @Inject
    private transient ProfileService profileService;

    /** The {@link Right} association (available/associated) of the {@link #getSelectedValue() selected profile}. */
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
        // Right association
        getSelectedValue().getRights().clear();
        getSelectedValue().getRights().addAll(rightsAssociation.getTarget());

        super.create();
    }

    @Override
    protected Profile getNewValue() {
        Profile newEntity = new Profile();
        newEntity.setRights(new HashSet<>());
        return newEntity;
    }

    public DualListModel<Right> getRightsAssociation() {
        return rightsAssociation;
    }

    @Override
    public AbstractService<Profile, ? extends Serializable> getService() {
        return profileService;
    }

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

        // Right association
        List<Right> associatedRights = new ArrayList<>(getSelectedValue().getRights());
        List<Right> availableRights = rightService.getAll().stream().filter(right -> !associatedRights.contains(right))
                .collect(toList());
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