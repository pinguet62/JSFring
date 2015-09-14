package fr.pinguet62.jsfring.gui.htmlunit.user.popup;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;

import fr.pinguet62.jsfring.gui.htmlunit.field.Field;
import fr.pinguet62.jsfring.gui.htmlunit.field.StringOutputText;

public abstract class AbstractUserPopup {

    protected final HtmlPage page;

    protected AbstractUserPopup(HtmlPage page) {
        this.page = page;
    }

    protected abstract String getDialogId();

    /**
     * Get the {@link HtmlTableDataCell}: from row index, in 2nd column.
     *
     * @param index The row index.<br>
     *            For 1st line: {@code index=0}
     * @return The {@link HtmlTableDataCell}.
     */
    protected HtmlTableDataCell getFieldTableCell(int index) {
        // "index+1" because XPath first index start is 1
        return (HtmlTableDataCell) page.getByXPath(
                "//div[contains(@id, '" + getDialogId()
                + "') and contains(@class, 'ui-dialog')]//div[contains(@class, 'ui-dialog-content')]//table/tbody/tr["
                + (index + 1) + "]/td[2]").get(0);
    }

    public Field<?, ?> getLastConnection() {
        return new StringOutputText((HtmlSpan) getFieldTableCell(3).getByXPath("./span").get(0));
    }

    public Field<?, ?> getLogin() {
        return new StringOutputText((HtmlSpan) getFieldTableCell(0).getByXPath("./span").get(0));
    }

}