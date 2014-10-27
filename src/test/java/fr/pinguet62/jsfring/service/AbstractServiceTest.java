package fr.pinguet62.jsfring.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.Serializable;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.Config;
import fr.pinguet62.jsfring.model.Language;
import fr.pinguet62.jsfring.model.Profile;
import fr.pinguet62.jsfring.service.AbstractService;
import fr.pinguet62.jsfring.service.LanguageService;
import fr.pinguet62.jsfring.service.ProfileService;

/** Tests for {@link AbstractService}. */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = Config.SPRING)
@DatabaseSetup(Config.DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DbUnitTestExecutionListener.class })
public class AbstractServiceTest {

    @Autowired
    private LanguageService languageService;

    @Autowired
    private ProfileService profileService;

    /** @see AbstractService#create(Object) */
    @Test
    public void test_create() {
        assertEquals(3, languageService.count());
        languageService.create(new Language("es", "Español"));
        assertEquals(4, languageService.count());
        languageService.create(new Language("cr", "ᓀᐦᐃᔭᐍᐏᐣ"));
        assertEquals(5, languageService.count());
    }

    /** @see AbstractService#get(Serializable) */
    @Test
    public void test_get() {
        assertEquals("Français", languageService.get("fr").getName());
        assertNull(languageService.get("??"));
    }

    /**
     * @see ManyToMany#fetch()
     * @see FetchType#LAZY
     */
    @Test
    public void test_manyToMany_lazy() {
        Profile profile = profileService.get(1);
        assertEquals(3, profile.getRights().size());
    }

    /** @see AbstractService#update(Object) */
    @Test
    public void test_update() {
        Profile profile = profileService.get(1);
        // update
        profile.setTitle("new title");
        profileService.update(profile);
        // test
        assertEquals("new title", profileService.get(1).getTitle());
    }

}