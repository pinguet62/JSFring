package fr.pinguet62.jsfring.webapp.jsf.htmlunit;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.SelectOneMenu;

public final class MyAccountPage extends AbstractPage {

    protected MyAccountPage(HtmlPage page) {
        super(page);
    }

    private HtmlDivision getField(int i) {
        return page.getFirstByXPath("//form//table/tbody/tr[" + (i + 1) + "]/td[2]/div");
    }

    public SelectOneMenu getLangSwitcher() {
        return new SelectOneMenu(getField(1));
    }

    public SelectOneMenu getThemeSwitcher() {
        return new SelectOneMenu(getField(0));
    }

}
