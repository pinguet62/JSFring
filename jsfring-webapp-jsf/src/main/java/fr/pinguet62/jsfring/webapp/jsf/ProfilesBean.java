package fr.pinguet62.jsfring.webapp.jsf;

import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.model.sql.Right;
import fr.pinguet62.jsfring.service.AbstractService;
import fr.pinguet62.jsfring.service.ProfileService;
import fr.pinguet62.jsfring.service.RightService;
import fr.pinguet62.jsfring.webapp.jsf.config.scope.SpringViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @see Profile
 */
@Component
@SpringViewScoped
public final class ProfilesBean extends AbstractCrudBean<Profile> {

    private static final long serialVersionUID = 1;

    @Autowired
    private transient ProfileService profileService;

    /**
     * The {@link Right} association (available/associated) of the {@link #getSelectedValue() selected profile}.
     */
    @Getter
    @Setter
    private DualListModel<Right> rightsAssociation = new DualListModel<>();

    @Autowired
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

    @Override
    public AbstractService<Profile, ? extends Serializable> getService() {
        return profileService;
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
        List<Right> availableRights = rightService.getAll().filter(right -> !associatedRights.contains(right)).collectList().block();
        rightsAssociation = new DualListModel<>(availableRights, associatedRights);
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