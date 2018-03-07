package com.ivan.java8.thread;

import com.ivan.java8.kit.StringKit;
import com.ivan.java8.pojo.Article;

import java.util.concurrent.*;

/**
 * Created by feiFan.gou on 2018/3/1 18:14.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

//        System.out.println(asynchronous());
//        Thread.sleep(20* 1000);

        Runnable runnable = () -> {
            try {
                Thread.sleep(1000 * 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(StringKit.divide);
        };
        ExecutorService service = Executors.newCachedThreadPool();
        FutureTask<Integer> task = new FutureTask<>(runnable, 0);
        service.submit(runnable);
        System.out.println("-----------------------");
        Thread.sleep(1000 * 10);

    }

    private static String asynchronous() throws InterruptedException {

        ExecutorService service = Executors.newCachedThreadPool();
        Task task = new Task();
        FutureTask<Article> futureTask = new FutureTask<>(task);
        service.submit(futureTask);

        for (int i = 0; i < 10; i++) {
            Thread.sleep(800);
            System.out.println(i);
        }
//        try {
//            System.out.println(futureTask.get().getTitle());
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
        service.shutdown();
        return "result";
    }
}
class Task implements Callable<Article> {

    @Override
    public Article call() throws InterruptedException {

        Thread.sleep(10 * 1000);

        System.out.println(StringKit.divide_with_content.apply("entry call "));
        return Article.data.get(0);
    }

}