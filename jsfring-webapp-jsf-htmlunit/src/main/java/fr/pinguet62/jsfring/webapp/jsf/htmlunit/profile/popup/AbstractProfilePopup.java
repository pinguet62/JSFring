package fr.pinguet62.jsfring.webapp.jsf.htmlunit.profile.popup;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup.DetailsPopup;

public abstract class AbstractProfilePopup extends AbstractPage implements DetailsPopup {

    protected AbstractProfilePopup(HtmlPage page) {
        super(page);
    }

}