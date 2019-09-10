package fr.pinguet62.jsfring.webapp.jsf.htmlunit.profile;

import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.NavigatorException;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.AbstractDatatablePage;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.profile.popup.ProfileCreatePopup;

import java.io.IOException;
import java.util.function.Function;

import static fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage.Delay.SHORT;

public final class ProfilesPage extends AbstractDatatablePage<ProfileRow, ProfileCreatePopup> {

    public ProfilesPage(HtmlPage page) {
        super(page);
    }

    @Override
    public ProfileCreatePopup actionCreate() {
        HtmlButton button = getDatatableHeader().getFirstByXPath("./button[contains(@onclick, 'createDialog')]");
        try {
            HtmlPage page = button.click();
            waitJS(SHORT);
            debug(page);

            return new ProfileCreatePopup(page);
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

    @Override
    protected Function<HtmlPage, ProfileCreatePopup> getPopupCreateFactory() {
        return ProfileCreatePopup::new;
    }

    @Override
    protected Function<HtmlTableRow, ProfileRow> getRowFactory() {
        return ProfileRow::new;
    }

}
