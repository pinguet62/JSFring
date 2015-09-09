package fr.pinguet62.jsfring.gui.htmlunit.right;

import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import fr.pinguet62.jsfring.gui.htmlunit.AbstractRow;

public final class RightRow extends AbstractRow {

    public RightRow(HtmlTableRow row) {
        super(row);
    }

    public String getCode() {
        return getString(0);
    }

    public String getTitle() {
        return getString(1);
    }

}