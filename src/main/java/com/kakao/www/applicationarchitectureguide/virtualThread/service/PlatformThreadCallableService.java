package com.kakao.www.applicationarchitectureguide.virtualThread.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;

@Service
public class PlatformThreadCallableService implements Callable {

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for(int i=0; i < 10; i++){
            sum +=i;
        }
        return sum;
    }
}
