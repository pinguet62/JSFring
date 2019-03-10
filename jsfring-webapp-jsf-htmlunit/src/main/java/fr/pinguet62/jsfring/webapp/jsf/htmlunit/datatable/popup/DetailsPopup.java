package fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup;

import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;

/**
 * {@link Popup} who contains {@link HtmlTable} with details.
 */
public interface DetailsPopup extends Popup {

    /**
     * Get the {@link HtmlTableDataCell}: from row index, in 2nd column.
     *
     * @param index The row index.<br>
     *              For 1st line: {@code index=0}.
     * @return The {@link HtmlTableDataCell}.
     */
    default HtmlTableDataCell getFieldTableCell(int index) {
        // "index+1" because XPath first index start is 1
        return getDialog().getFirstByXPath("./div[contains(@class, 'ui-dialog-content')]//table/tbody/tr[" + (index + 1) + "]/td[2]");
    }

}
