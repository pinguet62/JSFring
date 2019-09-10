package fr.pinguet62.jsfring.webapp.jsf.htmlunit.field;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.NavigatorException;

import java.io.IOException;

public final class CheckBox extends ReadWriteField<HtmlDivision, Boolean> {

    public CheckBox(HtmlDivision div) {
        super(div);
    }

    /**
     * The {@link HtmlDivision} contains the {@code "ui-state-active"} class.
     */
    @Override
    public Boolean getValue() {
        HtmlInput input = html.getFirstByXPath("./div[1]/input");
        String value = input.getAttribute("aria-checked");
        if ("true".equals(value))
            return true;
        else if ("false".equals(value))
            return false;
        else
            throw new NavigatorException("Unknown CheckBox 'aria-checked' value: " + value);
    }

    @Override
    public void setValue(Boolean value) {
        if (getValue() == value)
            return;
        try {
            HtmlSpan span = html.getFirstByXPath("./div[2]/span[contains(@class, 'ui-chkbox-icon')]");
            HtmlPage page = span.click();
            debug(page);
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

}
