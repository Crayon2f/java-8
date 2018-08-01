package com.crayon2f.java8.optional;

import org.junit.jupiter.api.Test;

import java.util.OptionalInt;

/**
 * Created by feiFan.gou on 2018/1/29 16:22.
 * 个人感觉很鸡肋
 */
class OptionGeneric {

    @Test
    void test() {

        int i = 0;
        System.out.println(OptionalInt.of(i).orElseGet(() -> {
            System.out.println("");
            return 4 * 7;
        }));
        System.out.println(OptionalInt.empty().orElseGet(() -> {
            System.out.println("");
            return 4 * 7;
        }));
        OptionalInt optionalInt = OptionalInt.of(4);
        //根本就不会进到orElse,因为of已经传入了一个int值,不可能是null,
        System.out.println(optionalInt.orElse(4));
        //除非 使用empty,但是没啥意义啊,使用了empty 就不可能使用 ifPresent(),因为根本进不去
        OptionalInt empty = OptionalInt.empty();
        System.out.println(empty.orElse(3));
        empty.ifPresent(System.out::println); //输出空值 ""

        //也就是说
        // OptionalInt.of 只能使用 ifPresent()
        // OptionalInt.empty 只能使用 orElse相关的
        // 跟我自己定义 一个int值没啥区别,倒不如直接使用Optional 还可以去除null值

//        OptionalLong,OptionalDouble 使用和 OptionalInt一样的。
    }
}
