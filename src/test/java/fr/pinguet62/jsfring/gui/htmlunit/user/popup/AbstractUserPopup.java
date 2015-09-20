package fr.pinguet62.jsfring.gui.htmlunit.user.popup;

import java.util.Date;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.datatable.popup.DetailsPopup;
import fr.pinguet62.jsfring.gui.htmlunit.field.DateOutputText;
import fr.pinguet62.jsfring.gui.htmlunit.field.Field;
import fr.pinguet62.jsfring.gui.htmlunit.field.StringOutputText;

public abstract class AbstractUserPopup extends AbstractPage implements DetailsPopup {

    protected AbstractUserPopup(HtmlPage page) {
        super(page);
    }

    public Field<?, Date> getLastConnection() {
        return new DateOutputText((HtmlSpan) getFieldTableCell(3).getByXPath("./span").get(0));
    }

    public Field<?, String> getLogin() {
        return new StringOutputText((HtmlSpan) getFieldTableCell(0).getByXPath("./span").get(0));
    }

}