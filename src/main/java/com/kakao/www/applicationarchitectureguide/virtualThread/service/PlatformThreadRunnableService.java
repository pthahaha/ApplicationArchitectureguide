package com.kakao.www.applicationarchitectureguide.virtualThread.service;

import org.springframework.stereotype.Service;

@Service
public class PlatformThreadRunnableService implements Runnable{
    @Override
    public void run() {
        System.out.println("Thread ID: " +Thread.currentThread().threadId());
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+"가"+ i+"을 출력!");
        }
    }
}
