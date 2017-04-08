package fr.pinguet62.jsfring.gui.htmlunit;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public final class ChangePasswordPage extends AbstractPage {

    protected ChangePasswordPage(HtmlPage page) {
        super(page);
    }

    private HtmlForm getForm() {
        return (HtmlForm) page.getByXPath("//form").get(0);
    }

    public void setActualPassword(String value) {
        ((HtmlInput) getForm().getByXPath(".//input[contains(@id, 'currentPassword')]").get(0))
        .setValueAttribute(value);
    }

    public void setConfirmPassword(String value) {
        ((HtmlInput) getForm().getByXPath(".//input[contains(@id, 'newPasswordConfirmation')]").get(0))
        .setValueAttribute(value);
    }

    public void setNewPassword(String value) {
        ((HtmlInput) getForm().getByXPath(".//input[contains(@id, 'newPassword')]").get(0)).setValueAttribute(value);
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