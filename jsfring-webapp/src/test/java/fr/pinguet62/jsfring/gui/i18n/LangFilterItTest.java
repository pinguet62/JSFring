package fr.pinguet62.jsfring.gui.i18n;

import static fr.pinguet62.jsfring.gui.htmlunit.AbstractPage.get;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringBootConfig.class)
@WebIntegrationTest
public class LangFilterItTest {

    @Inject
    private LangBean langBean;

    private AbstractPage navigator;

    @Before
    public void before() {
        navigator = get();
    }

    @Test
    public void test_getParameter() {
        String initialLangKey = navigator.gotoMyAccountPage().getLangSwitcher().getValue();
        String initialTitle = navigator.gotoUsersPage().getTitle();

        String newLangKey = langBean.getSupportedLocales().stream().filter(l -> l.getLanguage().equals(initialLangKey))
                .findAny().get().getLanguage();
        String subUrl = String.format("/index.xhtml?%1=%2", LangFilter.PARAMETER, newLangKey);
        navigator.gotoUrl(subUrl);

        String newTitle = navigator.gotoUsersPage().getTitle();
        assertThat(newTitle, is(not(equalTo(initialTitle))));
    }

}