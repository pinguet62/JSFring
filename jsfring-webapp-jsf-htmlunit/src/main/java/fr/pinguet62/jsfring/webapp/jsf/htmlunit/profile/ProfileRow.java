package fr.pinguet62.jsfring.webapp.jsf.htmlunit.profile;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.AbstractRow;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.profile.popup.ProfileShowPopup;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.profile.popup.ProfileUpdatePopup;

import java.util.function.Function;

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