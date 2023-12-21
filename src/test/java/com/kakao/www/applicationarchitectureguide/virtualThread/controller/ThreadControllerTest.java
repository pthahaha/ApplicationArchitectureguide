package com.kakao.www.applicationarchitectureguide.virtualThread.controller;

import com.kakao.www.applicationarchitectureguide.virtualThread.service.PlatformThreadCallableService;
import com.kakao.www.applicationarchitectureguide.virtualThread.service.PlatformThreadRunnableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ThreadController.class)

class ThreadControllerTest {

    @Autowired private MockMvc mvc;

    @MockBean
    PlatformThreadRunnableService ptRunnableService;

    @MockBean
    PlatformThreadCallableService ptCallableServlce;


    @BeforeEach
    public void setUp() {
         ptRunnableService = new PlatformThreadRunnableService();
         ptCallableServlce = new PlatformThreadCallableService();
    }

    @Test
    void hello_return() throws Exception {
        String hello = "helloWorld";
        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    void virtualRunnable() throws InterruptedException{
        // given
        Thread.Builder builder = Thread.ofVirtual().name("virtual thread-", 0);

        // when
        Thread t = builder.start(ptRunnableService);
        Thread t1 = builder.start(ptRunnableService);

        // then
        assertTrue(t.isVirtual());
        assertTrue(t.join(Duration.ofSeconds(1)));

        assertTrue(t1.isVirtual());
        assertTrue(t1.join(Duration.ofSeconds(1)));
    }

    @Test
    void virtualCallable() throws InterruptedException, ExecutionException {
        // given
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

        // when
        Future<Integer> future = executorService.submit(ptCallableServlce);
        executorService.shutdown();
        int sum = future.get();

        // then
        assertEquals(45, sum);

    }

    @Test
    void platformRunnable() throws InterruptedException {

        // given
        Thread pt1 = new Thread(ptRunnableService);
        Thread pt2 = new Thread(ptRunnableService);
        System.out.println("Thread t1 name: " + pt1.getName());
        System.out.println("Thread t2 name: " + pt2.getName());

        // when
        pt1.start();
        pt2.start();

        // then
        assertFalse(pt1.isVirtual());
        assertTrue(pt1.join(Duration.ofSeconds(1)));

        assertFalse(pt2.isVirtual());
        assertTrue(pt2.join(Duration.ofSeconds(1)));


    }

    @Test
    void platformCallable() throws InterruptedException, ExecutionException {
        // given
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // when
        Future<Integer> future = executorService.submit(ptCallableServlce);
        executorService.shutdown();
        int sum = future.get();

        // then
        assertEquals(45, sum);
    }
}