package fr.pinguet62.jsfring.gui.htmlunit;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public final class LoginPage extends AbstractPage {

    protected LoginPage(HtmlPage page) {
        super(page);
    }

    /**
     * Process to login.
     *
     * @param login The login.
     * @param password The password.
     * @return The target page.
     */
    public AbstractPage doLogin(String login, String password) {
        HtmlForm form = (HtmlForm) page.getByXPath("//form").get(0);
        form.getInputByName("username").setValueAttribute(login);
        form.getInputByName("password").setValueAttribute(password);
        HtmlButton submit = (HtmlButton) form.getByXPath(".//button[@type='submit']").get(0);

        try {
            page = submit.click();
            debug();
        } catch (IOException e) {
            throw new NavigatorException(e);
        }

        if (getMessageError() != null)
            return this;
        else
            return new IndexPage(page);
    }

    public ForgottenPasswordPage gotoForgottenPasswordPage() {
        HtmlAnchor anchor = (HtmlAnchor) page.getByXPath("//a[contains(@href, 'forgottenPassword.xhtml')]").get(0);
        try {
            page = anchor.click();
            debug();
        } catch (IOException e) {
            throw new NavigatorException(e);
        }

        return new ForgottenPasswordPage(page);
    }

}