package com.kakao.www.applicationarchitectureguide.virtualThread.controller;

import com.kakao.www.applicationarchitectureguide.virtualThread.service.PlatformThreadCallableService;
import com.kakao.www.applicationarchitectureguide.virtualThread.service.PlatformThreadRunnableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
public class ThreadController {

    @Autowired
    PlatformThreadRunnableService ptRunnableService;

    @Autowired
    PlatformThreadCallableService ptCallableServlce;

    @GetMapping("/hello")
    public String hello(){
        System.out.println(Thread.currentThread().getName());
        return "helloWorld";
    }

    @GetMapping("/virtualRunnable")
    public void virtualRunnable() throws InterruptedException {
        Thread.Builder builder = Thread.ofVirtual().name("virtual thread-", 0);
        Thread t = builder.start(ptRunnableService);
        System.out.println("Thread t name: " + t.getName());
        t.join();
        System.out.println(t.getName() + " terminated");

        Thread t1 = builder.start(ptRunnableService);
        System.out.println("Thread t name: " + t1.getName());
        t1.join();
        System.out.println(t1.getName() + " terminated");

    }

    @GetMapping("/virtualCallable")
    public void virtualCallable() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        Future<Integer> future = executorService.submit(ptCallableServlce);
        executorService.shutdown();
        int sum = future.get();
        System.out.println(sum);

    }

    @GetMapping("/runnable")
    public void platformRunnable()  {
        Thread pt1 = new Thread(ptRunnableService);
        Thread pt2 = new Thread(ptRunnableService);
        System.out.println("Thread t1 name: " + pt1.getName());
        pt1.start();
        System.out.println("Thread t2 name: " + pt2.getName());
        pt2.start();

    }

    @GetMapping("/callable")
    public void platformCallable()  throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> future = executorService.submit(ptCallableServlce);
        executorService.shutdown();
        int sum = future.get();
        System.out.println(sum);
    }
}
