package fr.pinguet62.jsfring.webapp.jsf.htmlunit.profile.popup;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
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
        return new ListField(
                (HtmlDivision) getFieldTableCell(1).getByXPath("./div[contains(@class, 'ui-datalist')]").get(0));
    }

    public Field<?, ?> getTitle() {
        return new StringOutputText((HtmlSpan) getFieldTableCell(0).getByXPath("./span").get(0));
    }

}