package fr.pinguet62.jsfring.webapp.jsf.htmlunit.field;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.NavigatorException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage.Delay.SHORT;

public final class SelectOneMenu extends ReadWriteField<HtmlDivision, String> {

    private final String id;

    /**
     * <code>&lt;div class="ui-selectonemenu..."&gt;</code>
     *
     * @param html The {@link HtmlDivision}.
     */
    public SelectOneMenu(HtmlDivision html) {
        super(html);
        id = html.getId();
    }

    private List<KeyLabelIndex> getItems() {
        List<HtmlOption> options = page.getByXPath("//select[@id='" + id + "_input']/option");
        List<KeyLabelIndex> items = new ArrayList<>();
        int index = 0;
        for (HtmlOption option : options) {
            items.add(new KeyLabelIndex(index, option.getAttribute("value"), option.asText(), "selected".equals(option.getAttribute("selected"))));
            index++;
        }
        return items;
    }

    @Override
    public String getValue() {
        return getItems().stream().filter(i -> i.isSelected()).findFirst().get().getKey();
    }

    /**
     * @param key The key of {@link HtmlOption}.
     */
    @Override
    public void setValue(String key) {
        KeyLabelIndex item = getItems().stream().filter(i -> i.getKey().equals(key)).findFirst().get();
        int index = item.getIndex();

        HtmlElement tr = (HtmlElement) page.getElementById(id + "_" + index);
        try {
            page = tr.click();
            waitJS(SHORT);
            debug(page);
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

}
