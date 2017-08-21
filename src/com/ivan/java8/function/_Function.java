package com.ivan.java8.function;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.function.Function;

/**
 * Created by feiFan.gou on 2017/8/15 18:33.
 */
public class _Function {

    @Test
    public void test1() {

        Map<String, String> map = Maps.asMap(Sets.newHashSet("123", "345", "444"), key -> key + 111);
        System.out.println(map);

        test(111, integer -> new StringBuffer(integer + 12 + ""));
        Function<String, String> function = (x) -> {
            System.out.println(x);
            return "function";
        };
        function.apply("123");
        Function<Integer, Integer> times2 = e -> e * 2;

        Function<Integer, Integer> squared = e -> e * e;
        times2.compose(squared).apply(4);
        // Returns 32

        times2.andThen(squared).apply(4);
    }

    private void test(Integer integer, Function<Integer, StringBuffer> function) {


        System.out.println(function.apply(integer));
        Function<Integer, StringBuffer> after = function.andThen(stringBuffer -> stringBuffer.append("999"));
//        Function<Integer, StringBuffer> before = function.compose(integer1 ->);
        System.out.println(after.apply(integer));
    }
}
