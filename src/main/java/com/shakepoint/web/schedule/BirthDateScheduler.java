package com.shakepoint.web.schedule;

import com.shakepoint.web.schedule.data.entity.UserProfile;
import com.shakepoint.web.schedule.repository.UserRepository;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

public class BirthDateScheduler implements Job {

    private final Logger log = Logger.getLogger(getClass());

    @Inject
    private UserRepository userRepository;

    @Inject
    private Event<UserProfile> event;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Executing scheduler...");
        //get date
        LocalDate localDate = LocalDate.now();
        userRepository.usersWithBirthDates()
                .stream().forEach(profile -> {
            LocalDate birthDateLocalDate = LocalDate.parse(profile.getBirthDate(), DateTimeFormatter.ofPattern("dd/MM/YYYY"));
            //check if birthdate contains same month
            if (localDate.get(ChronoField.DAY_OF_YEAR) == birthDateLocalDate.get(ChronoField.DAY_OF_YEAR)) {
                //check month
                if (localDate.get(ChronoField.MONTH_OF_YEAR) == birthDateLocalDate.get(ChronoField.MONTH_OF_YEAR)) {
                    //user birth day!
                    event.fire(profile);
                }
            }
        });
    }
}
