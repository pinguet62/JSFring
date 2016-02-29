package fr.pinguet62.jsfring.gui.htmlunit.profile;

import static fr.pinguet62.jsfring.gui.htmlunit.AbstractPage.Delay.SHORT;

import java.io.IOException;
import java.util.function.Function;

import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import fr.pinguet62.jsfring.gui.htmlunit.NavigatorException;
import fr.pinguet62.jsfring.gui.htmlunit.datatable.AbstractDatatablePage;
import fr.pinguet62.jsfring.gui.htmlunit.profile.popup.ProfileCreatePopup;

/** @see ProfilesBean */
public final class ProfilesPage extends AbstractDatatablePage<ProfileRow, ProfileCreatePopup> {

    public ProfilesPage(HtmlPage page) {
        super(page);
    }

    @Override
    public ProfileCreatePopup actionCreate() {
        HtmlButton button = (HtmlButton) getDatatableHeader().getByXPath("./button[contains(@onclick, 'createDialog')]")
                .get(0);
        try {
            HtmlPage page = button.click();
            waitJS(SHORT);
            debug();

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