package com.ivan.java8.joda.date;

import com.ivan.java8.kit.DateKit;
import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalQuery;

/**
 * Created by feiFan.gou on 2018/1/20 15:13.
 * DateTime 学习
 */
public class LocalDateLearn {

    @Test
    public void staticMethod() {

        LocalDate date = LocalDate.now();
        System.out.println(String.format("toString : %s", date));

        System.out.println(LocalDate.of(2018, Month.JANUARY, 1));

        System.out.println(LocalDate.ofEpochDay(0));  //从 1970-01-01 开始推后 xx天;
        System.out.println(LocalDate.ofYearDay(2018, 200)); // 从xx年,推后xx天

        System.out.println(LocalDate.parse("2017-10-23")); // str -> date

        System.out.println(LocalDate.parse("2017,10,23", DateTimeFormatter.ofPattern("yyyy,MM,dd"))); //根据指定格式得到date

        System.out.println(LocalDate.MAX); //+999999999-12-31
        System.out.println(LocalDate.MIN); //-999999999-01-01

    }

    @Test
    public void method() {

        LocalDate date = LocalDate.now();
        System.out.println(String.format("day of month : %s", date.getDayOfMonth()));
        System.out.println(String.format("get (day of month) : %s", date.get(ChronoField.DAY_OF_MONTH)));
        System.out.println(String.format("format date : %s", date.format(DateTimeFormatter.ISO_LOCAL_DATE)));
        System.out.println(String.format("format date : %s", date.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"))));
        System.out.println(Instant.now());

        System.out.println(LocalDate.now().plus(Period.ofDays(12)));
        //1月29 + 一个月应该是2月29号,但是2018年2月没有29日,则返回最后一天,不报错,很好
        System.out.println(LocalDate.of(2018, 1, 29).plus(Period.ofMonths(1)));
        //但是初始化如果日期超过了,就会报错 Invalid value for DayOfMonth
//        System.out.println(LocalDate.of(2018,1,32));
        //获取date的最开始时间<DateTime> xxxx-xx-xx 00:00:00,一天的开始
        System.out.println(date.atStartOfDay());
        //一天某个时间,简单粗暴,很好
        System.out.println(date.atTime(12, 12));
        System.out.println(date.atTime(12, 12, 12, 12));
        System.out.println(date.atTime(LocalTime.now()));

        //compareTo 比较,返回差几天 例如 2018-01-12.compareTo(2018-01-14) = -2
        System.out.println(date.compareTo(DateKit.yesterday().plus(-1, ChronoUnit.DAYS)));
        System.out.println(date.compareTo(LocalDate.now()));
        System.out.println(date.compareTo(DateKit.tomorrow()));

        //rang 顾名思义,是获取个范围
        System.out.println(date.range(ChronoField.DAY_OF_WEEK));
        System.out.println(date.range(ChronoField.DAY_OF_MONTH));
        System.out.println(date.range(ChronoField.DAY_OF_YEAR));
        System.out.println(date.range(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR));

        date.adjustInto(LocalDate.now());

        //调整日期,根据date
        System.out.println(date.with(TemporalAdjusters.firstDayOfMonth()));//一个月的第一天
        System.out.println(date.with(TemporalAdjusters.lastDayOfMonth()));//一个月的最后一天
        System.out.println(date.withDayOfMonth(15));
        System.out.println(LocalDate.parse("2018-01-28").with(ChronoField.DAY_OF_WEEK, DayOfWeek.MONDAY.getValue()));
        System.out.println(date.withMonth(2));

        System.out.println(DateKit.getLastDayOfWeek());
        //获取年表
        System.out.println(date.getChronology().dateNow());


        System.out.println(date.getDayOfWeek());//当前周几
        System.out.println(date.getMonth()); //当前月份
        System.out.println(date.getMonthValue()); //月份数字
        System.out.println(date.getYear()); //当前月份

        System.out.println(date.getLong(ChronoField.DAY_OF_MONTH)); //一个月的第几天
        System.out.println(date.getLong(ChronoField.DAY_OF_WEEK)); //一周的第几天
        System.out.println(date.getLong(ChronoField.DAY_OF_YEAR)); //一年的第几天

        System.out.println(date.getEra().getValue()); //获得纪元

        System.out.println(date.isAfter(LocalDate.parse("2018-01-21"))); //在...之后
        System.out.println(date.isBefore(LocalDate.parse("2018-01-21"))); //在...之前
        System.out.println(date.isEqual(LocalDate.parse("2018-01-22"))); //日期相等

        System.out.println(date.isLeapYear()); //是不是闰年
        System.out.println(LocalDate.parse("2016-01-09").isLeapYear()); //是不是闰年

        System.out.println(Year.of(2020).isLeap());

        System.out.println(date.isSupported(ChronoField.DAY_OF_YEAR));

        System.out.println(date.lengthOfMonth()); //当前月有几天
        System.out.println(date.lengthOfYear()); //当年有几天

        System.out.println(date.minus(1, ChronoUnit.DAYS));
        System.out.println(date.minusWeeks(1));

        TemporalQuery<String> query = temporal -> String.valueOf(temporal.get(ChronoField.YEAR));
        System.out.println(date.query(query));// 暂时没搞太清楚是干嘛用的

        System.out.println(date.until(LocalDate.parse("2018-01-21")).getDays()); //距离 xx 多久
        System.out.println(date.until(LocalDate.parse("2018-02-23")).getMonths()); //距离 xx 多久
    }

}
