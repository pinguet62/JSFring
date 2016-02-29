package fr.pinguet62.jsfring.gui.htmlunit.profile;

import java.util.function.Function;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import fr.pinguet62.jsfring.gui.htmlunit.datatable.AbstractRow;
import fr.pinguet62.jsfring.gui.htmlunit.profile.popup.ProfileShowPopup;
import fr.pinguet62.jsfring.gui.htmlunit.profile.popup.ProfileUpdatePopup;

public final class ProfileRow extends AbstractRow<ProfileShowPopup, ProfileUpdatePopup> {

    public ProfileRow(HtmlTableRow row) {
        super(row);
    }

    @Override
    protected Function<HtmlPage, ProfileShowPopup> getPopupShowFactory() {
        return ProfileShowPopup::new;
    }

    @Override
    protected Function<HtmlPage, ProfileUpdatePopup> getPopupUpdateFactory() {
        return ProfileUpdatePopup::new;
    }

    public String getTitle() {
        return getString(0);
    }

}