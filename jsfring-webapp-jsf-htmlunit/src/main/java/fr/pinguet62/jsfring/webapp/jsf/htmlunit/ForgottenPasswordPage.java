package fr.pinguet62.jsfring.webapp.jsf.htmlunit;

import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.InputText;

import java.io.IOException;

import static fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage.Delay.LONG;

public final class ForgottenPasswordPage extends AbstractPage {

    protected ForgottenPasswordPage(HtmlPage page) {
        super(page);
    }

    public InputText getEmail() {
        return new InputText(getForm().getFirstByXPath(".//input[contains(@id, ':email')]"));
    }

    private HtmlForm getForm() {
        return page.getFirstByXPath("//form");
    }

    public void submit() {
        HtmlButton submit = getForm().getFirstByXPath(".//button[@type='submit']");
        try {
            page = submit.click();
            waitJS(LONG);
            debug(page);
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

}
