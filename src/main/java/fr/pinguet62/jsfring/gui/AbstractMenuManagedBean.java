package fr.pinguet62.jsfring.gui;

import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.primefaces.component.breadcrumb.BreadCrumb;
import org.primefaces.component.menubar.Menubar;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuGroup;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.MenuModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;

/**
 * Abstract Managed Bean used to generate dynamically the {@link BreadCrumb}
 * from the {@link MenuModel} of {@link Menubar}.
 */
public abstract class AbstractMenuManagedBean {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(AbstractMenuManagedBean.class);

    /** The association of <b>outcome</b> to its {@link BreadCrumb}. */
    protected final Map<String, MenuModel> breadcrumbs = new HashMap<>();

    /** First {@link MenuItem} of {@link #model}. */
    protected DefaultMenuItem home;

    /**
     * {@link Bean} for i18n.
     *
     * @inject.setter {@link #setMessageSource(MessageSource)}
     */
    @ManagedProperty("#{messageSource}")
    private MessageSource messageSource;

    /** The {@link MenuModel} for {@link Menubar}. */
    protected final MenuModel model = new DefaultMenuModel();

    /**
     * Generate the {@link BreadCrumb}:<br/>
     * get the current <b>outcome</b> and return the associated
     * {@link BreadCrumb}.
     *
     * @return The {@link BreadCrumb}.
     */
    public final MenuModel getBreadcrumb() {
        // Get outcome
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Set<NavigationCase>> navigationRules = ((ConfigurableNavigationHandler) context
                .getApplication().getNavigationHandler()).getNavigationCases();
        String viewId = context.getViewRoot().getViewId();
        NavigationCase target = null;
        for (Set<NavigationCase> navigationCases : navigationRules.values())
            for (NavigationCase navigationCase : navigationCases)
                if (navigationCase.getToViewId(context).equals(viewId)) {
                    target = navigationCase;
                    break;
                }
        if (target == null)
            return null;

        return breadcrumbs.get(target.getFromOutcome());
    }

    /**
     * Get the {@link MenuItem} for home link.<br/>
     * Used as root of {@link BreadCrumb}.<br/>
     * The value must not be {@code null}.
     */
    protected abstract DefaultMenuItem getHome();

    // protected: unittest without i18n
    /**
     * Get the i18n translation of value, using the {@link #messageSource}.
     *
     * @param key
     *            The i18n key.
     * @return The translated value.
     */
    protected final String getMessage(String key) {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot()
                .getLocale();
        return messageSource.getMessage(key, null, locale);
    }

    /**
     * Used by the xHTML to get the {@link MenuModel}.
     *
     * @return The {@link #model}.
     */
    public final MenuModel getModel() {
        return model;
    }

    /** Initialize the {@link MenuModel} and the {@link BreadCrumb}. */
    @PostConstruct
    protected void init() {
        home = getHome();

        LOGGER.trace("Initialization of MenuBar...");
        initMenu();

        LOGGER.trace("Initialization of BreadCrumb...");
        initBreadcrumbs();
    }

    /**
     * Initialize the {@link #breadcrumbs}.
     * <p>
     * The {@link #home} has no sub-level.
     */
    private final void initBreadcrumbs() {
        for (MenuElement element : model.getElements())
            // Home
            if (element.equals(home)) {
                MenuModel homeModel = new DefaultMenuModel();
                MenuItem homeItem = home;
                homeModel.addElement(homeItem);
                breadcrumbs.put(homeItem.getOutcome(), homeModel);
            } else
                initBreadcrumbWithMenuElement(new LinkedList<MenuElement>(
                        Arrays.asList(element)));
    }

    /**
     * Recursive method who initialize the {@link BreadCrumb} from
     * {@link #model}.
     *
     * @param thread
     *            The current thread who iterate the tree.
     */
    private final void initBreadcrumbWithMenuElement(Deque<MenuElement> thread) {
        MenuElement last = thread.getLast();
        // Item
        if (last instanceof MenuItem) {
            DefaultMenuItem lastItem = (DefaultMenuItem) last;
            // Build breadcrumb
            MenuModel breadcrumb = new DefaultMenuModel();
            breadcrumb.addElement(home); // "home"
            for (MenuElement element : thread) {
                // Convert MenuElement to MenuItem
                DefaultMenuItem breadcrumbItem;
                if (element instanceof MenuItem)
                    breadcrumbItem = (DefaultMenuItem) element;
                else if (element instanceof MenuGroup) {
                    DefaultSubMenu group = (DefaultSubMenu) element;
                    breadcrumbItem = new DefaultMenuItem();
                    // breadcrumbItem.setIcon(group.getIcon());
                    breadcrumbItem.setId(group.getId());
                    breadcrumbItem.setStyle(group.getStyle());
                    breadcrumbItem.setStyleClass(group.getStyleClass());
                    breadcrumbItem.setValue(group.getLabel());
                } else
                    throw new IllegalArgumentException("Type unknown: "
                            + element);
                breadcrumb.addElement(breadcrumbItem);
            }
            breadcrumbs.put(lastItem.getOutcome(), breadcrumb);
            // LOGGER.info("Breadcrumb: "
            // + breadcrumb
            // .getElements()
            // .stream()
            // .map(element -> (DefaultMenuItem) element)
            // .map(item -> String.format("(\"%s\"/%s)",
            // item.getTitle(), item.getOutcome()))
            // .collect(Collectors.joining(" > ")));
        }
        // Recursive
        else if (last instanceof MenuGroup) {
            MenuGroup group = (MenuGroup) last;
            @SuppressWarnings("unchecked")
            List<MenuElement> subElements = group.getElements();
            for (MenuElement subElement : subElements) {
                thread.addLast(subElement);
                initBreadcrumbWithMenuElement(thread);
                thread.removeLast();
            }
        } else
            throw new IllegalArgumentException("Type unknown: " + last);
    }

    // protected: unittest with data test
    /**
     * Initialize the {@link Menubar}.
     * <p>
     * The first {@link MenuItem} is {@link #home}.
     */
    protected abstract void initMenu();

    /** @inject.attribute {@link #messageSource} */
    public final void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

}
