package fr.pinguet62.jsfring.webapp.jsf.i18n;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage.get;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
public class LangITTest {

    @Autowired
    private LangBean langBean;

    private AbstractPage navigator;

    @BeforeEach
    public void before() {
        navigator = get();
    }

    @Test
    @Disabled // TODO fix
    public void test_switcher() {
        String initialLangKey = navigator.gotoMyAccountPage().getLangSwitcher().getValue();
        String initialTitle = navigator.gotoRightsPage().getTitle();

        String newLangKey = langBean.getSupportedLocales().stream()
                .filter(loc -> !loc.getLanguage().equals(initialLangKey))
                .findAny().get()
                .getLanguage();
        navigator.gotoMyAccountPage().getLangSwitcher().setValue(newLangKey);

        String newTitle = navigator.gotoRightsPage().getTitle();
        assertThat(newTitle, is(not(equalTo(initialTitle))));
    }

}