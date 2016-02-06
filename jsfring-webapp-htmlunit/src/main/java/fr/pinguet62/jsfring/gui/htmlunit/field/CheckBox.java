package fr.pinguet62.jsfring.gui.htmlunit.field;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.NavigatorException;

public final class CheckBox extends ReadWriteField<HtmlDivision, Boolean> {

    public CheckBox(HtmlDivision div) {
        super(div);
    }

    /** The {@link HtmlDivision} contains the {@code "ui-state-active"} class. */
    @Override
    public Boolean getValue() {
        HtmlDivision div = (HtmlDivision) html.getByXPath("./div[contains(@class, 'ui-chkbox-box')]").get(0);
        return div.getAttribute("class").contains("ui-state-active");
    }

    @Override
    public void setValue(Boolean value) {
        try {
            HtmlPage page = html.click();
            AbstractPage.debug(page);
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

}