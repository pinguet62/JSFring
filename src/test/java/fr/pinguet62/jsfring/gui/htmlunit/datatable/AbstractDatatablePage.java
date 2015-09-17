package fr.pinguet62.jsfring.gui.htmlunit.datatable;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableHeaderCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import fr.pinguet62.jsfring.gui.component.DataTableComponent;
import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.NavigatorException;

/**
 * @param <T> The {@link AbstractRow} type.
 * @see DataTableComponent
 */
public abstract class AbstractDatatablePage<T extends AbstractRow<?, ?>> extends AbstractPage {

    protected AbstractDatatablePage(HtmlPage page) {
        super(page);
    }

    /**
     * Iterate on each {@link HtmlSpan} and stop when
     * {@code class="ui-state-active"}.
     * <p>
     * Search paginator by <i>XPath</i>:
     * <ol>
     * <li>Paginator: {@code div} avec une {@code class} de
     * {@code "ui-paginator"}</li>
     * <li>Footer: le 2nd</li>
     * <li>Liste des pages: {@code span} avec une {@code class} de
     * {@code "ui-paginator-pages"}</li>
     * <li>Page active: {@code span} avec une {@code class} de
     * {@code "ui-state-active"}</li>
     * </ol>
     */
    protected Iterator<HtmlSpan> getCurrentPage() {
        @SuppressWarnings("unchecked")
        List<HtmlSpan> paginator = (List<HtmlSpan>) getDatatable().getByXPath(
                "./div[contains(@class, 'ui-paginator')][2]/span[@class='ui-paginator-pages']/span");
        Iterator<HtmlSpan> it;
        for (it = paginator.iterator(); it.hasNext(); it.next())
            if (it.next().getAttribute("class").contains("ui-state-active"))
                break;
        return it;
    }

    /**
     * <code>&lt;div class="...ui-datatable..."&gt;</code>
     *
     * @return The {@link HtmlDivision}.
     */
    protected HtmlDivision getDatatable() {
        return (HtmlDivision) page.getByXPath("//div[contains(@class, 'ui-datatable')]").get(0);
    }

    /**
     * <code>&lt;div class="...ui-datatable-tablewrapper..."&gt;&lt;table&gt;</code>
     *
     * @return The {@link HtmlTable} wrapped into {@link DataTableComponent}.
     */
    protected HtmlTable getDatatableTable() {
        return (HtmlTable) getDatatable().getByXPath("./div[@class='ui-datatable-tablewrapper']/table").get(0);
    }

    /**
     * @param title The column title.
     * @return The {@link HtmlTableHeaderCell}.
     */
    protected HtmlTableHeaderCell getDatatableTableHeader(String title) {
        Optional<HtmlTableHeaderCell> find = getDatatableTableHeaders()
                .stream()
                .filter(th -> ((HtmlSpan) th.getByXPath("./span[contains(@class, 'ui-column-title')]").get(0)).asText().equals(
                        title)).findAny();
        return find.isPresent() ? find.get() : null;
    }

    /**
     * <code>&lt;thead&gt;&lt;tr&gt;&lt;th&gt;</code>
     *
     * @return The {@link HtmlTableHeaderCell}s.
     */
    @SuppressWarnings("unchecked")
    protected List<HtmlTableHeaderCell> getDatatableTableHeaders() {
        return (List<HtmlTableHeaderCell>) getDatatableTable().getByXPath("./thead/tr/th");
    }

    /**
     * Converter from {@link HtmlTableRow} to {@link AbstractRow}.
     *
     * @return The {@link Function}.
     */
    protected abstract Function<HtmlTableRow, T> getRowFactory();

    /**
     * <code>&lt;tbody&gt;&lt;tr&gt;</code>
     *
     * @return The {@link AbstractRow}s.
     */
    public List<T> getRows() {
        @SuppressWarnings("unchecked")
        List<HtmlTableRow> rows = (List<HtmlTableRow>) getDatatableTable().getByXPath("./tbody/tr");
        return rows.stream().map(getRowFactory()).collect(toList());
    }

    /** @throws NavigatorException No next page. See {@link #hasNextPage()}. */
    public void gotoNextPage() {
        Iterator<HtmlSpan> current = getCurrentPage();

        if (!current.hasNext())
            throw new NavigatorException("No next page. Test with hasNextPage() before each transition to next page.");

        HtmlSpan next = current.next();
        try {
            page = next.click();
            waitJS();
            debug();
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

    /** Check than a {@link HtmlSpan} exists after the current page. */
    public boolean hasNextPage() {
        return getCurrentPage().hasNext();
    }

    public boolean isCreateButtonVisible() {
        final String xpath = "./div[contains(@class, 'ui-datatable-header')]/button[contains(@onclick, 'createDialog')]";
        @SuppressWarnings("unchecked")
        List<HtmlButton> buttons = (List<HtmlButton>) getDatatable().getByXPath(xpath);
        if (buttons.size() >= 2)
            throw new IllegalArgumentException("More than 1 tag found with XPath: " + xpath);
        return !buttons.isEmpty();
    }

    /**
     * Find the {@link HtmlTableHeaderCell} from column title, and click to sort
     * results.
     * <p>
     * <code>&lt;span class="...ui-column-title..."&gt;</code>
     *
     * @param title The column title.
     */
    protected void sortBy(String title) {
        HtmlTableHeaderCell header = getDatatableTableHeader(title);
        try {
            page = header.click();
            waitJS();
            debug();
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

}