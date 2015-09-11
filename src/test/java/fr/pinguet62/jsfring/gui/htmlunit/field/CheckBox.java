package fr.pinguet62.jsfring.gui.htmlunit.field;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;

import fr.pinguet62.jsfring.gui.htmlunit.NavigatorException;

public final class CheckBox extends ReadWriteField<Boolean> {

    public CheckBox(HtmlTableDataCell htmlTableDataCell) {
        super(htmlTableDataCell);
    }

    private HtmlDivision get() {
        return (HtmlDivision) htmlTableDataCell.getByXPath(
                "./div[contains(@class, 'ui-chkbox')]/div[contains(@class, 'ui-chkbox-box')]").get(0);
    }

    /** The {@link HtmlDivision} contains the {@code "ui-state-active"} class. */
    @Override
    public Boolean getValue() {
        return get().getAttribute("class").contains("ui-state-active");
    }

    @Override
    public void setValue(Boolean value) {
        try {
            get().click();
            // TODO debug();
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

}