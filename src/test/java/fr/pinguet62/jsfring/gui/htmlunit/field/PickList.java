package fr.pinguet62.jsfring.gui.htmlunit.field;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlListItem;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.NavigatorException;

public final class PickList extends ReadWriteField<HtmlDivision, List<String>> {

    public PickList(HtmlDivision div) {
        super(div);
    }

    private void clickAddSelected() {
        HtmlButton button = getActionButton("ui-picklist-button-add");
        try {
            HtmlPage page = button.click();
            AbstractPage.debug(page);
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

    private void clickRemoveSelected() {
        HtmlButton button = getActionButton("ui-picklist-button-remove");
        try {
            HtmlPage page = button.click();
            AbstractPage.debug(page);
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

    /**
     * @param classValue The {@code "class"} tag attribute used to determinate
     *            what type of action is.
     */
    private HtmlButton getActionButton(String classValue) {
        return (HtmlButton) html
                .getByXPath("./div[contains(@class, 'ui-picklist')]/div[@class='ui-picklist-buttons']/div/button[contains(@class, 'ui-picklist-button-remove')][1]");
    }

    /**
     * @param classValue The {@code "class"} tag attribute used to determinate
     *            if it's the source or target list.
     */
    @SuppressWarnings("unchecked")
    private List<HtmlListItem> getList(String classValue) {
        return (List<HtmlListItem>) html.getByXPath("./div/ul[contains(@class, '" + classValue + "')]/li");
    }

    // private List<HtmlListItem> getSource() {
    // return getList("ui-picklist-source");
    // }

    private List<HtmlListItem> getTarget() {
        return getList("ui-picklist-target");
    }

    @Override
    public List<String> getValue() {
        return getTarget().stream().map(HtmlElement::asText).collect(Collectors.toList());
    }

    /** Warning: the item values must be unique. */
    @Override
    public void setValue(List<String> values) {
        try {
            // Remove old
            continue_removes: while (true) {
                for (HtmlListItem li : getTarget())
                    if (!values.contains(li.asText())) {
                        HtmlPage page = li.click();
                        AbstractPage.debug(page);
                        clickRemoveSelected();
                        break continue_removes;
                    }
                break; // no remove: stop
            }

            // Add new
            continue_adds: while (true) {
                for (HtmlListItem li : getTarget())
                    if (values.contains(li.asText())) {
                        HtmlPage page = li.click();
                        AbstractPage.debug(page);
                        clickAddSelected();
                        break continue_adds;
                    }
                break; // no add: stop
            }
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

}