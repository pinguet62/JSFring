package fr.pinguet62.jsfring.gui.htmlunit.user;

import java.io.IOException;
import java.util.function.Function;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlTableHeaderCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import fr.pinguet62.jsfring.gui.htmlunit.NavigatorException;
import fr.pinguet62.jsfring.gui.htmlunit.datatable.AbstractDatatablePage;
import fr.pinguet62.jsfring.gui.htmlunit.field.SelectOneButton;

public final class UsersPage extends AbstractDatatablePage<UserRow> {

    public static enum ActiveFilter {
        All, No, Yes;
    }

    public UsersPage(HtmlPage page) {
        super(page);
    }

    public void filterByActive(ActiveFilter value) {
        // TODO common abstract parent method
        HtmlTableHeaderCell headerCell = getDatatableTableHeader().get(2);
        HtmlDivision div = (HtmlDivision) headerCell.getByXPath(
                "./div[contains(@class, 'ui-column-customfilter')]/div[contains(@class, 'ui-selectonebutton')]").get(0);
        SelectOneButton<ActiveFilter> selectOneButton = new SelectOneButton<ActiveFilter>(div, ActiveFilter::valueOf);
        selectOneButton.setValue(value);
    }

    @Override
    protected Function<HtmlTableRow, UserRow> getRowFactory() {
        return UserRow::new;
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
        HtmlTableHeaderCell header = getDatatableTableHeader()
                .stream()
                .filter(th -> ((HtmlSpan) th.getByXPath("./span[contains(@class, 'ui-column-title')]").get(0)).asText().equals(
                        title)).findAny().get();
        try {
            page = header.click();
            waitJS();
            debug();
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

    public void sortByEmail() {
        sortBy("Email");
    }

    public void sortByLogin() {
        sortBy("Identifiant");
    }

}