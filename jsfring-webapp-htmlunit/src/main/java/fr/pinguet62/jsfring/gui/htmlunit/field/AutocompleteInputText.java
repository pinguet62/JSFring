package fr.pinguet62.jsfring.gui.htmlunit.field;

import static fr.pinguet62.jsfring.gui.htmlunit.AbstractPage.Delay.MEDIUM;

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

    /** Get the {@link HtmlListItem} containing values. */
    @SuppressWarnings("unchecked")
    private List<HtmlListItem> getTokens() {
        return (List<HtmlListItem>) html.getByXPath(
                "./ul[contains(@class, 'ui-autocomplete-multiple-container')]/li[contains(@class, 'ui-autocomplete-token')]");
    }

    @Override
    public List<String> getValue() {
        return getTokens().stream()
                .map(li -> ((HtmlSpan) li.getByXPath("./span[contains(@class, 'ui-autocomplete-token-label')]").get(0))
                        .getTextContent())
                .collect(Collectors.toList());
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
            // Reset value
            for (HtmlListItem li : getTokens()) {
                HtmlSpan span = (HtmlSpan) li.getByXPath("./span[contains(@class, 'ui-autocomplete-token-icon')]");
                page = span.click();
                debug();
            }

            // New values
            for (String value : values) {
                HtmlInput input = (HtmlInput) html
                        .getByXPath(
                                "./ul[contains(@class, 'ui-autocomplete-multiple-container')]/li[contains(@class, 'ui-autocomplete-input-token')]/input")
                        .get(0);
                input.type(value);
                waitJS(MEDIUM);
                debug();

                // Autocomplete
                @SuppressWarnings("unchecked")
                List<HtmlListItem> lis = (List<HtmlListItem>) page.getByXPath(
                        "//div[contains(@class, 'ui-autocomplete-panel')]/ul[contains(@class, 'ui-autocomplete-list')]/li");
                if (lis.size() > 1)
                    throw new IllegalArgumentException("More than 1 result found into auto-complemente results");
                page = lis.get(0).click();
                waitJS(MEDIUM);
                debug();
            }
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

}