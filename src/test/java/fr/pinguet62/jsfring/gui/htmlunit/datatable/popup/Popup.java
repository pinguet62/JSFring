package fr.pinguet62.jsfring.gui.htmlunit.datatable.popup;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public interface Popup {

    HtmlDivision getDialog();

    HtmlPage getPage();

}