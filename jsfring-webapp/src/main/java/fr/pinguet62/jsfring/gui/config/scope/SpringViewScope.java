package fr.pinguet62.jsfring.gui.config.scope;

import java.util.Map;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.context.annotation.Bean;

/**
 * JSF view {@link Scope} implementation for Spring.
 *
 * @see <a href=
 *      "https://cagataycivici.wordpress.com/2010/02/17/port-jsf-2-0s-viewscope-to-spring-3-0/"
 *      >Porting JSF 2.0’s ViewScope to Spring 3.0<a/>
 */
public final class SpringViewScope implements Scope {

    public static final String NAME = "view";

    /**
     * Get the {@link Bean} from its name.<br>
     * Get the value into the {@link UIViewRoot#getViewMap() view map}. If it
     * doesn't exist, the {@link Bean} is created, and stored into this
     * {@link UIViewRoot#getViewMap() view map}.
     */
    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
        Object bean = viewMap.get(name);
        if (bean == null) {
            bean = objectFactory.getObject();
            viewMap.put(name, bean);
        }
        return bean;
    }

    /** @return {@code null} */
    @Override
    public String getConversationId() {
        return null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Not supported.
     */
    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        // Not supported
    }

    @Override
    public Object remove(String name) {
        return FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove(name);
    }

    /** @return {@code null} */
    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

}