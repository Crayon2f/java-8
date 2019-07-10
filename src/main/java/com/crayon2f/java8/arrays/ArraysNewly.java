package com.crayon2f.java8.arrays;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * Created by feifan.gou@gmail.com on 2019/5/21 16:57.
 */

class ArraysNewly {

    private int[] ints = {1, 2, 3, 4, 5};

    @Test
    void parallelPrefix() {

        Arrays.parallelPrefix(ints, (pre, next) -> pre * next);
        System.out.println(Arrays.toString(ints));
    }
}
