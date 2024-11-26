package org.jala.university.config.config_loan;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * This class provides a singleton instance of a
 * {@link ThreadPoolTaskScheduler} configured for scheduling tasks
 * related to loans.
 */
public class SchedulerConfig {

    /**
     * The singleton instance of the {@link ThreadPoolTaskScheduler}.
     */
    private static final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();

    static {
        // Define the pool size for the task scheduler
        scheduler.setPoolSize(5);
        scheduler.setThreadNamePrefix("LoanScheduler-");
        scheduler.initialize();
    }

    /**
     * Returns the singleton instance of the
     * {@link ThreadPoolTaskScheduler}.
     *
     * @return The configured task scheduler.
     */
    public static ThreadPoolTaskScheduler getScheduler() {
        return scheduler;
    }
}
