package fr.pinguet62.jsfring.gui.htmlunit;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

import fr.pinguet62.jsfring.gui.htmlunit.profile.ProfilesPage;
import fr.pinguet62.jsfring.gui.htmlunit.right.RightsPage;
import fr.pinguet62.jsfring.gui.htmlunit.user.UsersPage;

public class AbstractPage {

    /**
     * Doesn't end with {@code "/"} character. So the sub-link must start with
     * {@code "/"}.
     */
    public static final String BASE_URL = "http://localhost:8080/JSFring";

    public static final boolean DEBUG = true;

    private static final String ERROR_XPATH = "//span[@class='ui-messages-error-summary']";

    private static final String INFO_XPATH = "//span[@class='ui-messages-info-summary']";

    public static AbstractPage get() {
        return new AbstractPage(null);
    }

    protected HtmlPage page;

    protected final WebClient webClient = new WebClient();

    /**
     * Constructor used by classes that inherit.
     * 
     * @param page The {@link HtmlPage HTML page} once on the target page.
     */
    protected AbstractPage(HtmlPage page) {
        this.page = page;
    }

    /**
     * To call after each page action.<br>
     * In {@link #DEBUG} mode: the HTML page content is written to temporary
     * file.
     * 
     * @param page The {@link HtmlPage HTML page} to write.
     */
    protected void debug() {
        if (!DEBUG)
            return;

        try {
            IOUtils.write(page.asXml(), new FileOutputStream("D:/Profiles/jpinguet/Downloads/out.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the <code>&lt;p:messages/&gt;</code> content.
     * 
     * @param xpath The XPath to find the tag.<br>
     *            Depends on message level.
     * @return The tag content.
     */
    private String getMessage(String xpath) {
        @SuppressWarnings("unchecked")
        List<HtmlSpan> spans = (List<HtmlSpan>) getPage().getByXPath(xpath);
        if (spans.isEmpty())
            return null;
        if (spans.size() > 1)
            throw new NavigatorException();
        return spans.get(0).getTextContent();
    }

    public String getMessageError() {
        return getMessage(ERROR_XPATH);
    }

    public String getMessageInfo() {
        return getMessage(INFO_XPATH);
    }

    public HtmlPage getPage() {
        return page;
    }

    public LoginPage gotoLoginPage() {
        try {
            page = webClient.getPage(BASE_URL + "/login.xhtml");
            debug();
            return new LoginPage(page);
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

    public ProfilesPage gotoProfilesPage() {
        try {
            page = webClient.getPage(BASE_URL + "/profile/list.xhtml");
            debug();
            return new ProfilesPage(page);
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

    public RightsPage gotoRightsPage() {
        try {
            page = webClient.getPage(BASE_URL + "/right/list.xhtml");
            debug();
            return new RightsPage(page);
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

    public UsersPage gotoUsersPage() {
        try {
            page = webClient.getPage(BASE_URL + "/user/list.xhtml");
            debug();
            return new UsersPage(page);
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

    protected void waitJS() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new NavigatorException(e);
        }
    }

}