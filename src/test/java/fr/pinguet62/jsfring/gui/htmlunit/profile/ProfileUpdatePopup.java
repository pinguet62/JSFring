package fr.pinguet62.jsfring.gui.htmlunit.profile;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.datatable.popup.UpdatePopup;

public class ProfileUpdatePopup extends AbstractPage implements UpdatePopup {

    protected ProfileUpdatePopup(HtmlPage page) {
        super(page);
    }

}