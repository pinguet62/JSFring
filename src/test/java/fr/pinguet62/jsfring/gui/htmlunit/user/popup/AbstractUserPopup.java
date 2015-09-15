package fr.pinguet62.jsfring.gui.htmlunit.user.popup;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

import fr.pinguet62.jsfring.gui.htmlunit.datatable.popup.AbstractSubmitPopup;
import fr.pinguet62.jsfring.gui.htmlunit.field.Field;
import fr.pinguet62.jsfring.gui.htmlunit.field.StringOutputText;

public abstract class AbstractUserPopup extends AbstractSubmitPopup {

    protected AbstractUserPopup(HtmlPage page) {
        super(page);
    }

    public Field<?, ?> getLastConnection() {
        return new StringOutputText((HtmlSpan) getFieldTableCell(3).getByXPath("./span").get(0));
    }

    public Field<?, ?> getLogin() {
        return new StringOutputText((HtmlSpan) getFieldTableCell(0).getByXPath("./span").get(0));
    }

}