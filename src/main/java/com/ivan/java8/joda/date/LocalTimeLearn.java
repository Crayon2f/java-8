package com.ivan.java8.joda.date;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by feiFan.gou on 2018/1/23 11:39.
 */
class LocalTimeLearn {

    @Test
    void method() {

        LocalTime time = LocalTime.now();
//        LocalTime.MAX
        System.out.println(time.format(DateTimeFormatter.ofPattern("HH,mm,ss,SS")));
        System.out.println(time.format(DateTimeFormatter.ISO_TIME));
        System.out.println(time);
    }
}
