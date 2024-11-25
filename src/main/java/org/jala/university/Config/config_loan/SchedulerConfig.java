package org.jala.university.config.config_loan;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public class SchedulerConfig {

    private static final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();

    static {
        scheduler.setPoolSize(5);
        scheduler.setThreadNamePrefix("LoanScheduler-");
        scheduler.initialize();
    }

    public static ThreadPoolTaskScheduler getScheduler() {
        return scheduler;
    }
}
