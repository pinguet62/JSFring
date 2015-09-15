package fr.pinguet62.jsfring.gui.htmlunit.profile;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fr.pinguet62.jsfring.gui.htmlunit.datatable.popup.UpdatePopup;
import fr.pinguet62.jsfring.gui.htmlunit.user.popup.AbstractUserPopup;

public class ProfileUpdatePopup extends AbstractUserPopup implements UpdatePopup {

    protected ProfileUpdatePopup(HtmlPage page) {
        super(page);
    }

}