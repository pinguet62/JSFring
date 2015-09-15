package fr.pinguet62.jsfring.gui.htmlunit.datatable.popup;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;

public interface UpdatePopup extends ShowPopup {

    @Override
    default HtmlDivision getDialog() {
        return (HtmlDivision) getPage().getByXPath("//div[contains(@class, 'ui-dialog') and contains(@id, 'updateDialog')]")
                .get(0);
    }

}