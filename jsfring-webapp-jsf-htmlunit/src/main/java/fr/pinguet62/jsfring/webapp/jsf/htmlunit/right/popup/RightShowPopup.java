package fr.pinguet62.jsfring.webapp.jsf.htmlunit.right.popup;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup.ShowPopup;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.Field;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.StringOutputText;

public final class RightShowPopup extends AbstractRightPopup implements ShowPopup {

    public RightShowPopup(HtmlPage page) {
        super(page);
    }

    public Field<?, ?> getTitle() {
        return new StringOutputText(getFieldTableCell(1).getFirstByXPath("./span"));
    }

}
