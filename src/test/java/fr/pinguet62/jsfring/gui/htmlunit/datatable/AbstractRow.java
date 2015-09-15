package fr.pinguet62.jsfring.gui.htmlunit.datatable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.google.common.collect.Lists;

import fr.pinguet62.jsfring.gui.component.DataTableComponent;
import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.DateUtils;
import fr.pinguet62.jsfring.gui.htmlunit.NavigatorException;
import fr.pinguet62.jsfring.gui.htmlunit.datatable.popup.ConfirmPopup;

/**
 * Commons data for {@link DataTableComponent}.
 *
 * @see DataTableComponent
 */
public abstract class AbstractRow<SP, UP> extends AbstractPage {

    private final HtmlTableRow row;

    /**
     * Initialize the "actions" cell.
     *
     * @param row The {@link HtmlTableRow table row}.
     */
    protected AbstractRow(HtmlTableRow row) {
        super(row.getHtmlPageOrNull());
        this.row = row;
    }

    /**
     * Click on "delete" action button.
     *
     * @throws UnsupportedOperationException "Delete" action not available.
     */
    public ConfirmPopup actionDelete() {
        HtmlButton button = getActionButtonDelete();

        if (button == null)
            throw new UnsupportedOperationException("Delete button not visible");

        try {
            HtmlPage page = button.click();
            debug();

            return new ConfirmPopup(page);
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

    /**
     * Click on "show" action button.
     *
     * @throws UnsupportedOperationException "Show" action not available.
     */
    public SP actionShow() {
        HtmlButton button = getActionButtonShow();

        if (button == null)
            throw new UnsupportedOperationException("Show button not visible");

        try {
            HtmlPage page = button.click();
            waitJS();
            debug();

            return getPopupShowFactory().apply(page);
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

    /**
     * Click on "update" action button.
     *
     * @throws UnsupportedOperationException "Update" action not available.
     */
    public UP actionUpdate() {
        HtmlButton button = getActionButtonUpdate();

        if (button == null)
            throw new UnsupportedOperationException("Update button not visible");

        try {
            HtmlPage page = button.click();
            waitJS();
            debug();

            return getPopupUpdateFactory().apply(page);
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

    /**
     * Find action {@link HtmlButton button}, in action column, by XPath.
     *
     * @param xpath The XPath to find {@link HtmlButton}.
     * @return The {@link HtmlButton}.<br>
     *         {@code null} if not found.
     * @throws NavigatorException More than 1 {@link HtmlButton} found.
     */
    private HtmlButton getActionButton(String xpath) {
        @SuppressWarnings("unchecked")
        List<HtmlTableCell> cells = (List<HtmlTableCell>) row.getByXPath("./td");
        HtmlTableDataCell actionsCell = (HtmlTableDataCell) cells.get(cells.size() - 1);
        @SuppressWarnings("unchecked")
        List<HtmlButton> elements = (List<HtmlButton>) actionsCell.getByXPath(xpath);
        if (elements.size() >= 2)
            throw new NavigatorException("More than 1 tag found with XPath: " + xpath);
        return elements.isEmpty() ? null : elements.get(0);
    }

    private HtmlButton getActionButtonDelete() {
        return getActionButton("./button[contains(@data-pfconfirmcommand, 'PrimeFaces.bcn(this')]");
    }

    private HtmlButton getActionButtonShow() {
        return getActionButton("./button[contains(@onclick, 'showDialog')]");
    }

    private HtmlButton getActionButtonUpdate() {
        return getActionButton("./button[contains(@onclick, 'updateDialog')]");
    }

    // TODO i18n
    /**
     * Get the {@link Boolean}.
     *
     * @param column The column index.
     * @return The {@link Boolean}.<br>
     *         {@code null} if empty cell.
     * @throws RuntimeException Unknown boolean format.
     */
    protected Boolean getBoolean(int column) {
        String content = getString(column);
        if (content == null)
            return null;

        // Parse
        if (Arrays.asList("true").contains(content))
            return Boolean.TRUE;
        if (Arrays.asList("false").contains(content))
            return Boolean.FALSE;
        throw new RuntimeException("Invalid boolean format: " + content);
    }

    /**
     * Get the {@link Date}.
     *
     * @param column The column index.
     * @return The {@link Date}.
     * @see DateUtils#parseDateOrDateTime(String)
     */
    protected Date getDate(int column) {
        return DateUtils.parseDateOrDateTime(getString(column));
    }

    /**
     * Converter from {@link HtmlPage} to {@link ShowPopup}.
     *
     * @return The {@link Function}.
     */
    protected abstract Function<HtmlPage, SP> getPopupShowFactory();

    /**
     * Converter from {@link HtmlPage} to {@link UpdatePopup}.
     *
     * @return The {@link Function}.
     */
    protected abstract Function<HtmlPage, UP> getPopupUpdateFactory();

    /**
     * Get the {@link String}.
     *
     * @param column The column index.
     * @return The {@link String}.<br>
     *         {@code null} if empty cell.
     */
    protected String getString(int column) {
        ArrayList<DomElement> tds = Lists.newArrayList(row.getChildElements());
        String content = tds.get(column).asText();
        return content.isEmpty() ? null : content;
    }

    public boolean isActionButtonDeleteVisible() {
        return getActionButtonDelete() != null;
    }

    public boolean isActionButtonShowVisible() {
        return getActionButtonShow() != null;
    }

    public boolean isActionButtonUpdateVisible() {
        return getActionButtonUpdate() != null;
    }

}