package com.ivan.java8.thread;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTest {

    @Test
    public void test() {

        ExecutorService exec = Executors.newFixedThreadPool(5);
        List<Runnable> runnableList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            final int j = i;
            runnableList.add(() -> System.out.println(100 / j));
        }
        runnableList.forEach(exec::submit);
        exec.shutdown();

    }
}
