package com.ivan.java8.kit;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Created by feiFan.gou on 2018/1/20 16:19.
 */
public class DateKit {

    public static LocalDate yesterday() {

        return LocalDate.now().plus(1, ChronoUnit.DAYS);
    }


    public static String yesterdayStr() {

        return yesterday().toString();
    }
}
