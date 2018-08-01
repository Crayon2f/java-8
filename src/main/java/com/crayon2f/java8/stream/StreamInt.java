package com.crayon2f.java8.stream;

import com.google.common.collect.Lists;
import com.crayon2f.common.pojo.Article;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by feiFan.gou on 2017/12/12 11:31.
 */
class StreamInt {

    /**
     * 取一个区间
     */
    @Test
    void range() {

        IntStream.range(1,11).forEachOrdered(System.out::println);
    }

    /**
     * 初始化一个IntStream of(Integer...values)
     */
    @Test
    void of() {

        IntStream intStream = IntStream.of(3,5,4,3,3);

        System.out.println(Arrays.toString(intStream.toArray()));

        List<Integer> list = Lists.newArrayList(2, 3, 4, 5);

        System.out.println(Arrays.toString(list.stream().mapToInt(Integer::intValue).toArray()));
//        System.out.println(result);
    }

    @Test
    void convert2List() {
        IntStream intStream = IntStream.of(3,4,5,6);
        System.out.println(intStream.boxed().collect(Collectors.toList()));
    }

    @Test
    void test() {

        IntStream intStream = IntStream.of(3,5,4,3,3);
//        System.out.println(intStream.sorted().sum());
        System.out.println(intStream.peek(System.out::println).distinct().boxed().collect(Collectors.toList()));
//        intStream.summaryStatistics().
//        System.out.println(intStream.sequential().boxed().collect(Collectors.toList()));
    }
}
