package com.shakepoint.web.schedule;

import com.shakepoint.integration.jms.client.handler.JmsHandler;
import com.shakepoint.web.schedule.data.entity.UserProfile;
import org.apache.log4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class SchedulerObserver {

    private static final String BIRTHDATE_QUEUE_NAME = "birthdate_promocode";
    private final Logger log = Logger.getLogger(getClass());

    @Inject
    private JmsHandler jmsHandler;

    public void onUserBirthDate(@Observes UserProfile userProfile) {
        try {
            log.info("Will send notification for user birth day");
            jmsHandler.send(BIRTHDATE_QUEUE_NAME, userProfile.getUserId());
        } catch (Exception ex) {
            log.error("Could not send birthdate jms message", ex);
        }
    }
}
