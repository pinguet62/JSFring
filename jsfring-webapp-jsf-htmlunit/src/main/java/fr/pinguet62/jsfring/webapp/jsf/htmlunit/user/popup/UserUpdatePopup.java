package fr.pinguet62.jsfring.webapp.jsf.htmlunit.user.popup;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup.UpdatePopup;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.*;

import java.time.LocalDateTime;
import java.util.List;

public final class UserUpdatePopup extends AbstractUserPopup implements UpdatePopup {

    public UserUpdatePopup(HtmlPage page) {
        super(page);
    }

    public ReadWriteField<?, Boolean> getActive() {
        return new CheckBox((HtmlDivision) getFieldTableCell(1).getByXPath("./div[contains(@class, 'ui-chkbox')]").get(0));
    }

    public Field<?, String> getEmail() {
        return new StringOutputText((HtmlSpan) getFieldTableCell(0).getByXPath("./span").get(0));
    }

    public Field<?, LocalDateTime> getLastConnection() {
        return new DateOutputText((HtmlSpan) getFieldTableCell(2).getByXPath("./span").get(0));
    }

    public ReadWriteField<?, List<String>> getProfiles() {
        return new PickList((HtmlDivision) getFieldTableCell(3).getByXPath("./div").get(0));
    }

}