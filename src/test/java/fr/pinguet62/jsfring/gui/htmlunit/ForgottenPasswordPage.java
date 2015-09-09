package fr.pinguet62.jsfring.gui.htmlunit;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fr.pinguet62.jsfring.gui.ForgottenPasswordBean;

/** @see ForgottenPasswordBean */
public final class ForgottenPasswordPage extends AbstractPage {

    protected ForgottenPasswordPage(HtmlPage page) {
        super(page);
    }

    private HtmlForm getForm() {
        return (HtmlForm) page.getByXPath("//form").get(0);
    }

    public void setEmail(String email) {
        HtmlInput input = getForm().getFirstByXPath(".//input[contains(@id, ':email')]");
        input.setValueAttribute(email);
        debug();
    }

    public void submit() {
        HtmlButton submit = (HtmlButton) getForm().getByXPath(".//button[@type='submit']").get(0);
        try {
            page = submit.click();
            debug();
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

}