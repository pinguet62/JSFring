package fr.pinguet62.jsfring.gui.htmlunit.user.popup;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

import fr.pinguet62.jsfring.gui.htmlunit.datatable.popup.ShowPopup;
import fr.pinguet62.jsfring.gui.htmlunit.field.Field;
import fr.pinguet62.jsfring.gui.htmlunit.field.ListField;
import fr.pinguet62.jsfring.gui.htmlunit.field.OutputText;

public final class UserShowPopup extends AbstractUserPopup implements ShowPopup {

    public UserShowPopup(HtmlPage page) {
        super(page);
    }

    public Field<?, ?> getEmail() {
        return new OutputText((HtmlSpan) getFieldTableCell(1).getByXPath("./span").get(0));
    }

    public Field<?, ?> getProfiles() {
        return new ListField((HtmlDivision) getFieldTableCell(4).getByXPath("./div[contains(@class, 'ui-datalist')]").get(0));
    }

    public Field<?, ?> isActive() {
        return new OutputText((HtmlSpan) getFieldTableCell(2).getByXPath("./span").get(0));
    }

}