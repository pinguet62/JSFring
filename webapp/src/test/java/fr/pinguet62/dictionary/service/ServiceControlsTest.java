package fr.pinguet62.dictionary.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.UUID;

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

    private Right getRandomRight() {
        return new Right(UUID.randomUUID().toString().substring(0, 10), UUID
                .randomUUID().toString().substring(0, 15));
    }

    /**
     * Read-only method who insert an object into database.<br/>
     * At the exit, no object must be inserted.
     * <p>
     * Tested by {@link ServiceControlsTest#test_readOnlyControl()}.
     */
    @Transactional(readOnly = true)
    void readOnlyMethod() {
        rightDao.create(getRandomRight());
    }

    /**
     * Insert an object and provokes an error.<br/>
     * The inserted object must be removed after rollback.
     */
    @Transactional
    void rollbackMethod() {
        rightDao.create(getRandomRight());
        throw new RollbackMeIMFamousException();
    }
}

/** Tests that the controls of {@link AbstractService} are correct. */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class })
@Transactional
public final class ServiceControlsTest {

    @Autowired
    BadService badService;

    @Autowired
    RightDao rightDao;

    /**
     * Test that {@link Service} methods annotated with
     * {@link Transactional#readOnly()} with value {@code true} will not insert
     * object into database.
     * <p>
     *
     * @see BadService#readOnlyMethod()
     */
    @Test
    public void test_readOnlyControl() {
        long initialCount = rightDao.count();
        badService.readOnlyMethod();
        assertEquals(initialCount, rightDao.count());
    }

    /**
     * Test that a rollback was be done after that an error occurs into a
     * {@link Service}.
     *
     * @see BadService#rollbackMethod()
     */
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
