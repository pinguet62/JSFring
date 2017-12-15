package fr.pinguet62.jsfring.webapp.jsf.theme;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.MyAccountPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.SessionScope;

import static fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage.get;
import static java.util.Arrays.stream;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
public class ThemeSwitcherITTest {

    private MyAccountPage page;

    @BeforeEach
    public void before() {
        page = get().gotoMyAccountPage();
    }

    /**
     * The switch to another {@link Theme} change instantly the CSS.
     */
    @Disabled // TODO fix
    @Test
    public void test_instantly() {
        String intialTheme = page.getTheme();
        String newTheme = stream(Theme.values()).filter(t -> !t.getKey().equals(intialTheme)).findAny().get().getKey();

        page.getThemeSwitcher().setValue(newTheme);

        assertThat(page.getTheme(), is(equalTo(newTheme)));
    }

    /**
     * The {@link Theme} is stored into {@link SessionScope session}: after page change, the CSS doesn't change.
     */
    @Disabled // TODO fix
    @Test
    public void test_sessionScope() {
        String intialTheme = page.getTheme();
        String newTheme = stream(Theme.values()).filter(t -> !t.getKey().equals(intialTheme)).findAny().get().getKey();

        page.getThemeSwitcher().setValue(newTheme);

        page.gotoLoginPage(); // other page
        assertThat(page.getTheme(), is(equalTo(newTheme)));
    }

}