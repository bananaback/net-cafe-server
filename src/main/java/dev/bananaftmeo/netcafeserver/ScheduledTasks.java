package dev.bananaftmeo.netcafeserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import dev.bananaftmeo.netcafeserver.services.usersessionservices.IUserSessionService;

@Component
public class ScheduledTasks {

    @Autowired
    private IUserSessionService userSessionService;

    @Scheduled(fixedRate = 60000) // Run every minute (60000 milliseconds)
    public void deductBalancesForOngoingSessions() {
        userSessionService.deductBalancesForOngoingSessions();
    }
}
