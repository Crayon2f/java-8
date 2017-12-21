package com.ivan.java8.stream;

import org.junit.jupiter.api.Test;

import java.util.IntSummaryStatistics;
import java.util.stream.IntStream;

/**
 * Created by feiFan.gou on 2017/12/20 17:20.
 */
class Statistics {

    private static final IntSummaryStatistics statistics =
            IntStream.of(8, 4, 3, 2, 55, 32, 56, -100).summaryStatistics();

    /**
     * 向统计中增加元素
     */
    @Test
    void accept() {
        statistics.accept(3);
        statistics.accept(5);
        System.out.println(statistics.toString());
    }

    /**
     * 函数
     */
    @Test
    void method() {
        // 求和
        System.out.println(statistics.getSum());
        // 最大,最小值
        System.out.println(statistics.getMin());
        System.out.println(statistics.getMax());
        // 平均值
        System.out.println(statistics.getAverage());
        // 个数
        System.out.println(statistics.getCount());
    }



}
