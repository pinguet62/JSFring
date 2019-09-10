package fr.pinguet62.jsfring.webapp.jsf.htmlunit.field;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlListItem;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.NavigatorException;

import java.io.IOException;
import java.util.List;

import static fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage.Delay.MEDIUM;
import static java.util.stream.Collectors.toList;

public final class AutocompleteInputText extends ReadWriteField<HtmlDivision, List<String>> {

    public AutocompleteInputText(HtmlDivision html) {
        super(html);
    }

    /**
     * Get the {@link HtmlListItem} containing values.
     */
    private List<HtmlListItem> getTokens() {
        return html.getByXPath("./ul[contains(@class, 'ui-autocomplete-multiple-container')]/li[contains(@class, 'ui-autocomplete-token')]");
    }

    @Override
    public List<String> getValue() {
        return getTokens().stream()
                .map(li -> li.<HtmlSpan>getFirstByXPath("./span[contains(@class, 'ui-autocomplete-token-label')]"))
                .map(HtmlSpan::getTextContent)
                .collect(toList());
    }

    // TODO reset initial values
    // TODO multiple auto-complete fields

    /**
     * @throws IllegalArgumentException If more than 1 item found into auto-complete list.
     */
    @Override
    public void setValue(List<String> values) {
        try {
            // Reset value
            for (HtmlListItem li : getTokens()) {
                HtmlSpan span = li.getFirstByXPath("./span[contains(@class, 'ui-autocomplete-token-icon')]");
                page = span.click();
                debug(page);
            }

            // New values
            for (String value : values) {
                HtmlInput input = html.getFirstByXPath("./ul[contains(@class, 'ui-autocomplete-multiple-container')]/li[contains(@class, 'ui-autocomplete-input-token')]/input");
                input.type(value);
                waitJS(MEDIUM);
                debug();

                // Autocomplete
                List<HtmlListItem> lis = page.getByXPath("//span[contains(@class, 'ui-autocomplete-panel')]/ul[contains(@class, 'ui-autocomplete-list')]/li");
                if (lis.size() > 1)
                    throw new IllegalArgumentException("More than 1 result found into auto-complemente results");
                page = lis.get(0).click();
                waitJS(MEDIUM);
                debug(page);
            }
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

}
