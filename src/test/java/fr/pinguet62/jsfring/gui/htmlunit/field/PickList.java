package fr.pinguet62.jsfring.gui.htmlunit.field;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlListItem;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;

import fr.pinguet62.jsfring.gui.htmlunit.NavigatorException;

public final class PickList extends ReadWriteField<List<String>> {

    public PickList(HtmlTableDataCell htmlTableDataCell) {
        super(htmlTableDataCell);
    }

    private void clickAddSelected() {
        HtmlButton button = getActionButton("ui-picklist-button-add");
        try {
            button.click();
            // TODO debug();
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

    private void clickRemoveSelected() {
        HtmlButton button = getActionButton("ui-picklist-button-remove");
        try {
            button.click();
            // TODO debug();
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

    /**
     * @param classValue The {@code "class"} tag attribute used to determinate
     *            what type of action is.
     */
    private HtmlButton getActionButton(String classValue) {
        return (HtmlButton) htmlTableDataCell
                .getByXPath("./div[contains(@class, 'ui-picklist')]/div[@class='ui-picklist-buttons']/div/button[contains(@class, 'ui-picklist-button-remove')][1]");
    }

    /**
     * @param classValue The {@code "class"} tag attribute used to determinate
     *            if it's the source or target list.
     */
    @SuppressWarnings("unchecked")
    private List<HtmlListItem> getList(String classValue) {
        return (List<HtmlListItem>) htmlTableDataCell
                .getByXPath("./div[contains(@class, 'ui-picklist')]/div/ul[contains(@class, '" + classValue + "')]/li");
    }

    private List<HtmlListItem> getSource() {
        return getList("ui-picklist-source");
    }

    private List<HtmlListItem> getTarget() {
        return getList("ui-picklist-target");
    }

    @Override
    public List<String> getValue() {
        return getSource().stream().map(HtmlElement::asText).collect(Collectors.toList());
    }

    /** Warning: the item values must be unique. */
    @Override
    public void setValue(List<String> values) {
        try {
            // Remove old
            continue_removes: while (true) {
                for (HtmlListItem li : getTarget())
                    if (!values.contains(li.asText())) {
                        li.click();
                        // TODO debug();
                        clickRemoveSelected();
                        break continue_removes;
                    }
                break; // no remove: stop
            }

            // Add new
            continue_adds: while (true) {
                for (HtmlListItem li : getTarget())
                    if (values.contains(li.asText())) {
                        li.click();
                        // TODO debug();
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