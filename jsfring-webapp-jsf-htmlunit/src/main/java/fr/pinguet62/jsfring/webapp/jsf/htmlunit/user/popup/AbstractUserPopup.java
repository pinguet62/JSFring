package fr.pinguet62.jsfring.webapp.jsf.htmlunit.user.popup;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup.DetailsPopup;

public abstract class AbstractUserPopup extends AbstractPage implements DetailsPopup {

    protected AbstractUserPopup(HtmlPage page) {
        super(page);
    }

}