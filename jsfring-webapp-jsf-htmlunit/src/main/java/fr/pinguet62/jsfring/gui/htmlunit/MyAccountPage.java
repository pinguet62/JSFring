package fr.pinguet62.jsfring.gui.htmlunit;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fr.pinguet62.jsfring.gui.htmlunit.field.SelectOneMenu;

public final class MyAccountPage extends AbstractPage {

    protected MyAccountPage(HtmlPage page) {
        super(page);
    }

    private HtmlDivision getField(int i) {
        return (HtmlDivision) page.getByXPath("//form//table/tbody/tr[" + (i + 1) + "]/td[2]/div").get(0);
    }

    public SelectOneMenu getLangSwitcher() {
        return new SelectOneMenu(getField(1));
    }

    public SelectOneMenu getThemeSwitcher() {
        return new SelectOneMenu(getField(0));
    }

}