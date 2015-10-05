package fr.pinguet62.jsfring.gui.htmlunit.field;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlListItem;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

import fr.pinguet62.jsfring.gui.htmlunit.NavigatorException;

public final class AutocompleteInputText extends ReadWriteField<HtmlDivision, List<String>> {

    public AutocompleteInputText(HtmlDivision html) {
        super(html);
    }

    @Override
    public List<String> getValue() {
        @SuppressWarnings("unchecked")
        List<HtmlSpan> tokens = (List<HtmlSpan>) html.getByXPath(
                "./ul[contains(@class, 'ui-autocomplete-multiple-container')]/li[contains(@class, 'ui-autocomplete-token')]/span[contains(@class, 'ui-autocomplete-token-label')]");
        return tokens.stream().map(span -> span.getTextContent()).collect(Collectors.toList());
    }

    // TODO reset initial values
    // TODO multiple auto-complete fields
    /**
     * @throws IllegalArgumentException If more than 1 item found into
     *             auto-complete list.
     */
    @Override
    public void setValue(List<String> values) {
        try {
            for (String value : values) {
                HtmlInput input = (HtmlInput) html
                        .getByXPath(
                                "./ul[contains(@class, 'ui-autocomplete-multiple-container')]/li[contains(@class, 'ui-autocomplete-token')]/span[contains(@class, 'ui-autocomplete-token-label')]")
                        .get(0);
                input.type(value);
                waitJS();
                debug();

                // Autocomplete
                @SuppressWarnings("unchecked")
                List<HtmlListItem> lis = (List<HtmlListItem>) html.getByXPath(
                        "//div[contains(@class, 'ui-autocomplete-panel')]/ul[contains(@class, 'ui-autocomplete-list')]/li");
                if (lis.size() > 1)
                    throw new IllegalArgumentException("More than 1 result found into auto-complemente results");
                page = lis.get(0).click();
                waitJS();
                debug();
            }
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

}