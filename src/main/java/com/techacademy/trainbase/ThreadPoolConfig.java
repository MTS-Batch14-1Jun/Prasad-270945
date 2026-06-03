package com.techacademy.trainbase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

@Configuration
@EnableAsync
public class ThreadPoolConfig {
    
    // 4.3 CPU-intensive tasks thread pool
    @Bean(name = "cpuIntensiveExecutor")
    public ExecutorService cpuIntensiveExecutor() {
        int coreCount = Runtime.getRuntime().availableProcessors();
        return Executors.newFixedThreadPool(coreCount, threadFactory("cpu-pool-"));
    }
    
    // 4.3 I/O-bound tasks thread pool (larger pool)
    @Bean(name = "ioBoundExecutor")
    public ExecutorService ioBoundExecutor() {
        return Executors.newFixedThreadPool(100, threadFactory("io-pool-"));
    }
    
    // 4.3 Virtual threads executor (Java 21+)
    @Bean(name = "virtualThreadExecutor")
    public ExecutorService virtualThreadExecutor() {
        // Use a virtual-thread factory that sets names so Thread.currentThread().getName() is not blank
        ThreadFactory virtualFactory = Thread.ofVirtual().name("virtual-pool-", 0).factory();
        // Use cached thread pool with a virtual-thread factory so threads are virtual and named
        //return Executors.newCachedThreadPool(virtualFactory);
        //return Executors.newVirtualThreadPerTaskExecutor();
        return Executors.newCachedThreadPool(virtualFactory);
    }
    
    // 4.3 Scheduled tasks executor
    @Bean(name = "scheduledExecutor")
    public ScheduledExecutorService scheduledExecutor() {
        return Executors.newScheduledThreadPool(5, threadFactory("scheduled-"));
    }

    // 4.3 Spring's async task executor
    @Bean(name = "asyncTaskExecutor")
    public ThreadPoolTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("async-task-");
     //   executor.setRejectedExecutionHandler(new ThreadPoolTaskExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
    
    // 4.3 Custom thread factory with naming
    private ThreadFactory threadFactory(String namePrefix) {
        return new ThreadFactory() {
            private int counter = 0;
            
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName(namePrefix + counter++);
                thread.setDaemon(true); // Daemon threads won't prevent JVM shutdown
                return thread;
            }
        };
    }


}