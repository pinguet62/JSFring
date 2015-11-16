package fr.pinguet62.jsfring.gui.util.scope;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * JSF view {@link Scope} implementation for Spring.
 *
 * @see <a href=
 *      "https://cagataycivici.wordpress.com/2010/02/17/port-jsf-2-0s-viewscope-to-spring-3-0/"
 *      >Porting JSF 2.0’s ViewScope to Spring 3.0<a/>
 */
public final class SpringViewScope implements Scope {

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();

        if (viewMap.containsKey(name)) {
            Object bean = viewMap.get(name);

            // Restore a transient autowired beans after re-serialization bean
            WebApplicationContext webAppContext = ContextLoader.getCurrentWebApplicationContext();
            AutowireCapableBeanFactory autowireFactory = webAppContext.getAutowireCapableBeanFactory();
            if (webAppContext.containsBean(name))
                bean = autowireFactory.configureBean(bean, name);

            return bean;
        } else {
            Object object = objectFactory.getObject();
            viewMap.put(name, object);
            return object;
        }
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