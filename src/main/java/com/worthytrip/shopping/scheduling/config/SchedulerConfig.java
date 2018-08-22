package com.worthytrip.shopping.scheduling.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * @author yuzhigang on 1/6/2018 10:17 AM.
 * @version 1.0
 * Description:
 */
@Configuration
@ConfigurationProperties(prefix = "scheduling", ignoreUnknownFields = true)
public class SchedulerConfig implements SchedulingConfigurer {
    private final int DEFAULT_POOL_SIZE = 10;

    private int poolSize;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

        if (poolSize < 1) {
            poolSize = DEFAULT_POOL_SIZE;
        }
        threadPoolTaskScheduler.setPoolSize(poolSize);

        threadPoolTaskScheduler.setThreadNamePrefix("my-scheduled-task-pool-");

        threadPoolTaskScheduler.initialize();

        scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }
}
