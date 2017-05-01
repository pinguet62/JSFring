package fr.pinguet62.jsfring.webapp.jsf.htmlunit.field;

import static java.util.stream.Collectors.toList;

import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlListItem;

public final class ListField extends ReadOnlyField<HtmlDivision, List<String>> {

    public ListField(HtmlDivision div) {
        super(div);
    }

    /** @return The {@link HtmlListItem} label. */
    @Override
    public List<String> getValue() {
        @SuppressWarnings("unchecked")
        List<HtmlListItem> lis = (List<HtmlListItem>) html.getByXPath(
                "./div[contains(@class, 'ui-datalist-content')]/ul[contains(@class, 'ui-datalist-data')]/li");
        List<String> values = lis.stream().map(HtmlElement::asText).collect(toList());
        return values;
    }

}