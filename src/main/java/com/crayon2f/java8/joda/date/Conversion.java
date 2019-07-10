package com.crayon2f.java8.joda.date;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by feifan.gou@gmail.com on 2019/7/10 16:25.
 * 各种转换
 */
public class Conversion {

    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final String DATE_TIME_STR = "2019-07-10 12:00:43";
    private static final String DATE_STR = "2019-01-10";
    private static final String TIME_STR = "12:00:43";
    private static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    private static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";
    private static final ZoneOffset DEFAULT_ZONE_OFFSET = ZoneOffset.of("+8");
    private static final long TIMESTAMP = 1522741189270L; //2018-04-03T15:39:49.270

    /* ----------------------------------------------- String ==> TemporalAccessor ------------------------------- */

    /**
     * 字符串转LocalDateTime
     * @param dateTimeStr 时间字符串
     */
    LocalDateTime string2LocalDateTime(String dateTimeStr) {

        return string2LocalDateTime(dateTimeStr, DEFAULT_DATE_TIME_PATTERN);
    }

    LocalDateTime string2LocalDateTime(String dateTimeStr, String pattern) {

        try {
            return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    void testString2LocalDateTime() {
        System.out.println(string2LocalDateTime(DATE_TIME_STR));
        System.out.println(string2LocalDateTime(DATE_STR, DEFAULT_DATE_PATTERN));
        //bug：
        // 这点没有 之前的java.util.Date 做得好，只格式化日期或时间时候使用，不可以使用LocalDateTime！！！
        // 如果想转日期或者时间，请对应使用LocalDate 和 LocalTime
    }

    LocalDate string2LocalDate(String dateStr) {

        return string2LocalDate(dateStr, DEFAULT_DATE_PATTERN);
    }

    LocalDate string2LocalDate(String dateStr, String pattern) {

        try {
            return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    void testString2LocalDate() {

        System.out.println(string2LocalDate(DATE_STR));
        System.out.println(string2LocalDate("2018/12/17", "yyyy/MM/dd"));
    }

    /* string2LocalTime 就不写了,一样的道理 */


    /* --------------------------------------- TemporalAccessor ==> String 即(format) ------------------------------- */

    // 常用不多说

    String format(TemporalAccessor accessor, String pattern) {

        return DateTimeFormatter.ofPattern(pattern).format(accessor);
    }

    @Test
    void testFormat() {

        System.out.println(format(NOW, "yyyy年MM月dd日 HH时mm分ss秒")); //2019年07月10日 16时52分00秒
    }

    /* --------------------------------------- TemporalAccessor ==> timestamp 即(时间戳) ------------------------------- */

    long localDateTime2Seconds(LocalDateTime localDateTime) {

        //method1:
//        return localDateTime.toEpochSecond(ZoneOffset.of("+8"));
        //method2: 通过转换成Instant获取
        return localDateTime.toInstant(DEFAULT_ZONE_OFFSET).getEpochSecond();
    }

    long localDateTime2Millis(LocalDateTime localDateTime) {

        //method1:
//        return localDateTime.toInstant(DEFAULT_ZONE_OFFSET).toEpochMilli();
        //method2: 通过转Timestamp实现
        return Timestamp.valueOf(localDateTime).getTime();
    }

    @Test
    void testToTimestamp() {

        System.out.println(localDateTime2Seconds(NOW));
        System.out.println(localDateTime2Millis(NOW));
    }

    /* --------------------------------------- timestamp 即(时间戳) ==> TemporalAccessor------------------------------- */

    LocalDateTime millis2localDateTime(long millis) {

        //method1: 通过Instant
//        Instant instant = Instant.ofEpochMilli(timestamp);
//        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        //method2: 通过Timestamp
//        return new Timestamp(millis).toLocalDateTime();

        //method3: 通过Date
        Instant instant = new Date(millis).toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    LocalDateTime seconds2localDataTime(long seconds) {

        return LocalDateTime.ofEpochSecond(seconds, 0, DEFAULT_ZONE_OFFSET);

        //或者 * 1000 当做毫秒去处理
    }

    Instant millis2Instant(long millis) {

        return Instant.ofEpochMilli(millis);
    }

    Instant seconds2Instant(long seconds) {

        return Instant.ofEpochSecond(seconds);
    }

    @Test
    void testTimestamp2LocalDateTime() {

        System.out.println(ZoneId.systemDefault());
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("Asia/Shanghai")));
        System.out.println(millis2localDateTime(TIMESTAMP));
        System.out.println(seconds2localDataTime(TIMESTAMP / 1000));
        System.out.println(millis2Instant(TIMESTAMP));
        System.out.println(seconds2Instant(TIMESTAMP / 1000));
    }

    /* --------------------------------------- 获取当前时间(时间戳) ------------------------------- */

    @Test
    void nowMillisTimestamp() {

        //1.通过系统
        System.out.println(System.currentTimeMillis());
        //2.通过Date
        System.out.println(new Date().getTime());
        //3.通过Instant
        System.out.println(Instant.now().toEpochMilli());
        //4.通过Calendar
        System.out.println(Calendar.getInstance().getTimeInMillis());
        //5.通过LocalDateTime
        System.out.println(localDateTime2Millis(LocalDateTime.now()));

    }

    @Test
    void nowSecondsTimestamp() {

        System.out.println(Instant.now().getEpochSecond());
//        System.out.println(LocalDateTime.now().getLong(ChronoField.INSTANT_SECONDS));
    }

    @Test
    void test() {

        Clock clock = Clock.system(ZoneId.of("Asia/Shanghai"));
        System.out.println(Instant.now(clock));

        //使用Instant时候需要注意时区!!!! 它的默认时区会比当前少8个小时
    }

}
