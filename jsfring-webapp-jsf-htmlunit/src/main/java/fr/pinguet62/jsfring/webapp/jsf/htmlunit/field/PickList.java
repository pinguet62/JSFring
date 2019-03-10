package fr.pinguet62.jsfring.webapp.jsf.htmlunit.field;

import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlListItem;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.NavigatorException;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import static fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage.Delay.SHORT;
import static java.util.stream.Collectors.toList;

public final class PickList extends ReadWriteField<HtmlDivision, List<String>> {

    public PickList(HtmlDivision div) {
        super(div);
    }

    private void clickAddSelected() {
        HtmlButton button = getActionButton("ui-picklist-button-add");
        try {
            page = button.click();
            waitJS(SHORT);
            debug(page);
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

    private void clickRemoveSelected() {
        HtmlButton button = getActionButton("ui-picklist-button-remove");
        try {
            page = button.click();
            waitJS(SHORT);
            debug(page);
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

    /**
     * @param classValue The {@code "class"} tag attribute used to determinate what type of action is.
     */
    private HtmlButton getActionButton(String classValue) {
        return html.getFirstByXPath("./div[@class='ui-picklist-buttons']/div/button[contains(@class, '" + classValue + "')]");
    }

    /**
     * @param classValue The {@code "class"} tag attribute used to determinate if it's the source or target list.
     */
    private List<HtmlListItem> getList(String classValue) {
        return html.getByXPath("./div/ul[contains(@class, '" + classValue + "')]/li");
    }

    private List<HtmlListItem> getSource() {
        return getList("ui-picklist-source");
    }

    private List<HtmlListItem> getTarget() {
        return getList("ui-picklist-target");
    }

    @Override
    public List<String> getValue() {
        return getTarget().stream().map(HtmlElement::asText).collect(toList());
    }

    /**
     * Warning: the item values must be unique.
     */
    @Override
    public void setValue(List<String> values) {
        // Check unique
        if (values.size() != new HashSet<>(values).size())
            throw new IllegalArgumentException("There are duplicate values");

        try {
            { // Remove old
                List<HtmlListItem> lis = getTarget();
                for (int i = lis.size() - 1; 0 <= i; i--) {
                    HtmlListItem li = lis.get(i);
                    if (!values.contains(li.asText())) {
                        HtmlPage page = li.click();
                        debug(page);
                        clickRemoveSelected();
                    }
                }
            }

            { // Add new
                List<HtmlListItem> lis = getSource();
                for (int i = lis.size() - 1; 0 <= i; i--) {
                    HtmlListItem li = lis.get(i);
                    if (values.contains(li.asText())) {
                        HtmlPage page = li.click();
                        debug(page);
                        clickAddSelected();
                    }
                }
            }
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

}
