package fr.pinguet62.jsfring.task;

import fr.pinguet62.jsfring.dao.sql.UserDao;
import fr.pinguet62.jsfring.model.sql.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.requireNonNull;

/**
 * The task for {@link User}.
 */
@Slf4j
@Component
public class UserTask {

    private final UserDao dao;

    private final int numberOfDays;

    public UserTask(UserDao dao, @Value("${jsfring.task.user.disableInactives.numberOfDays}") int numberOfDays) {
        this.dao = requireNonNull(dao);
        this.numberOfDays = numberOfDays;
    }

    /**
     * Disable all users who have not connected since a delay.
     */
    @Scheduled(cron = "${jsfring.task.user.disableInactives.cron}")
    @Transactional
    public void disableInactiveUsers() {
        log.info("Scheduling...");
        dao.disableInactiveUsers(7);
    }

}