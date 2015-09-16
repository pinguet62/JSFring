package fr.pinguet62.jsfring.gui.htmlunit.user;

import java.io.IOException;
import java.util.function.Function;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlTableHeaderCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import fr.pinguet62.jsfring.gui.htmlunit.NavigatorException;
import fr.pinguet62.jsfring.gui.htmlunit.datatable.AbstractDatatablePage;

public final class UsersPage extends AbstractDatatablePage<UserRow> {

    public UsersPage(HtmlPage page) {
        super(page);
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