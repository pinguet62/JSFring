package fr.pinguet62.jsfring.task;

import fr.pinguet62.jsfring.dao.sql.UserDao;
import fr.pinguet62.jsfring.model.sql.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * The task for {@link User}.
 */
@Slf4j
@Component
public class UserTask {

    @Autowired
    private UserDao dao;

    @Value("${app.task.user.disableInactives.numberOfDays}")
    private int numberOfDays;

    /**
     * Disable all users who have not connected since a delay.
     */
    @Scheduled(cron = "${app.task.user.disableInactives}")
    @Transactional
    public void disableInactiveUsers() {
        log.info("Scheduling...");
        dao.disableInactiveUsers(7);
    }

}