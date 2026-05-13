package com.Deep.library_api.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BookMaintenanceScheduler {
    private static final Logger log = LoggerFactory.getLogger(BookMaintenanceScheduler.class);

    // waits 5 minutes between task completions, then run again
    @Scheduled(fixedDelay = 300000)
    public void cleanupExpiredReservations() {
        log.info("Running scheduled task: Cleaned expired reservations");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("Cleanup completed");
    }
    // runs at 9 AM every day (cron expression)
    @Scheduled(cron = "0 0 9 * * *")
    public void sendOverdueReminders() {
        log.info("Running scheduled task: Send overdue book reminders");
        log.info("Overdue reminders sent");
    }
    // runs every 30 seconds regardless of how long the task takes
    @Scheduled(fixedRate = 30000)
    public void refreshCache() {
        log.info("Running scheduled task: Refresh cache");
        log.info("Cache refreshed");
    }
}
