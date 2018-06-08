package com.ivan.java8.joda.date;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * Created by feiFan.gou on 2018/6/6 11:30.
 */
public class ZoneDateTimeLearn {

    @Test
    public void test() {

        ZonedDateTime dateTime = ZonedDateTime.now();
        System.out.println(dateTime);
        System.out.println(LocalDateTime.now());
    }
}
