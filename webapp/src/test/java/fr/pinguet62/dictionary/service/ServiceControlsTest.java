package fr.pinguet62.dictionary.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import fr.pinguet62.dictionary.dao.RightDao;
import fr.pinguet62.dictionary.model.Right;
import fr.pinguet62.dictionary.service.BadService.RollbackMeIMFamousException;

@Service
class BadService {
    class RollbackMeIMFamousException extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }

    @Autowired
    RightDao rightDao;

    /**
     * Test to insert an object into database.<br/>
     * An error must occur.
     */
    @Transactional(readOnly = true)
    void readOnlyMethod() {
        rightDao.create(new Right("code", "title"));
    }

    /**
     * Insert an object and provokes an error.<br/>
     * The inserted object must be removed after rollback.
     */
    @Transactional
    void rollbackMethod() {
        rightDao.create(new Right("code", "title"));
        throw new RollbackMeIMFamousException();
    }
}

/** Tests that the controls of {@link AbstractService} are correct. */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
// @DatabaseSetup("/dataset.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class })
@Transactional
public final class ServiceControlsTest {

    @Autowired
    BadService badService;

    @Autowired
    RightDao rightDao;

    @Test
    public void test_readOnlyControl() {
        long initialCount = rightDao.count();
        badService.readOnlyMethod();
        assertEquals(initialCount, rightDao.count());
    }

    @Test
    public void test_rollbackOnError() {
        long initialCount = rightDao.count();
        try {
            badService.rollbackMethod();
            fail();
        } catch (RollbackMeIMFamousException e) {
            assertEquals(initialCount, rightDao.count());
        }
    }

}
