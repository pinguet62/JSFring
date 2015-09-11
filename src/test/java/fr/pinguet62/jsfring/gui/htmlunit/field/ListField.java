package fr.pinguet62.jsfring.gui.htmlunit.field;

import java.util.List;
import java.util.stream.Collectors;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlListItem;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;

public final class ListField extends ReadOnlyField<List<String>> {

    public ListField(HtmlTableDataCell htmlTableDataCell) {
        super(htmlTableDataCell);
    }

    @Override
    public List<String> getValue() {
        @SuppressWarnings("unchecked")
        List<HtmlListItem> lis = (List<HtmlListItem>) htmlTableDataCell
                .getByXPath("./div[contains(@class, 'ui-datalist')]/div[contains(@class, 'ui-datalist-content')]/ul[contains(@class, 'ui-datalist-data')]/li");
        List<String> values = lis.stream().map(HtmlElement::asText).collect(Collectors.toList());
        return values;
    }

}