package fr.pinguet62.jsfring.gui.htmlunit.user;

import java.util.Date;

import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import fr.pinguet62.jsfring.gui.htmlunit.AbstractRow;

public final class UserRow extends AbstractRow {

    public UserRow(HtmlTableRow row) {
        super(row);
    }

    public boolean getActive() {
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

}