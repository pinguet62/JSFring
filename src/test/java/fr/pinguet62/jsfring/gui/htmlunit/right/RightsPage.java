package fr.pinguet62.jsfring.gui.htmlunit.right;

import java.util.function.Function;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import fr.pinguet62.jsfring.gui.htmlunit.datatable.AbstractDatatablePage;

public final class RightsPage extends AbstractDatatablePage<RightRow> {

    public RightsPage(HtmlPage page) {
        super(page);
    }

    @Override
    protected Function<HtmlTableRow, RightRow> getRowFactory() {
        return RightRow::new;
    }

}