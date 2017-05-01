package fr.pinguet62.jsfring.webapp.jsf.htmlunit.right;

import java.util.function.Function;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.AbstractRow;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.right.popup.RightShowPopup;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.right.popup.RightUpdatePopup;

public final class RightRow extends AbstractRow<RightShowPopup, RightUpdatePopup> {

    public RightRow(HtmlTableRow row) {
        super(row);
    }

    public String getCode() {
        return getString(0);
    }

    @Override
    protected Function<HtmlPage, RightShowPopup> getPopupShowFactory() {
        return RightShowPopup::new;
    }

    @Override
    protected Function<HtmlPage, RightUpdatePopup> getPopupUpdateFactory() {
        return RightUpdatePopup::new;
    }

    public String getTitle() {
        return getString(1);
    }

}