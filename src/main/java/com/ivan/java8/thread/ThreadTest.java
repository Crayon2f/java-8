package com.ivan.java8.thread;

import lombok.SneakyThrows;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
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

    @SneakyThrows
    public static void main(String[] args) {

        Runnable runnable = () -> {

            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("i'm daemon thread");
            }
        };
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
//        thread.setdaemon(false);
        thread.start();
        Thread other = new Thread(() -> {
            try {
                Thread.sleep(12000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("=============");
        });
        other.start();
    }
}
