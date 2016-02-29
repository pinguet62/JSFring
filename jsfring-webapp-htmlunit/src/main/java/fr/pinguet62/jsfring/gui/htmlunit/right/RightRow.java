package fr.pinguet62.jsfring.gui.htmlunit.right;

import java.util.function.Function;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import fr.pinguet62.jsfring.gui.htmlunit.datatable.AbstractRow;
import fr.pinguet62.jsfring.gui.htmlunit.right.popup.RightShowPopup;
import fr.pinguet62.jsfring.gui.htmlunit.right.popup.RightUpdatePopup;

public final class RightRow extends AbstractRow<RightShowPopup, RightUpdatePopup> {

    public RightRow(HtmlTableRow row) {
        super(row);
    }

    public String getCode() {
        return getString(0);
    }

    @Override
    protected Function<HtmlPage, RightShowPopup> getPopupShowFactory() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Function<HtmlPage, RightUpdatePopup> getPopupUpdateFactory() {
        throw new UnsupportedOperationException();
    }

    public String getTitle() {
        return getString(1);
    }

}