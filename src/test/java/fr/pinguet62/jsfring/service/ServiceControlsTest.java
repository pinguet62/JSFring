package fr.pinguet62.jsfring.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.Config;
import fr.pinguet62.jsfring.dao.ProfileDao;
import fr.pinguet62.jsfring.model.Profile;
import fr.pinguet62.jsfring.service.TestService.RollbackMeIMFamousException;

/**
 * Tests that the controls of {@link AbstractService} are correct.
 * <p>
 * {@link TestService} contains the specifics {@link Service}s.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = Config.SPRING)
@DatabaseSetup(Config.DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DbUnitTestExecutionListener.class })
public class ServiceControlsTest {

    @Inject
    private ProfileService profileService;

    @Inject
    private TestService testService;

    /**
     * When a table is locked by a modifications of a {@link Service}, other
     * {@link Service} <b>cannot modify</b> it in same time.
     */
    @Test
    public void test_concurrence_modificationLockedByOtherModification() {
        Thread parallelNonLockedAction = new Thread(() -> testService.write());
        Runnable lockingAction = () -> testService.write();
        Thread parallelLockedAction = new Thread(() -> testService.write());

        testService.concurrence(parallelNonLockedAction, lockingAction,
                parallelLockedAction, 2_000);
    }

    /**
     * When a table is locked by a modifications of a {@link Service}, other
     * {@link Service} <b>can read</b> table.
     */
    @Test
    public void test_concurrence_readNotLockedByLock() {
        Thread parallelReadAction = new Thread(() -> testService.read());
        Runnable lockingAction = () -> testService.write();
        Thread parallelReadAction2 = new Thread(() -> testService.read());

        testService.concurrence(parallelReadAction, lockingAction,
                parallelReadAction2, 0);
    }

    /**
     * {@link Transactional#readOnly() Read-only} {@link Service} cannot modify
     * database, event if {@link Service} try to modify the database.
     *
     * @see TestService#modificationIntoReadonlyService()
     */
    @Test
    public void test_readonly_noModification() {
        final long initialCount = profileService.count();
        testService.modificationIntoReadonlyService();
        assertEquals(initialCount, profileService.count());
    }

    /**
     * When an error occurs, the {@link Transactional transaction} must be
     * rollbacked.<br>
     * Inserted/Updated/Deleted objects must not be visible.
     *
     * @see TestService#rollbackedMethod()
     */
    @Test
    public void test_rollbackWhenError() {
        final long initialCount = profileService.count();
        try {
            testService.rollbackedMethod();
            fail();
        } catch (RollbackMeIMFamousException e) {
            assertEquals(initialCount, profileService.count());
        }
    }

}

/** A {@link Service} for {@link ServiceControlsTest}. */
@Service
class TestService {

    /** @see #rollbackedMethod() */
    class RollbackMeIMFamousException extends RuntimeException {
        private static final long serialVersionUID = 1;
    }

    private static final Logger LOGGER = LoggerFactory
            .getLogger(TestService.class);

    @Inject
    private ProfileDao profileDao;

    @Inject
    private ProfileService profileService;

    /**
     * A concurrence scenario to execute a parallel action <b>during</b> this
     * {@link Service}.
     * <ol>
     * <li>Transaction begins: the table is not locked</li>
     * <li>A concurrent {@link Thread}: can access to table</li>
     * <li>The table locked: a write action</li>
     * <li>A concurrent {@link Thread}: cannot access to table, so it must wait
     * for end of the current transaction</li>
     * <li>Transaction end: the table is unlocked</li>
     * <li>The concurrent {@link Thread} can continue his action</li>
     * </ol>
     *
     * @param actionBefore Parallel action to execute between transaction
     *            beginning and locking action.
     * @param lockFct Action to execute during the service, to lock the table.
     * @param actionAfter Parallel action to execute between locking action and
     *            end of transaction.
     * @param maxWait The max time to {@link Thread#join(long) wait} the end of
     *            2nd parallel action.<br>
     *            For <i>non-locking action</i> the value must be {@code 0} to
     *            wait for the end. For <i>locking action</i> the value must be
     *            positive.
     * @throws AssertionError If the {@link Thread 2nd action} is joined after
     *             this delay, then the test {@link Assert#fail() fail}.
     */
    @Transactional
    public void concurrence(Thread actionBefore, Runnable lockFct,
            Thread actionAfter, int maxWait) {
        LOGGER.debug("Main thread: begin transaction");

        // Between transaction beginning and main action
        actionBefore.start();
        try {
            actionBefore.join();
            assertFalse(actionBefore.isAlive());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Main action
        lockFct.run();

        // Between main action and transaction end
        actionAfter.start();
        try {
            Date startJoin = new Date();
            actionAfter.join(maxWait);
            Date endJoin = new Date();
            assertTrue(endJoin.getTime() - startJoin.getTime() > maxWait);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        LOGGER.debug("Main thread: end transaction");
    }

    /**
     * Read-only method who insert an object into database.
     *
     * @see ServiceControlsTest#test_readOnlyControl()
     */
    @Transactional(readOnly = true)
    void modificationIntoReadonlyService() {
        profileDao.create(random());
    }

    /**
     * A <i>read only service</i> who will be called by another {@link Thread}.<br>
     * The other {@link Thread} will be invoked after (with {@code before}
     * function) and terminated before (with {@code after} function), to be sure
     * that 2 services will access to the same service in same time.
     *
     * @param threadName The name of {@link Thread}, for logging.
     * @param before The action to perform before treatment, after starting of
     *            method.
     * @param after The action to perform after treatment, before to exist the
     *            method.
     * @see ServiceControlsTest#test_concurrence_readOnly()
     */
    @Transactional(readOnly = true)
    public void parallelRead(String threadName, Runnable before, Runnable after) {
        profileService.getAll();

        LOGGER.debug(threadName + ": starting");
        if (before != null) {
            LOGGER.debug(threadName + ": run \"before\"");
            before.run();
        }

        profileService.getAll();

        if (after != null) {
            LOGGER.debug(threadName + ": run \"after\"");
            after.run();
        }
        LOGGER.debug(threadName + ": terminated");

        profileService.getAll();
    }

    /**
     * A <i>write service</i> who will be called by another {@link Thread}.<br>
     * The other {@link Thread} will be invoked after (with {@code before}
     * function)... TODO
     *
     * @param threadName The name of {@link Thread}, for logging.
     * @param before The action to perform before treatment, after starting of
     *            method.
     * @param after The action to perform after treatment, before to exist the
     *            method.
     * @see ServiceControlsTest#test_concurrence_write()
     */
    @Transactional
    public void parallelWrite(Thread accessBetweenTransactionAndLock,
            Thread accessDuringLock) {
        accessBetweenTransactionAndLock.start();
        try {
            accessBetweenTransactionAndLock.join();
        } catch (InterruptedException e) {
            LOGGER.error("Error during joining", e);
            throw new RuntimeException(e);
        }

        // Lock table
        profileDao.create(random());

        try {
            accessBetweenTransactionAndLock.join(500);
        } catch (InterruptedException e) {
            LOGGER.debug("Timeout join, because table is locked", e);
        }
    }

    private Profile random() {
        return new Profile(UUID.randomUUID().toString().substring(0, 10));
    }

    @Transactional
    public void read() {
        profileDao.getAll();
        System.err.println("t2 : lu - " + new Date().getTime());
    }

    /**
     * <ul>
     * <li>Insert an object</li>
     * <li>Throw an {@link RuntimeException}</li>
     * </ul>
     *
     * @throws RollbackMeIMFamousException Always thrown.
     * @see ServiceControlsTest#test_rollbackWhenError()
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void rollbackedMethod() {
        profileDao.create(random());
        throw new RollbackMeIMFamousException();
    }

    @Transactional
    public void write() {
        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        profileDao.deleteAll();
        System.err.println("t1 : locké - " + new Date().getTime());

        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}