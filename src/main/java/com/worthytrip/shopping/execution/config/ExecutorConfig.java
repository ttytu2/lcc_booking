package com.worthytrip.shopping.execution.config;

import com.google.common.collect.Sets;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.Set;
import java.util.concurrent.Executor;

/**
 * @author yuzhigang on 1/6/2018 11:27 AM.
 * @version 1.0
 * Description:
 */
@Configuration
@EnableAsync
@ConfigurationProperties(prefix = "execution", ignoreUnknownFields = true)
public class ExecutorConfig {
    private final int DEFAULT_MAX_POOL_SIZE = 10;

    private final int DEFAULT_CORE_POOL_SIZE = 5;
    private final int DEFAULT_QUEUE_CAPACITY = 10;

    private int maxPoolSize;
    private int corePoolSize;
    private int queueCapacity;

    /**
     * @date 18-4-26 上午10:09
     *  保存当前批次异步爬虫时所有的线程名,用于正确增加代理爬虫失败次数
     */
    private static Set CURRENT_THREAD_INFO = Sets.newHashSet();

    public static <T> void pushThreadInfo(T t) {
        CURRENT_THREAD_INFO.add(t);
    }

    public static void initCurrentThreadInfo() {
        CURRENT_THREAD_INFO.clear();
    }

    public static <T> boolean checkThreadInfoExist(T t) {
        return CURRENT_THREAD_INFO.contains(t);
    }


    /**
     * SimpleAsyncTaskExecutor
     *
     * This implementation does not reuse any threads, rather it starts up a new thread for each invocation. However, it does support a concurrency limit which will block any invocations that are over the limit until a slot has been freed up. If you're looking for true pooling, keep scrolling further down the page.
     *
     * SyncTaskExecutor
     *
     * This implementation doesn't execute invocations asynchronously. Instead, each invocation takes place in the calling thread. It is primarily used in situations where multithreading isn't necessary such as simple test cases.
     *
     * ConcurrentTaskExecutor
     *
     * This implementation is a wrapper for a Java 5 java.util.concurrent.Executor. There is an alternative, ThreadPoolTaskExecutor, that exposes the Executor configuration parameters as bean properties. It is rare to need to use the ConcurrentTaskExecutor but if the ThreadPoolTaskExecutor isn't robust enough for your needs, the ConcurrentTaskExecutor is an alternative.
     *
     * SimpleThreadPoolTaskExecutor
     *
     * This implementation is actually a subclass of Quartz's SimpleThreadPool which listens to Spring's lifecycle callbacks. This is typically used when you have a thread pool that may need to be shared by both Quartz and non-Quartz components.
     *
     * ThreadPoolTaskExecutor
     *
     *
     * It is not possible to use any backport or alternate versions of the java.util.concurrent package with this implementation. Both Doug Lea's and Dawid Kurzyniec's implementations use different package structures which will prevent them from working correctly.
     *
     * This implementation can only be used in a Java 5 environment but is also the most commonly used one in that environment. It exposes bean properties for configuring a java.util.concurrent.ThreadPoolExecutor and wraps it in a TaskExecutor. If you need something advanced such as a ScheduledThreadPoolExecutor, it is recommended that you use a ConcurrentTaskExecutor instead.
     *
     * TimerTaskExecutor
     *
     * This implementation uses a single TimerTask as its backing implementation. It's different from the SyncTaskExecutor in that the method invocations are executed in a separate thread, although they are synchronous in that thread.
     *
     * WorkManagerTaskExecutor
     *
     *
     * CommonJ is a set of specifications jointly developed between BEA and IBM. These specifications are not Java EE standards, but are standard across BEA's and IBM's Application Server implementations.
     *
     * This implementation uses the CommonJ WorkManager as its backing implementation and is the central convenience class for setting up a CommonJ WorkManager reference in a Spring context. Similar to the SimpleThreadPoolTaskExecutor, this class implements the WorkManager interface and therefore can be used directly as a WorkManager as well.
     *
     */
    @Bean
    public Executor myExcutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        if (corePoolSize < 1) {
            corePoolSize = DEFAULT_CORE_POOL_SIZE;
        }
        executor.setCorePoolSize(corePoolSize);

        if (maxPoolSize < 1) {
            maxPoolSize = DEFAULT_MAX_POOL_SIZE;
        }
        executor.setMaxPoolSize(maxPoolSize);

        if (queueCapacity < 0) {
            queueCapacity = DEFAULT_QUEUE_CAPACITY;
        }
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("MyExecutor-");
        executor.initialize();
        return executor;
    }


    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }
}
