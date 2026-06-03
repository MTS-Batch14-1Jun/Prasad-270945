package com.techacademy.trainbase.threads;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class VirtualThreadService {

    public void executeVirtualThread(){

        // Traditional platform thread
        Thread platformThread = new Thread(() -> System.out.println("Running on platform thread: " + Thread.currentThread()));
        platformThread.start();

        // Virtual thread (Java 21+)
        Thread virtualThread = Thread.ofVirtual()
                .name("virtual-thread-", 0)
                .start(() -> System.out.println("Running on virtual thread: " + Thread.currentThread()));
        System.out.println(virtualThread.getName() + " is alive: " + virtualThread.isAlive());

        // Using Executors with virtual threads
        ExecutorService virtualExecutor = Executors.newVirtualThreadPerTaskExecutor();
        virtualExecutor.submit(() -> System.out.println("Task running on virtual thread"));
        virtualExecutor.shutdown();

    }

    @Async
    public void executeAscThread(){
        System.out.println("Testing :executeAscThread " + Thread.currentThread().getName());

    }

    @Async
    public void copyExecuteAscThread(){
        System.out.println("Testing :copyExecuteAscThread " + Thread.currentThread().getName());

    }
}
