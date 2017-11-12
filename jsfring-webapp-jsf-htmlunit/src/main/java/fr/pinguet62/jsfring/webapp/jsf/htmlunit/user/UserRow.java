package fr.pinguet62.jsfring.webapp.jsf.htmlunit.user;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.AbstractRow;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.user.popup.UserShowPopup;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.user.popup.UserUpdatePopup;

import java.time.LocalDateTime;
import java.util.function.Function;

public final class UserRow extends AbstractRow<UserShowPopup, UserUpdatePopup> {

    public UserRow(HtmlTableRow row) {
        super(row);
    }

    public String getEmail() {
        return getString(0);
    }

    public LocalDateTime getLastConnection() {
        return getDate(2);
    }

    @Override
    protected Function<HtmlPage, UserShowPopup> getPopupShowFactory() {
        return UserShowPopup::new;
    }

    @Override
    protected Function<HtmlPage, UserUpdatePopup> getPopupUpdateFactory() {
        return UserUpdatePopup::new;
    }

    public boolean isActive() {
        return getBoolean(1);
    }

}