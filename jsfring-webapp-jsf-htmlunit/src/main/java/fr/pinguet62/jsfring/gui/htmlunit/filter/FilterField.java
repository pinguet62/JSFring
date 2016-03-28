package fr.pinguet62.jsfring.gui.htmlunit.filter;

import static fr.pinguet62.jsfring.gui.htmlunit.AbstractPage.Delay.MEDIUM;
import static fr.pinguet62.jsfring.gui.htmlunit.AbstractPage.Delay.SHORT;

import java.io.IOException;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.NavigatorException;

public class FilterField extends AbstractPage {

    private final int index;

    FilterField(HtmlPage page, int index) {
        super(page);
        this.index = index;
    }

    /** @param i The column index. */
    private HtmlTableDataCell getColumn(int i) {
        return (HtmlTableDataCell) getFilter().getByXPath("./td").get(i);
    }

    private HtmlTableRow getFilter() {
        return (HtmlTableRow) page.getByXPath("//form/table/tbody/tr").get(index);
    }

    // FIXME "div/label/span" instead of "div/span" after submit
    @SuppressWarnings("unchecked")
    private List<HtmlInput> getInputValues() {
        return (List<HtmlInput>) getColumn(2).getByXPath("./div//span/input[contains(@class, 'ui-inputtext')]");
    }

    public int getNumberOfArguments() {
        return getInputValues().size();
    }

    public String getQuery() {
        return getColumn(4).getTextContent();
    }

    /**
     * Test if the field is not valid.<br>
     * Check the red border, by checking the {@code class} attribute.
     * 
     * @return The result.
     */
    public boolean isError() {
        return getInputValues().stream().anyMatch(input -> input.getAttribute("class").contains("ui-state-error"));
    }

    /** @param operator The {@link Class class} of operator. */
    public void setOperator(Class<?> operator) {
        String value = operator == null ? "" : operator.getName();
        HtmlSelect select = (HtmlSelect) getColumn(2)
                .getByXPath(
                        "./div/div[contains(@class, 'ui-selectonemenu')]/div[@class='ui-helper-hidden-accessible']/select")
                .get(0);
        page = select.setSelectedAttribute(value, true);
        waitJS(SHORT);
        debug();
    }

    /**
     * Set value to argument.
     *
     * @param index The argument index.
     * @param value The argument value.
     */
    public void setValue(int index, String value) {
        page = (HtmlPage) getInputValues().get(index).setValueAttribute(value);
        debug();
    }

    public void submit() {
        HtmlButton button = (HtmlButton) getColumn(3).getByXPath("./button").get(0);
        try {
            page = button.click();
            waitJS(MEDIUM);
            debug();
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

}