package fr.pinguet62.jsfring.gui.htmlunit.right.popup;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.datatable.popup.DetailsPopup;
import fr.pinguet62.jsfring.gui.htmlunit.field.Field;
import fr.pinguet62.jsfring.gui.htmlunit.field.StringOutputText;

public abstract class AbstractRightPopup extends AbstractPage implements DetailsPopup {

    protected AbstractRightPopup(HtmlPage page) {
        super(page);
    }

    public Field<?, String> getCode() {
        return new StringOutputText((HtmlSpan) getFieldTableCell(0).getByXPath("./span").get(0));
    }

}