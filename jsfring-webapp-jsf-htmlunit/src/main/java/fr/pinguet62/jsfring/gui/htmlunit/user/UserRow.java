package fr.pinguet62.jsfring.gui.htmlunit.user;

import java.util.Date;
import java.util.function.Function;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import fr.pinguet62.jsfring.gui.htmlunit.datatable.AbstractRow;
import fr.pinguet62.jsfring.gui.htmlunit.user.popup.UserShowPopup;
import fr.pinguet62.jsfring.gui.htmlunit.user.popup.UserUpdatePopup;

public final class UserRow extends AbstractRow<UserShowPopup, UserUpdatePopup> {

    public UserRow(HtmlTableRow row) {
        super(row);
    }

    public boolean isActive() {
        return getBoolean(2);
    }

    public String getEmail() {
        return getString(1);
    }

    public Date getLastConnection() {
        return getDate(3);
    }

    public String getLogin() {
        return getString(0);
    }

    @Override
    protected Function<HtmlPage, UserShowPopup> getPopupShowFactory() {
        return UserShowPopup::new;
    }

    @Override
    protected Function<HtmlPage, UserUpdatePopup> getPopupUpdateFactory() {
        return UserUpdatePopup::new;
    }

}