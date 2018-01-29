package com.ivan.java8.optional;

import org.junit.Test;

import java.util.OptionalInt;

/**
 * Created by feiFan.gou on 2018/1/29 16:22.
 */
public class OptionGeneric {

    @Test
    public void test() {

        Integer i = 6;
        System.out.println(OptionalInt.of(i).orElseGet(() -> {
            System.out.println("");
            return 4 * 7;
        }));
    }
}
