package com.shakepoint.web.schedule;

import com.shakepoint.integration.jms.client.handler.JmsHandler;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.inject.Inject;

public class TrainerPromoCodeScheduler implements Job {

    private static final String TRAINER_PROMOCODE_QUEUE_NAME = "trainer_promo_promocode";

    @Inject
    private JmsHandler jmsHandler;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        jmsHandler.send(TRAINER_PROMOCODE_QUEUE_NAME, "GO!");
    }
}
