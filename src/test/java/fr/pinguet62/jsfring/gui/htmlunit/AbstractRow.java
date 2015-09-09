package fr.pinguet62.jsfring.gui.htmlunit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.google.common.collect.Lists;

import fr.pinguet62.jsfring.gui.component.DataTableComponent;

/**
 * Commons data for {@link DataTableComponent}.
 * 
 * @see DataTableComponent
 */
public abstract class AbstractRow {

    private final HtmlTableRow row;

    /**
     * Initialize the "actions" cell.
     * 
     * @param row The {@link HtmlTableRow table row}.
     */
    protected AbstractRow(HtmlTableRow row) {
        this.row = row;
    }

    /**
     * Check if the XPath {@link HtmlElement} is present.
     * 
     * @param xpath The XPath to find element in row.
     * @return {@code false} if the tag is not found<br>
     *         {@code true} if the tag is found
     * @throws IllegalArgumentException More than 1 tags found.
     */
    private boolean actionVisible(String xpath) {
        List<HtmlElement> cells = row.getElementsByTagName("td");
        HtmlTableDataCell actionCell = (HtmlTableDataCell) cells.get(cells.size() - 1);
        @SuppressWarnings("unchecked")
        List<HtmlElement> elements = (List<HtmlElement>) actionCell.getByXPath("./button[contains(@onclick, 'showDialog')]");
        if (elements.size() >= 2)
            throw new IllegalArgumentException("More than 1 tag found with XPath: " + xpath);
        return !elements.isEmpty();
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

    // TODO i18n
    /**
     * Get the {@link Date}.
     * 
     * @param column The column index.
     * @return The {@link Date}.<br>
     *         {@code null} if empty cell.
     * @throws RuntimeException Invalid/Unknown date format.
     */
    protected Date getDate(int column) {
        String content = getString(column);
        if (content == null)
            return null;

        // Parse
        try {
            // Date
            if (content.length() == 10)
                return new SimpleDateFormat("dd-MM-yyyy").parse(content);

            // Datetime
            // TODO datetime

            throw new RuntimeException("Unknown date format: " + content);
        } catch (ParseException exception) {
            throw new RuntimeException("Invalid date format: " + content, exception);
        }
    }

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

    public boolean isDeleteButtonVisible() {
        return actionVisible("./button[contains(@data-pfconfirmcommand, 'PrimeFaces.bcn(this')]");
    }

    public boolean isShowButtonVisible() {
        return actionVisible("./button[contains(@onclick, 'showDialog')]");
    }

    public boolean isUpdateButtonVisible() {
        return actionVisible("./button[contains(@onclick, 'updateDialog')]");
    }

}