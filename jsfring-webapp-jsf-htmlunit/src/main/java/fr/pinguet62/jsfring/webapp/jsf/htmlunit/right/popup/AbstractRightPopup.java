package fr.pinguet62.jsfring.webapp.jsf.htmlunit.right.popup;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup.DetailsPopup;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.Field;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.StringOutputText;

public abstract class AbstractRightPopup extends AbstractPage implements DetailsPopup {

    protected AbstractRightPopup(HtmlPage page) {
        super(page);
    }

    public Field<?, String> getCode() {
        return new StringOutputText(getFieldTableCell(0).getFirstByXPath("./span"));
    }

}
