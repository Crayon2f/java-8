package com.ivan.java8.kit;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;

/**
 * Created by feiFan.gou on 2018/1/20 16:19.
 */
public class DateKit {

    public static LocalDate yesterday() {

        return LocalDate.now().minus(-1, ChronoUnit.DAYS);
    }

    public static LocalDate tomorrow() {

        return LocalDate.now().plus(1, ChronoUnit.DAYS);
    }

    public static LocalDate getFirstDayOfMonth(LocalDate current) {

        return Optional.ofNullable(current).orElse(LocalDate.now()).with(TemporalAdjusters.firstDayOfMonth());
    }

    public static LocalDate getFirstDayOfMonth() {

        return getFirstDayOfMonth(LocalDate.now());
    }


    public static LocalDate getLastDayOfMonth(LocalDate current) {

        return Optional.ofNullable(current).orElse(LocalDate.now()).with(TemporalAdjusters.lastDayOfMonth());
    }

    public static LocalDate getLastDayOfMonth() {

        return getLastDayOfMonth(LocalDate.now());
    }

    public static LocalDate getFirstDayOfWeek(LocalDate current) {

        return Optional.ofNullable(current).orElse(LocalDate.now()).with(ChronoField.DAY_OF_WEEK, DayOfWeek.MONDAY.getValue());
    }

    public static LocalDate getFirstDayOfWeek() {

        return getFirstDayOfWeek(LocalDate.now());
    }

    public static LocalDate getLastDayOfWeek(LocalDate current) {

        return Optional.ofNullable(current).orElse(LocalDate.now()).with(ChronoField.DAY_OF_WEEK, DayOfWeek.SUNDAY.getValue());
    }

    public static LocalDate getLastDayOfWeek() {

        return getLastDayOfWeek(LocalDate.now());
    }

}
