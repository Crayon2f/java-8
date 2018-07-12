package com.ivan.java8.thread;

import com.google.common.collect.Lists;
import com.ivan.java8.kit.StringKit;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by feiFan.gou on 2018/3/1 18:14.
 */
public class Main {


    @Test
    public void implRunnable() throws InterruptedException {

        Runnable runnable = this::asynchronous;

        Thread thread = new Thread(runnable);
        thread.start();
        main();
        thread.join();
    }

    class MyThread extends Thread {

        public void run() {
            asynchronous();
        }
    }

    @Test
    public void extendsThread() {

        MyThread thread = new MyThread();
        thread.run();
        main();
    }

    @Test
    public void useThreadPool() {

//        Runnable runnable = this::asynchronous;
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(this::asynchronous);
        executor.shutdown();
        main();

    }

    @Test
    public void test() throws ExecutionException, InterruptedException {

        Callable<List<String>> callable = this::implCallback;
        FutureTask<List<String>> task = new FutureTask<>(callable);
        ExecutorService executor = Executors.newCachedThreadPool();
//        executor.submit(new FutureTask<List<String>>(this::implCallback));
        executor.submit(task);
        while (task.isDone()) {
            main();
        }
        System.out.println(task.get()); //会堵塞,直到可以执行完毕,即 task.isDone() == true
        executor.shutdown();

    }

    @Test
    public void test2() {

        System.out.println(new Date(1520586876009L));
    }


    private void asynchronous() {

        for (int i = 0; i < 20; i++) {
            System.out.println(StringKit.divide_with_content.apply(i + 1 +""));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void main() {

        try {
            Thread.sleep(1000 * 3);
            System.out.println(StringKit.divide_with_content.apply("this is main thread"));
            Thread.sleep(1000 * 20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T implCallback() {

        this.asynchronous();
        List<String> stringList = Lists.newArrayList();
        stringList.add("a");
        stringList.add("b");
        stringList.add("c");
        stringList.add("d");
        return (T) stringList;
    }
}