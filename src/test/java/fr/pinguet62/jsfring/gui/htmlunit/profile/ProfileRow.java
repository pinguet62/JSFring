package fr.pinguet62.jsfring.gui.htmlunit.profile;

import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import fr.pinguet62.jsfring.gui.htmlunit.AbstractRow;

public final class ProfileRow extends AbstractRow {

    public ProfileRow(HtmlTableRow row) {
        super(row);
    }

    public String getTitle() {
        return getString(0);
    }

}