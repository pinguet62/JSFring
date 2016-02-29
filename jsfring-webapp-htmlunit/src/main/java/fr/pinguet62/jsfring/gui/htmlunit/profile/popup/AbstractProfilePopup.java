package fr.pinguet62.jsfring.gui.htmlunit.profile.popup;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.datatable.popup.DetailsPopup;

public abstract class AbstractProfilePopup extends AbstractPage implements DetailsPopup {

    protected AbstractProfilePopup(HtmlPage page) {
        super(page);
    }

}