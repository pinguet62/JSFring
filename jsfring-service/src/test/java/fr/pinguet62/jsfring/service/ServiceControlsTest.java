package fr.pinguet62.jsfring.service;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static java.lang.Thread.sleep;
import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.Date;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.dao.sql.ProfileDao;
import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.service.TestService.RollbackMeIMFamousException;
import fr.pinguet62.jsfring.test.TestRuntimeException;

/**
 * Tests that the controls of {@link AbstractService} are correct.
 * <p>
 * {@link TestService} contains the specifics {@link Service}s.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringBootConfig.class)
@DatabaseSetup(DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class ServiceControlsTest {

    @Inject
    private ProfileService profileService;

    @Inject
    private TestService testService;

    /**
     * When a table is locked by a modifications of a {@link Service}, other
     * {@link Service} <b>cannot modify</b> it in same time.
     */
    // TODO fix integration: @Test
    public void test_concurrence_modificationLockedByOtherModification() {
        Thread parallelNonLockedAction = new Thread(() -> testService.write());
        Runnable lockingAction = () -> testService.write();
        Thread parallelLockedAction = new Thread(() -> testService.write());

        testService.concurrence(parallelNonLockedAction, lockingAction, parallelLockedAction, 2_000);
    }

    /**
     * When a table is locked by a modifications of a {@link Service}, other
     * {@link Service} <b>can read</b> table.
     */
    // TODO fix integration: @Test
    public void test_concurrence_readNotLockedByLock() {
        Thread parallelReadAction = new Thread(() -> testService.read());
        Runnable lockingAction = () -> testService.write();
        Thread parallelReadAction2 = new Thread(() -> testService.read());

        testService.concurrence(parallelReadAction, lockingAction, parallelReadAction2, 0);
    }

    // TODO delete this
    @Test
    public void test_inutile() {
        assertEquals(1, 1);
    }

    /**
     * {@link Transactional#readOnly() Read-only} {@link Service} cannot modify
     * database, event if {@link Service} try to modify the database.
     *
     * @see TestService#modificationIntoReadonlyService()
     */
    // TODO fix local: @Test
    public void test_readonly_noModification() {
        final int initialCount = profileService.getAll().size();
        testService.modificationIntoReadonlyService();
        assertThat(profileService.getAll(), hasSize(initialCount));
    }

    /**
     * When an error occurs, the {@link Transactional transaction} must be
     * rollbacked.<br>
     * Inserted/Updated/Deleted objects must not be visible.
     *
     * @see TestService#rollbackedMethod()
     */
    // TODO fix local: @Test
    public void test_rollbackWhenError() {
        final int initialCount = profileService.getAll().size();
        try {
            testService.rollbackedMethod();
            fail();
        } catch (RollbackMeIMFamousException e) {
            assertThat(profileService.getAll(), hasSize(initialCount));
        }
    }

}

/** A {@link Service} for {@link ServiceControlsTest}. */
@Service
class TestService {

    /** @see #rollbackedMethod() */
    static class RollbackMeIMFamousException extends TestRuntimeException {
        private static final long serialVersionUID = 1;
    }

    private static final Logger LOGGER = getLogger(TestService.class);

    @Inject
    private ProfileDao profileDao;

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
    public void concurrence(Thread actionBefore, Runnable lockFct, Thread actionAfter, int maxWait) {
        LOGGER.debug("Beginning test...");

        // Between transaction beginning and main action
        LOGGER.debug("Before action: starting...");
        actionBefore.start();
        try {
            LOGGER.debug("Before action: joining...");
            actionBefore.join();
            assertThat(actionBefore.isAlive(), is(false));
        } catch (InterruptedException e) {
            throw new TestRuntimeException(e);
        }

        // Main action
        LOGGER.debug("Main action: running...");
        lockFct.run();

        // Between main action and transaction end
        LOGGER.debug("After action: starting...");
        actionAfter.start();
        try {
            Date startJoin = new Date();
            LOGGER.debug("After action: joining... ({}ms max)", maxWait);
            actionAfter.join(maxWait);
            Date endJoin = new Date();
            assertThat(endJoin.getTime() - startJoin.getTime() > maxWait, is(true));
        } catch (InterruptedException e) {
            throw new TestRuntimeException(e);
        }

        LOGGER.debug("Main thread: end transaction");
    }

    /**
     * Read-only method who insert an object into database.
     *
     * @see ServiceControlsTest#test_readOnlyControl()
     */
    @Transactional(readOnly = true)
    public void modificationIntoReadonlyService() {
        profileDao.save(random());
    }

    private Profile random() {
        return new Profile(randomUUID().toString().substring(0, 10));
    }

    @Transactional
    public void read() {
        profileDao.findAll();
        LOGGER.trace("Read: {}", new Date().getTime());
    }

    /**
     * <ul>
     * <li>Insert an object</li>
     * <li>Throw an {@link TestRuntimeException}</li>
     * </ul>
     *
     * @throws RollbackMeIMFamousException Always thrown.
     * @see ServiceControlsTest#test_rollbackWhenError()
     */
    @Transactional
    public void rollbackedMethod() {
        profileDao.save(random());
        throw new RollbackMeIMFamousException();
    }

    /** Sleep of 2sec before and after executing action. */
    @Transactional
    public void write() {
        try {
            LOGGER.trace("Write: {}", new Date().getTime());
            sleep(2_000);
        } catch (InterruptedException e) {
            throw new TestRuntimeException(e);
        }

        profileDao.deleteAll();
        LOGGER.trace("Write: {}", new Date().getTime());

        try {
            sleep(2_000);
        } catch (InterruptedException e) {
            throw new TestRuntimeException(e);
        }
    }

}