package fr.pinguet62.jsfring.gui.htmlunit.right.popup;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

import fr.pinguet62.jsfring.gui.htmlunit.datatable.popup.ShowPopup;
import fr.pinguet62.jsfring.gui.htmlunit.field.Field;
import fr.pinguet62.jsfring.gui.htmlunit.field.StringOutputText;

public final class RightShowPopup extends AbstractRightPopup implements ShowPopup {

    protected RightShowPopup(HtmlPage page) {
        super(page);
    }

    public Field<?, ?> getTitle() {
        return new StringOutputText((HtmlSpan) getFieldTableCell(1).getByXPath("./span").get(0));
    }

}