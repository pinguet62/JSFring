package fr.pinguet62.jsfring.webapp.jsf.htmlunit;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

public final class LoginPage extends AbstractPage {

    protected LoginPage(HtmlPage page) {
        super(page);
    }

    /**
     * Process to login.
     *
     * @param login    The login.
     * @param password The password.
     * @return The target page.
     */
    public AbstractPage doLogin(String login, String password) {
        HtmlForm form = page.getFirstByXPath("//form");
        form.getInputByName("username").setValueAttribute(login);
        form.getInputByName("password").setValueAttribute(password);
        HtmlButton submit = form.getFirstByXPath(".//button[@type='submit']");

        try {
            page = submit.click();
            debug(page);
        } catch (IOException e) {
            throw new NavigatorException(e);
        }

        if (getMessageError() != null)
            return this;
        else
            return new IndexPage(page);
    }

    public ForgottenPasswordPage gotoForgottenPasswordPage() {
        HtmlAnchor anchor = page.getFirstByXPath("//a[contains(@href, 'forgottenPassword.xhtml')]");
        try {
            page = anchor.click();
            debug(page);
        } catch (IOException e) {
            throw new NavigatorException(e);
        }

        return new ForgottenPasswordPage(page);
    }

}
