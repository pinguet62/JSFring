package fr.pinguet62.jsfring.gui.htmlunit.datatable.popup;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.NavigatorException;

/** {@link Popup} for "Delete" action. */
public final class ConfirmPopup extends AbstractPage implements Popup {

    public ConfirmPopup(HtmlPage page) {
        super(page);
    }

    public void cancel() {
        clickOnButton("ui-confirmdialog-no");
    }

    /**
     * Find {@link HtmlButton} in footer by {@code class}.<br>
     * {@link HtmlButton#click() Click} on {@link HtmlButton}.
     *
     * @param classe The {@code class} attribute on which filter.
     */
    private void clickOnButton(String classe) {
        HtmlButton button = (HtmlButton) getDialog().getByXPath(
                "./div[contains(@class, 'ui-dialog-buttonpane')]/button[contains(@class, '" + classe + "')]").get(0);
        try {
            HtmlPage page = button.click();
            // TODO waitJS();
            AbstractPage.debug(page);
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

    public void confirm() {
        clickOnButton("ui-confirmdialog-yes");
    }

    @Override
    public HtmlDivision getDialog() {
        return (HtmlDivision) getPage().getByXPath("//div[contains(@class, 'ui-confirm-dialog')]").get(0);
    }

}