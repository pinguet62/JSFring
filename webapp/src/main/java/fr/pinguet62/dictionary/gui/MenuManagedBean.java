package fr.pinguet62.dictionary.gui;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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

@ManagedBean
@RequestScoped
public class MenuManagedBean implements Serializable {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MenuManagedBean.class);

    /** Serial version UID. */
    private static final long serialVersionUID = 3909676962671942314L;

    /** The association of <b>outcome</b> and the {@link BreadCrumb}. */
    protected final Map<String, MenuModel> breadcrumbs = new HashMap<>();

    /** First {@link MenuItem} of {@link #model}. */
    protected final DefaultMenuItem home;

    /** The {@link Menubar}. */
    protected final MenuModel model = new DefaultMenuModel();

    /**
     * Constructor.
     * <p>
     * Initialize the {@link Menubar} and the {@link BreadCrumb}.
     */
    public MenuManagedBean() {
        home = new DefaultMenuItem("Home");
        home.setOutcome("index");
        home.setIcon("ui-icon-home");

        LOGGER.info("Initialization of MenuBar...");
        initMenu();

        LOGGER.info("Initialization of BreadCrumb...");
        initBreadcrumbs();
    }

    /**
     * Get the current <b>outcome</b> and return the associated
     * {@link BreadCrumb}.
     *
     * @return The {@link BreadCrumb}.
     */
    public MenuModel getBreadcrumb() {
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

    public MenuModel getModel() {
        return model;
    }

    /**
     * Initialize the {@link #breadcrumbs}.
     * <p>
     * The {@link #home} has no sub-level.
     */
    private void initBreadcrumbs() {
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
     * Recursive method who initialize the {@link BreadCrumb} with
     * {@link MenuItem}.
     *
     * @param thread
     *            The current thread who iterate the tree.
     */
    private void initBreadcrumbWithMenuElement(Deque<MenuElement> thread) {
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

    /**
     * Initialize the {@link Menubar}.
     * <p>
     * The first {@link MenuItem} is {@link #home}.
     *
     * @return The {@link MenuModel}.
     */
    protected void initMenu() {
        // Item 1: home
        model.addElement(home);
        LOGGER.info("MenuItem 1: \"Home\"/index");

        // Level 2
        DefaultSubMenu submenu2 = new DefaultSubMenu("Administration");
        submenu2.setIcon("ui-icon-gear");
        model.addElement(submenu2);
        LOGGER.info("SubMenu 2: \"Administration\" ...");
        // Item 21
        DefaultMenuItem item21 = new DefaultMenuItem("Users");
        item21.setOutcome("users");
        item21.setIcon("ui-icon-person");
        submenu2.addElement(item21);
        LOGGER.info("MenuItem 21: \"Users\"/users");
        // Item 22
        DefaultMenuItem item22 = new DefaultMenuItem("Profiles");
        item22.setOutcome("profiles");
        item22.setIcon("ui-icon-flag");
        submenu2.addElement(item22);
        LOGGER.info("MenuItem 22: \"Profiles\"/profiles");
    }

}
