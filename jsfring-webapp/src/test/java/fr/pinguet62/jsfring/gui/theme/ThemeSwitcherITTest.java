package fr.pinguet62.jsfring.gui.theme;

import static fr.pinguet62.jsfring.gui.htmlunit.AbstractPage.get;
import static java.util.Arrays.stream;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.request.SessionScope;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.gui.htmlunit.MyAccountPage;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringBootConfig.class)
@WebIntegrationTest
public class ThemeSwitcherITTest {

    private MyAccountPage page;

    @Before
    public void before() {
        page = get().gotoMyAccountPage();
    }

    /** The switch to another {@link Theme} change instantly the CSS. */
    @Test
    public void test_instantly() {
        String intialTheme = page.getTheme();
        String newTheme = stream(Theme.values()).filter(t -> !t.getKey().equals(intialTheme)).findAny().get().getKey();

        page.getThemeSwitcher().setValue(newTheme);

        assertThat(page.getTheme(), is(equalTo(newTheme)));
    }

    /** The {@link Theme} is stored into {@link SessionScope session}: after page change, the CSS doesn't change. */
    @Test
    public void test_sessionScope() {
        String intialTheme = page.getTheme();
        String newTheme = stream(Theme.values()).filter(t -> !t.getKey().equals(intialTheme)).findAny().get().getKey();

        page.getThemeSwitcher().setValue(newTheme);

        page.gotoLoginPage(); // other page
        assertThat(page.getTheme(), is(equalTo(newTheme)));
    }

}