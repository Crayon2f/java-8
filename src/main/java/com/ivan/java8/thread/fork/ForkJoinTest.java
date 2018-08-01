package com.ivan.java8.thread.fork;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class ForkJoinTest {

    @Test
    void test() {

        ForkJoinPool pool = new ForkJoinPool();

        RecursiveTask<Long> task = new CountTask(0, 2000000L);

    }
}

class CountTask extends RecursiveTask<Long> {

    private static final int THRESHOLD = 10000;
    private long start, end;

    CountTask(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {

        long sum = 0L;
        boolean canCompute = end - start < THRESHOLD;

        if (canCompute) {
            for (long i = start; i <= end; i++) {
                sum += i;
            }
        } else {

        }
        return sum;
    }
}
