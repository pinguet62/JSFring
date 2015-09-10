package fr.pinguet62.jsfring.gui.htmlunit.user;

import java.util.function.Function;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import fr.pinguet62.jsfring.gui.htmlunit.datatable.AbstractDatatablePage;

public final class UsersPage extends AbstractDatatablePage<UserRow> {

    public UsersPage(HtmlPage page) {
        super(page);
    }

    @Override
    protected Function<HtmlTableRow, UserRow> getRowFactory() {
        return UserRow::new;
    }

}