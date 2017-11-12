package fr.pinguet62.jsfring.webapp.jsf.htmlunit.right;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.AbstractDatatablePage;

import java.util.function.Function;

public final class RightsPage extends AbstractDatatablePage<RightRow, Void> {

    public RightsPage(HtmlPage page) {
        super(page);
    }

    @Override
    protected Function<HtmlPage, Void> getPopupCreateFactory() {
        throw new UnsupportedOperationException("Cannot create new Right.");
    }

    @Override
    protected Function<HtmlTableRow, RightRow> getRowFactory() {
        return RightRow::new;
    }

}