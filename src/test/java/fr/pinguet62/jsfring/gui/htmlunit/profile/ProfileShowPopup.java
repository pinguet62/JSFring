package fr.pinguet62.jsfring.gui.htmlunit.profile;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.datatable.popup.ShowPopup;

public class ProfileShowPopup extends AbstractPage implements ShowPopup {

    protected ProfileShowPopup(HtmlPage page) {
        super(page);
    }

}