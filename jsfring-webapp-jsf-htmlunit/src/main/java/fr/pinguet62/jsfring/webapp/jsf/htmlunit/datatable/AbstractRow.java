package fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable;

import com.gargoylesoftware.htmlunit.html.*;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.DateUtils;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.NavigatorException;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup.ConfirmPopup;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup.ShowPopup;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup.UpdatePopup;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import static fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage.Delay.MEDIUM;
import static fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage.Delay.SHORT;
import static fr.pinguet62.jsfring.webapp.jsf.htmlunit.DateUtils.DATETIME_FORMATTER;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Row of datatable of {@link AbstractDatatablePage}.
 *
 * @param <SP> The {@link ShowPopup} type.
 * @param <UP> The {@link UpdatePopup} type.
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
     * @return The {@link ConfirmPopup}.
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
     * @return The {@link ShowPopup}.
     * @throws UnsupportedOperationException "Show" action not available.
     */
    public SP actionShow() {
        HtmlButton button = getActionButtonShow();

        if (button == null)
            throw new UnsupportedOperationException("Show button not visible");

        try {
            HtmlPage page = button.click();
            waitJS(SHORT);
            debug();

            return getPopupShowFactory().apply(page);
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

    /**
     * Click on "update" action button.
     *
     * @return The {@link UpdatePopup}.
     * @throws UnsupportedOperationException "Update" action not available.
     */
    public UP actionUpdate() {
        HtmlButton button = getActionButtonUpdate();

        if (button == null)
            throw new UnsupportedOperationException("Update button not visible");

        try {
            HtmlPage page = button.click();
            waitJS(MEDIUM);
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
     * {@code null} if not found.
     * @throws NavigatorException More than 1 {@link HtmlButton} found.
     */
    private HtmlButton getActionButton(String xpath) {
        List<HtmlTableCell> cells = row.getByXPath("./td");
        HtmlTableDataCell actionsCell = (HtmlTableDataCell) cells.get(cells.size() - 1);
        List<HtmlButton> elements = actionsCell.getByXPath(xpath);
        if (elements.size() > 1)
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
     * {@code null} if empty cell.
     * @throws NavigatorException Unknown boolean format.
     */
    protected Boolean getBoolean(int column) {
        String content = getString(column);
        if (content == null)
            return null;

        // Parse
        if (asList("true").contains(content))
            return TRUE;
        if (asList("false").contains(content))
            return FALSE;
        throw new NavigatorException("Invalid boolean format: " + content);
    }

    /**
     * Get the {@link LocalDateTime}.
     *
     * @param column The column index.
     * @return The {@link LocalDateTime}.
     * @see DateUtils#parseDateOrDateTime(String)
     */
    protected LocalDateTime getDate(int column) {
        String value = getString(column);
        if (isBlank(value))
            return null;
        return LocalDateTime.parse(value, DATETIME_FORMATTER);
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
     * {@code null} if empty cell.
     */
    protected String getString(int column) {
        List<DomElement> tds = StreamSupport.stream(row.getChildElements().spliterator(), false).collect(toList());
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