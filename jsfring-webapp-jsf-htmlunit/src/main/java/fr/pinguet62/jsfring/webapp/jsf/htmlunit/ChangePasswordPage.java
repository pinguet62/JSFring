package fr.pinguet62.jsfring.webapp.jsf.htmlunit;

import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

public final class ChangePasswordPage extends AbstractPage {

    protected ChangePasswordPage(HtmlPage page) {
        super(page);
    }

    private HtmlForm getForm() {
        return page.getFirstByXPath("//form");
    }

    public void setActualPassword(String value) {
        getForm().<HtmlInput>getFirstByXPath(".//input[contains(@id, 'currentPassword')]").setValueAttribute(value);
    }

    public void setConfirmPassword(String value) {
        getForm().<HtmlInput>getFirstByXPath(".//input[contains(@id, 'newPasswordConfirmation')]").setValueAttribute(value);
    }

    public void setNewPassword(String value) {
        getForm().<HtmlInput>getFirstByXPath(".//input[contains(@id, 'newPassword')]").setValueAttribute(value);
    }

    public void submit() {
        HtmlButton submit = getForm().getFirstByXPath(".//button[@type='submit']");
        try {
            page = submit.click();
            debug(page);
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

}
