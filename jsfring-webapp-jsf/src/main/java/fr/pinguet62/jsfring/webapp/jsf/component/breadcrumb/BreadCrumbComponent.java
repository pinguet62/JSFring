package fr.pinguet62.jsfring.webapp.jsf.component.breadcrumb;

import lombok.extern.slf4j.Slf4j;
import org.primefaces.component.breadcrumb.BreadCrumb;
import org.primefaces.component.menu.Menu;
import org.primefaces.component.menubar.Menubar;
import org.primefaces.model.menu.*;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import java.util.*;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static javax.faces.context.FacesContext.getCurrentInstance;

@Slf4j
@FacesComponent("fr.pinguet62.jsfring.webapp.jsf.component.breadcrumb.BreadCrumbComponent")
public class BreadCrumbComponent extends BreadCrumb {

    private enum PropertyKeys {
        index, menu
    }

    /**
     * The association of <b>outcome</b> to its {@link BreadCrumb}.
     */
    protected final Map<String, MenuModel> breadcrumbs = new HashMap<>();

    /**
     * Generate the {@link MenuItem} corresponding to the index of web site.<br>
     * Set the <code>outcome</code> from attribute tag {@link #getIndex()}
     *
     * @return The {@link MenuItem}.
     * @see #getIndex()
     */
    private MenuItem creatIndexMenuItem() {
        DefaultMenuItem indexMenuItem = new DefaultMenuItem();
        indexMenuItem.setOutcome(getIndex());
        return indexMenuItem;
    }

    /**
     * Get the current <code>outcome</code> by reading the {@link FacesContext}.
     *
     * @return The current <code>outcome</code>.
     */
    private String getCurrentOutcome() {
        FacesContext context = getCurrentInstance();
        String viewId = context.getViewRoot().getViewId();
        Map<String, Set<NavigationCase>> navigationRules = ((ConfigurableNavigationHandler) context.getApplication().getNavigationHandler()).getNavigationCases();
        for (Set<NavigationCase> navigationCases : navigationRules.values())
            for (NavigationCase navigationCase : navigationCases)
                if (navigationCase.getToViewId(context).equals(viewId))
                    return navigationCase.getFromOutcome();
        return null; // not found
    }

    /**
     * Returns the component family of {@link UINamingContainer}.<br>
     * Required by composite component.
     */
    @Override
    public String getFamily() {
        return UINamingContainer.COMPONENT_FAMILY;
    }

    public String getIndex() {
        return (String) getStateHelper().get(PropertyKeys.index);
    }

    public String getMenu() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.menu);
    }

    /**
     * Parse the {@link Menu} to generate the {@link MenuModel} used by {@link BreadCrumb}.
     */
    @Override
    public MenuModel getModel() {
        String menuId = getMenu();
        Menubar menu = (Menubar) findComponent(menuId);
        initBreadcrumbs(menu);
        String outcome = getCurrentOutcome();
        return breadcrumbs.get(outcome);
    }

    /**
     * Parse the {@link Menu} to generate a {@link Map} who associate the <code>outcome</code> to the corresponding
     * {@link MenuModel}.
     * <p>
     * If no {@code outcome} set, the breadcrumbs is not saved.<br>
     * If many {@code outcome} is found, keep only the last.
     *
     * @return Association of <code>outcome</code> to {@link MenuModel}.
     */
    @SuppressWarnings("unchecked")
    private void initBreadcrumbs(Menubar menu) {
        for (MenuElement element : (List<MenuElement>) menu.getElements())
            parseMenuElement(new LinkedList<>(asList(element)));
    }

    /**
     * Initialize the {@link #breadcrumbs} by browsing the {@link Menubar}. <br>
     * It's a recursive method because {@link MenuElement} are linked as a tree with composite pattern of {@link MenuItem} and
     * {@link MenuGroup}.
     *
     * @param thread The current thread who is browsing the tree.
     * @throws IllegalArgumentException Unknown {@link MenuElement} type.
     */
    private void parseMenuElement(Deque<MenuElement> thread) {
        MenuElement last = thread.getLast();
        // Item
        if (last instanceof MenuItem) {
            MenuItem lastItem = (MenuItem) last;
            // Build breadcrumb
            MenuModel breadcrumb = new DefaultMenuModel();
            String outcome = lastItem.getOutcome();
            if (outcome == null)
                return;
            // - don't create double index MenuItem
            MenuItem indexMenuItem = creatIndexMenuItem();
            if (!outcome.equals(indexMenuItem.getOutcome()))
                breadcrumb.addElement(indexMenuItem);
            // - convert
            thread.stream().map(new MenuConverter()::apply).forEach(breadcrumb::addElement);
            // - save
            breadcrumbs.put(outcome, breadcrumb);
            log.trace("Breadcrumb: " + breadcrumb.getElements().stream()
                    .map(element -> (DefaultMenuItem) element)
                    .map(item -> String.format("(\"%s\"/%s)", item.getTitle(), item.getOutcome()))
                    .collect(joining(" > ")));
        }
        // Recursive
        else if (last instanceof MenuGroup) {
            MenuGroup group = (MenuGroup) last;
            @SuppressWarnings("unchecked")
            List<MenuElement> subElements = group.getElements();
            for (MenuElement subElement : subElements) {
                thread.addLast(subElement);
                parseMenuElement(thread);
                thread.removeLast();
            }
        } else
            throw new IllegalArgumentException("Unknown MenuElement: " + last);
    }

    public void setIndex(String index) {
        getStateHelper().put(PropertyKeys.index, index);
    }

    public void setMenu(String menu) {
        getStateHelper().put(PropertyKeys.menu, menu);
    }

}