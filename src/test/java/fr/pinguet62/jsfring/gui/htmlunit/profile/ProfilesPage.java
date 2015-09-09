package fr.pinguet62.jsfring.gui.htmlunit.profile;

import java.util.function.Function;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import fr.pinguet62.jsfring.gui.ProfilesBean;
import fr.pinguet62.jsfring.gui.htmlunit.AbstractDatatablePage;

/** @see ProfilesBean */
public final class ProfilesPage extends AbstractDatatablePage<ProfileRow> {

    public ProfilesPage(HtmlPage page) {
        super(page);
    }

    @Override
    protected Function<HtmlTableRow, ProfileRow> getRowFactory() {
        return ProfileRow::new;
    }

}