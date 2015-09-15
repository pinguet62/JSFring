package fr.pinguet62.jsfring.gui.htmlunit.datatable.popup;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;

public interface ShowPopup extends Popup {

    @Override
    default HtmlDivision getDialog() {
        return (HtmlDivision) getPage().getByXPath("//div[contains(@class, 'ui-dialog') and contains(@id, 'showDialog')]").get(
                0);
    }

    /**
     * Get the {@link HtmlTableDataCell}: from row index, in 2nd column.
     *
     * @param index The row index.<br>
     *            For 1st line: {@code index=0}.
     * @return The {@link HtmlTableDataCell}.
     */
    default HtmlTableDataCell getFieldTableCell(int index) {
        // "index+1" because XPath first index start is 1
        return (HtmlTableDataCell) getDialog().getByXPath(
                "./div[contains(@class, 'ui-dialog-content')]//table/tbody/tr[" + (index + 1) + "]/td[2]").get(0);
    }

}