package fr.pinguet62.jsfring.webapp.jsf.htmlunit.profile.popup;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup.ShowPopup;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.Field;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.ListField;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.StringOutputText;

import java.util.List;

public class ProfileShowPopup extends AbstractProfilePopup implements ShowPopup {

    public ProfileShowPopup(HtmlPage page) {
        super(page);
    }

    public Field<?, List<String>> getRights() {
        return new ListField(getFieldTableCell(1).getFirstByXPath("./div[contains(@class, 'ui-datalist')]"));
    }

    public Field<?, ?> getTitle() {
        return new StringOutputText(getFieldTableCell(0).getFirstByXPath("./span"));
    }

}
