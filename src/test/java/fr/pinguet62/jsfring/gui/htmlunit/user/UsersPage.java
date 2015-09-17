package fr.pinguet62.jsfring.gui.htmlunit.user;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlLabel;
import com.gargoylesoftware.htmlunit.html.HtmlListItem;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableHeaderCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import fr.pinguet62.jsfring.gui.htmlunit.NavigatorException;
import fr.pinguet62.jsfring.gui.htmlunit.datatable.AbstractDatatablePage;
import fr.pinguet62.jsfring.gui.htmlunit.field.SelectOneButton;

public final class UsersPage extends AbstractDatatablePage<UserRow> {

    public static enum ActiveFilter {
        All, No, Yes;
    }

    public static enum Column {

        ACTIONS("Actions"), ACTIVE("Actif"), EMAIL("Email"), LAST_CONNECTION("Dernière connexion"), LOGIN("Identifiant");

        static Column fromTitle(String title) {
            for (Column column : values())
                if (title.equals(column.title))
                    return column;
            throw new IllegalArgumentException("Unknown title: " + title);
        }

        private final String title;

        private Column(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

    }

    public UsersPage(HtmlPage page) {
        super(page);
    }

    public void filterByActive(ActiveFilter value) {
        // TODO common abstract parent method
        HtmlDivision div = (HtmlDivision) getDatatableTableHeader(Column.ACTIVE.getTitle()).getByXPath(
                "./div[contains(@class, 'ui-column-customfilter')]/div[contains(@class, 'ui-selectonebutton')]").get(0);
        SelectOneButton<ActiveFilter> selectOneButton = new SelectOneButton<ActiveFilter>(div, ActiveFilter::valueOf);
        selectOneButton.setValue(value);
    }

    /**
     * @param value The value to set.<br>
     *            {@code null} to reset filter.
     */
    public void filterLogin(String value) {
        HtmlInput input = (HtmlInput) getDatatableTableHeader(Column.LOGIN.getTitle()).getByXPath(
                "./input[contains(@class, 'ui-column-filter')]").get(0);
        input.setValueAttribute(value);
        waitJS();
        debug();
    }

    @Override
    protected Function<HtmlTableRow, UserRow> getRowFactory() {
        return UserRow::new;
    }

    public void hideOrShowColumn(Column column) {
        Function<String, Column> converter = Column::fromTitle;

        try {
            HtmlButton toggler = (HtmlButton) getDatatableHeader().getByXPath("./button[contains(@id, 'toggler')]").get(0);

            // Show
            page = toggler.click();
            debug();

            @SuppressWarnings("unchecked")
            List<HtmlListItem> choices = (List<HtmlListItem>) page
                    .getByXPath("//div[contains(@class, 'ui-columntoggler')]/ul[contains(@class, 'ui-columntoggler-items')]/li[contains(@class, 'ui-columntoggler-item')]");
            HtmlListItem choice = choices.stream()
                    .filter(li -> converter.apply(((HtmlLabel) li.getByXPath("./label")).asText()).equals(column)).findAny()
                    .get();
            choice.click();
            debug();

            // Hide Toogler
            page = toggler.click();
            debug();
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

    /**
     * Find the {@link HtmlTableHeaderCell} from column title, and click to sort
     * results.
     * <p>
     * <code>&lt;span class="...ui-column-title..."&gt;</code>
     *
     * @param title The column title.
     */
    public void sortBy(Column column) {
        sortBy(column.getTitle());
    }

}