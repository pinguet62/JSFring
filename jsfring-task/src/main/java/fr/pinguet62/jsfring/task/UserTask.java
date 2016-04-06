package fr.pinguet62.jsfring.task;

import static org.slf4j.LoggerFactory.getLogger;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import fr.pinguet62.jsfring.dao.sql.UserDao;
import fr.pinguet62.jsfring.model.sql.User;

/** The task for {@link User}. */
@Component
public class UserTask {

    private static final Logger LOGGER = getLogger(UserTask.class);

    @Inject
    private UserDao dao;

    @Value("${app.task.user.disableInactives.numberOfDays}")
    private int numberOfDays;

    /** Disable all users who have not connected since a delay. */
    @Scheduled(cron = "${app.task.user.disableInactives}")
    @Transactional
    public void disableInactiveUsers() {
        LOGGER.info("Scheduling...");
        dao.disableInactiveUsers(7);
    }

}