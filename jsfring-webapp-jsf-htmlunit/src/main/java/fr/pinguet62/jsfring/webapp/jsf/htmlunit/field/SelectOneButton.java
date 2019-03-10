package fr.pinguet62.jsfring.webapp.jsf.htmlunit.field;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.NavigatorException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage.Delay.SHORT;

/**
 * @param <T> The {@link Enum} type.
 */
public final class SelectOneButton<T> extends ReadWriteField<HtmlDivision, T> {

    private final Function<String, T> converter;

    /**
     * <code>&lt;div class="ui-selectonebutton..."&gt;</code>
     *
     * @param html      The {@link HtmlDivision}.
     * @param converter The {@link Function} used to convert label to value.
     */
    public SelectOneButton(HtmlDivision html, Function<String, T> converter) {
        super(html);
        this.converter = converter;
    }

    private List<HtmlDivision> getChoices() {
        return html.getByXPath("./div[contains(@class, 'ui-button')]");
    }

    /**
     * Find {@link HtmlDivision} with {@code class="ui-state-active"}.
     */
    @Override
    public T getValue() {
        Optional<HtmlDivision> find = getChoices().stream().filter(div -> div.getAttribute("class").contains("ui-state-active")).findAny();
        if (find.isPresent())
            return null;
        return converter.apply(find.get().asText());
    }

    @Override
    public void setValue(T value) {
        HtmlDivision button = getChoices().stream()
                .filter(div -> converter.apply((div.<HtmlSpan>getFirstByXPath("./span[contains(@class, 'ui-button-text')]")).getTextContent()).equals(value))
                .findAny().get();
        try {
            button.click();
            waitJS(SHORT);
            debug();
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

}
