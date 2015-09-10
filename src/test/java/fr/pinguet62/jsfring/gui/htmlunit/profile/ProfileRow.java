package fr.pinguet62.jsfring.gui.htmlunit.profile;

import java.util.function.Function;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import fr.pinguet62.jsfring.gui.htmlunit.datatable.AbstractRow;

public final class ProfileRow extends AbstractRow<ProfilePopup, ProfilePopup> {

    public ProfileRow(HtmlTableRow row) {
        super(row);
    }

    @Override
    protected Function<HtmlPage, ProfilePopup> getPopupShowFactory() {
        return (arg) -> new ProfilePopup();
    }

    @Override
    protected Function<HtmlPage, ProfilePopup> getPopupUpdateFactory() {
        return getPopupShowFactory();
    }

    public String getTitle() {
        return getString(0);
    }

}