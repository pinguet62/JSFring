package fr.pinguet62.dictionary.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.MenuModel;

/** @see MenuManagedBean */
public final class MenuManagedBeanTest {

    class AMenu extends MenuManagedBean {
        private static final long serialVersionUID = 1;

        @Override
        protected void initMenu() {
            // home
            model.addElement(home);
            // 1
            DefaultMenuItem item1 = new DefaultMenuItem("Item 1");
            item1.setOutcome("outcome 1");
            model.addElement(item1);
            // 2
            DefaultSubMenu submenu2 = new DefaultSubMenu("Menu 2");
            model.addElement(submenu2);
            // 21
            DefaultMenuItem item21 = new DefaultMenuItem("Item 21");
            item21.setOutcome("outcome 21");
            submenu2.addElement(item21);
            // 3
            DefaultSubMenu submenu3 = new DefaultSubMenu("Menu 3");
            model.addElement(submenu3);
            // 31
            DefaultSubMenu submenu31 = new DefaultSubMenu("Submenu 31");
            submenu3.addElement(submenu31);
            // 311
            DefaultMenuItem item311 = new DefaultMenuItem("Item 311");
            item311.setOutcome("outcome 311");
            submenu31.addElement(item311);
        }
    }

    /**
     * Check order of {@link MenuItem} into {@link MenuModel}.
     *
     * @see MenuManagedBean#breadcrumbs
     */
    @Test
    public void test() {
        MenuManagedBean bean = new AMenu();
        Map<String, MenuModel> breadcrumbs = bean.breadcrumbs;
        // 1
        MenuModel model1 = breadcrumbs.get("outcome 1");
        List<MenuElement> elements1 = model1.getElements();
        assertEquals(2, elements1.size());
        assertEquals(bean.home, elements1.get(0));
        assertEquals("Item 1", ((DefaultMenuItem) elements1.get(1)).getValue());
        // 21
        MenuModel model2 = breadcrumbs.get("outcome 21");
        List<MenuElement> elements2 = model2.getElements();
        assertEquals(3, elements2.size());
        assertEquals(bean.home, elements1.get(0));
        assertEquals("Menu 2", ((DefaultMenuItem) elements2.get(1)).getValue());
        assertEquals("Item 21", ((DefaultMenuItem) elements2.get(2)).getValue());
        // 311
        MenuModel model3 = breadcrumbs.get("outcome 311");
        List<MenuElement> elements3 = model3.getElements();
        assertEquals(4, elements3.size());
        assertEquals(bean.home, elements1.get(0));
        assertEquals("Menu 3", ((DefaultMenuItem) elements3.get(1)).getValue());
        assertEquals("Submenu 31",
                ((DefaultMenuItem) elements3.get(2)).getValue());
        assertEquals("Item 311",
                ((DefaultMenuItem) elements3.get(3)).getValue());
    }

    /**
     * All {@link MenuElement} of {@link MenuModel#getElements()} are instance
     * of {@link MenuItem}.
     *
     * @see MenuManagedBean#breadcrumbs
     */
    @Test
    public void test_typeMenuItem() {
        MenuManagedBean bean = new MenuManagedBean();
        Map<String, MenuModel> breadcrumbs = bean.breadcrumbs;
        breadcrumbs.values().forEach(
                breadcrumb -> breadcrumb.getElements().forEach(
                        element -> assertTrue(element instanceof MenuItem)));
    }

}
