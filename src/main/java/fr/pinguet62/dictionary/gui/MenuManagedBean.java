package fr.pinguet62.dictionary.gui;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultSubMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@RequestScoped
public class MenuManagedBean extends AbstractMenuManagedBean {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MenuManagedBean.class);

    @Override
    protected DefaultMenuItem getHome() {
        DefaultMenuItem home = new DefaultMenuItem(getMessage("menubar.index"));
        home.setOutcome("index");
        home.setIcon("ui-icon-home");
        return home;
    }

    @Override
    protected void initMenu() {
        // Item 1: home
        model.addElement(home);
        LOGGER.info("MenuItem 1: \"Home\"/index");

        // Level 2
        DefaultSubMenu submenu2 = new DefaultSubMenu(
                getMessage("menubar.administration"));
        submenu2.setIcon("ui-icon-gear");
        model.addElement(submenu2);
        LOGGER.info("SubMenu 2: \"Administration\" ...");
        // Item 21
        DefaultMenuItem item21 = new DefaultMenuItem(
                getMessage("menubar.administration.users"));
        item21.setOutcome("users");
        item21.setIcon("ui-icon-person");
        submenu2.addElement(item21);
        LOGGER.info("MenuItem 21: \"Users\"/users");
        // Item 22
        DefaultMenuItem item22 = new DefaultMenuItem(
                getMessage("menubar.administration.profiles"));
        item22.setOutcome("profiles");
        item22.setIcon("ui-icon-flag");
        submenu2.addElement(item22);
        LOGGER.info("MenuItem 22: \"Profiles\"/profiles");
    }

}
