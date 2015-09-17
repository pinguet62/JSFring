package fr.pinguet62.jsfring.gui.htmlunit.user;

import java.util.function.Function;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import fr.pinguet62.jsfring.gui.htmlunit.datatable.AbstractDatatablePage;

public final class UsersPage extends AbstractDatatablePage<UserRow> {

    public UsersPage(HtmlPage page) {
        super(page);
    }

    /**
     * @param value The value to set.<br>
     *            {@code null} to reset filter.
     */
    public void filterLogin(String value) {
        HtmlInput input = (HtmlInput) getDatatableTableHeader("Identifiant").getByXPath(
                "./input[contains(@class, 'ui-column-filter')]").get(0);
        input.setValueAttribute(value);
        waitJS();
        debug();
    }

    @Override
    protected Function<HtmlTableRow, UserRow> getRowFactory() {
        return UserRow::new;
    }

    public void sortByEmail() {
        sortBy("Email");
    }

    public void sortByLogin() {
        sortBy("Identifiant");
    }

}