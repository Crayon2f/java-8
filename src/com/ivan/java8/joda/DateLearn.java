package com.ivan.java8.joda;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAmount;

/**
 * Created by feiFan.gou on 2018/1/20 15:13.
 * DateTime 学习
 */
public class DateLearn {

    @Test
    public void localDate() {

        LocalDate date = LocalDate.now();
        System.out.println(String.format("toString : %s", date));

        System.out.println(String.format("day of month : %s", date.getDayOfMonth()));
        System.out.println(String.format("get (day of month) : %s", date.get(ChronoField.DAY_OF_MONTH)));

        TemporalAmount amount = Period.ofDays(2);

        System.out.println(date.plus(amount));
        System.out.println(String.format("format date : %s", date.format(DateTimeFormatter.ISO_LOCAL_DATE)));
        System.out.println(String.format("format date : %s", date.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"))));

        System.out.println(Instant.now());
    }

}
