package fr.pinguet62.jsfring.gui.htmlunit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.SgmlPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJobManager;

import fr.pinguet62.jsfring.gui.htmlunit.profile.ProfilesPage;
import fr.pinguet62.jsfring.gui.htmlunit.right.RightsPage;
import fr.pinguet62.jsfring.gui.htmlunit.user.UsersPage;

public class AbstractPage {

    /**
     * Doesn't end with {@code "/"} character. So the sub-link must start with
     * {@code "/"}.
     */
    public static final String BASE_URL = "http://localhost:8080/JSFring";

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPage.class);

    private static final File TMP_FILE;

    /** Initialize the {@link #OUTPUT_STREAM}. */
    static {
        try {
            TMP_FILE = File.createTempFile("navigator-", null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LOGGER.debug("Temporary file: " + TMP_FILE);
    }

    public static void debug(HtmlInput html) {
        debug(html.getHtmlPageOrNull().asXml());
    }

    public static void debug(SgmlPage page) {
        debug(page.asXml());
    }

    public static void debug(String xml) {
        if (!LOGGER.isDebugEnabled())
            return;

        try {
            IOUtils.write(xml, new FileOutputStream(TMP_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
     * Executed in {@link Logger#isDebugEnabled() debug level} or less.<br>
     * {@link OutputStream#write(byte[]) write} {@link HtmlPage#asXml() content
     * of current page} into {@link #OUTPUT_STREAM target Stream}.
     *
     * @param page The {@link HtmlPage HTML page} to write.
     */
    protected void debug() {
        debug(page);
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
        List<HtmlSpan> spans = (List<HtmlSpan>) page.getByXPath(xpath);
        if (spans.isEmpty())
            return null;
        if (spans.size() > 1)
            throw new NavigatorException();
        return spans.get(0).asText();
    }

    public String getMessageError() {
        return getMessage("//span[@class='ui-messages-error-summary']");
    }

    public String getMessageInfo() {
        return getMessage("//span[@class='ui-messages-info-summary']");
    }

    public HtmlPage getPage() {
        return page;
    }

    public IndexPage gotoIndex() {
        try {
            page = webClient.getPage(BASE_URL);
            debug();
            return new IndexPage(page);
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
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
        LOGGER.debug("Wait JavaScript");
        final int period = 200 /* ms */;
        JavaScriptJobManager manager = page.getEnclosingWindow().getJobManager();
        for (int t = 0; manager.getJobCount() > 0 && t < 5_000 /* ms max */; t += period)
            try {
                LOGGER.trace("Wait " + t + "ms");
                Thread.sleep(period);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
    }
}