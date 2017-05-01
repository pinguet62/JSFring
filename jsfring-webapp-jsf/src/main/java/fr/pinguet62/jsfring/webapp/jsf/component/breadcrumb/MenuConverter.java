package fr.pinguet62.jsfring.webapp.jsf.component.breadcrumb;

import java.util.function.Function;

import org.primefaces.component.breadcrumb.BreadCrumb;
import org.primefaces.component.menu.Menu;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.Submenu;

/**
 * Converter used to convert {@link MenuElement} ({@link Submenu} or {@link MenuItem}) to a {@link MenuItem}.
 * <p>
 * This converter is used to convert {@link Menu} to {@link BreadCrumb}.
 */
public final class MenuConverter implements Function<MenuElement, MenuItem> {

    /**
     * The {@link Submenu} is converted to {@link MenuItem} with similar properties.<br>
     * The {@link MenuItem} is copied with only necessary properties.
     *
     * @param element
     *            The {@link MenuElement} to convert.
     * @return The {@link MenuItem} to display in the {@link BreadCrumb}.
     * @throws IllegalArgumentException
     *             Unknown type.
     */
    @Override
    public MenuItem apply(MenuElement element) {
        if (element instanceof MenuItem)
            return apply((MenuItem) element);
        else if (element instanceof Submenu)
            return apply((Submenu) element);
        throw new IllegalArgumentException("Unknown type: " + element);
    }

    /**
     * Create new {@link MenuItem} and copy all necessary properties.
     *
     * @param menuItem
     *            The {@link MenuItem} to copy.
     * @return The new {@link MenuItem}.
     */
    private MenuItem apply(MenuItem menuItem) {
        DefaultMenuItem breadcrumbItem = new DefaultMenuItem();
        breadcrumbItem.setOutcome(menuItem.getOutcome());
        breadcrumbItem.setUrl(menuItem.getUrl());
        breadcrumbItem.setValue(menuItem.getValue());
        return breadcrumbItem;
    }

    /**
     * Convert to {@link MenuItem} with similar properties.
     *
     * @param submenu
     *            The {@link Submenu} to convert.
     * @return The {@link MenuItem}.
     */
    private MenuItem apply(Submenu submenu) {
        DefaultMenuItem breadcrumbItem = new DefaultMenuItem();
        // breadcrumbItem.setIcon(group.getIcon());
        breadcrumbItem.setId(submenu.getId());
        breadcrumbItem.setStyle(submenu.getStyle());
        breadcrumbItem.setStyleClass(submenu.getStyleClass());
        breadcrumbItem.setValue(submenu.getLabel());
        return breadcrumbItem;
    }

}
