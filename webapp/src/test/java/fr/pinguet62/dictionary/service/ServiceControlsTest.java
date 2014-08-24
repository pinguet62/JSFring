package fr.pinguet62.dictionary.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

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

    private Right randomRight() {
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
        rightDao.create(randomRight());
    }

    /**
     * Insert an object and provokes an error.<br/>
     * The inserted object must be removed after rollback.
     */
    @Transactional
    void rollbackMethod() {
        rightDao.create(randomRight());
        throw new RollbackMeIMFamousException();
    }
}

/** Tests that the controls of {@link AbstractService} are correct. */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
@DatabaseSetup("/dataset.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DbUnitTestExecutionListener.class })
public class ServiceControlsTest {

    private static final Logger LOGGER = Logger
            .getLogger(ServiceControlsTest.class);

    @Autowired
    BadService badService;

    @Autowired
    RightDao rightDao;

    @Autowired
    private ThreadTestService threadTestService;

    @Test
    public void test_concurrence() {
        Thread t2 = new Thread(() -> {
            threadTestService.method2();
        });

        Runnable runSecondThread = () -> {
            LOGGER.debug("Thread2: start");
            t2.start();
        };
        Runnable waitEndSecondThread = () -> {
            try {
                LOGGER.debug("Thread1: join Thread2...");
                t2.join();
                LOGGER.debug("Thread2: end");
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        Thread t1 = new Thread(() -> threadTestService.method1(runSecondThread,
                waitEndSecondThread));

        LOGGER.debug("Thread1: start");
        t1.start();
        try {
            LOGGER.debug("Thread1: join...");
            t1.join();
            LOGGER.debug("Thread1: end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test that {@link Service} methods annotated with
     * {@link Transactional#readOnly()} with value {@code true} will not insert
     * object into database.
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

@Service
class ThreadTestService {

    private static final Logger LOGGER = Logger
            .getLogger(ThreadTestService.class);

    @Autowired
    private RightService rightService;

    /**
     * First method called for the test.
     *
     * <ul>
     * <li>Start;</li>
     * <li>Wait the authorization of {@link #method2()} to continue;</li>
     * <li>Call stop method to wait exit.</li>
     * </ul>
     *
     * @param runSecondThread
     *            Run the second {@link Thread}.
     * @param waitEndSecondThread
     *            Wait the end of the second {@link Thread}.
     */
    @Transactional(readOnly = true)
    public void method1(Runnable runSecondThread, Runnable waitEndSecondThread) {
        LOGGER.debug("method1: started, start Thread2");
        runSecondThread.run();
        rightService.getAll();
        LOGGER.debug("method1: waiting Thread2...");
        waitEndSecondThread.run();
    }

    @Transactional(readOnly = true)
    public void method2() {
        LOGGER.debug("method2: started");
        rightService.getAll();
        LOGGER.debug("method2: finished");
    }

}