package com.shakepoint.web.schedule;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Startup
@Singleton
public class SchedulerConfigurator {

    private static final String JOB_NAME = "birthDateScheduler";
    private static final String TRIGGER_NAME = "birthDateTrigger";

    private final Logger log = Logger.getLogger(getClass());

    @Inject
    private CDIJobFactory cdiJobFactory;

    @PostConstruct
    public void init() {
        try {
            log.info("Launching scheduler configurator");
            //create job execution
            JobDetail jobDetail = JobBuilder.newJob(BirthDateScheduler.class)
                    .withIdentity(JOB_NAME).build();

            log.info("Creating daily trigger");
            //create a new trigger
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(TRIGGER_NAME)
                    .withPriority(Trigger.DEFAULT_PRIORITY)
                    .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(0, 0))
                    .forJob(JOB_NAME).build();

            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.setJobFactory(cdiJobFactory);
            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);
            log.info("Started scheduler");
        } catch (SchedulerException ex) {
            log.error("Could not create scheduler", ex);
        }
    }
}
