package fr.pinguet62.jsfring.gui.htmlunit.field;

import static fr.pinguet62.jsfring.gui.htmlunit.AbstractPage.Delay.SHORT;

import java.io.IOException;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import fr.pinguet62.jsfring.gui.htmlunit.NavigatorException;

public final class ThemeSwitcher extends ReadWriteField<HtmlDivision, String> {

    private final String id;

    /** <code>&lt;div class="ui-selectonemenu..."&gt;</code> */
    public ThemeSwitcher(HtmlDivision html) {
        super(html);
        id = html.getId();
    }

    @SuppressWarnings("unchecked")
    private List<HtmlOption> getOptions() {
        return (List<HtmlOption>) page.getByXPath("//select[@id='" + id + "_input']/option");
    }

    @Override
    public String getValue() {
        HtmlOption option = getOptions().stream().filter(opt -> opt.getAttribute("selected").equals("selected"))
                .findFirst().get();
        return option.getAttribute("value");
    }

    @Override
    public void setValue(String value) {
        // Find item index from key
        int index = 0;
        for (HtmlOption option : getOptions()) {
            String key = option.getAttribute("value");
            if (key.equals(value))
                break;
            index++;
        }
        if (index > getOptions().size() - 1)
            throw new IllegalArgumentException("Value not found: " + value);

        // Find <li>
        HtmlTableRow tr = (HtmlTableRow) page
                .getByXPath("//div[@id='" + id + "_panel']/div/table/tbody/tr[@id='" + id + "_" + index + "']").get(0);
        try {
            tr.click();
            waitJS(SHORT);
            debug();
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

}