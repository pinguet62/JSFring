package fr.pinguet62.jsfring.gui.htmlunit.field;

import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;

public final class InputText extends ReadWriteField<String> {

    public InputText(HtmlTableDataCell htmlTableDataCell) {
        super(htmlTableDataCell);
    }

    @Override
    public String getValue() {
        return htmlTableDataCell.asText();
    }

    @Override
    public void setValue(String value) {
        htmlTableDataCell.setTextContent(value);
    }

}
