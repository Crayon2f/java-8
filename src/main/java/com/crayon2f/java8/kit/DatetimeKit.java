package com.crayon2f.java8.kit;

import javax.annotation.Nonnull;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by feifan.gou@gmail.com on 2019/7/17 15:30.
 *
 * @version 1.0
 * @see LocalDateTime,LocalDate,LocalTime
 * <p>
 * deprecate java.util.Date
 * </p>
 * @since jdk1.8
 */
public final class DatetimeKit {

    private DatetimeKit() {}

    /* 凌晨零点 */
    public static final LocalTime ZERO_CLOCK = LocalTime.of(0, 0, 0);
    /* 午夜23:59:59 */
    public static final LocalTime NIGHT_CLOCK = LocalTime.of(23, 59, 59);
    /* Asia/Shanghai */
    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("Asia/Shanghai");
    /* The epoch year, '1970-01-01'*/
    private static final LocalDate EPOCH = LocalDate.of(1970, 1, 1);
    /* 东八区 */
    private static final ZoneOffset DEFAULT_ZONE_OFFSET = ZoneOffset.of("+8");

    /**
     * localDateTime ==> string
     *
     * @param datetime localDateTime 例: LocalDateTime.now()
     * @return 默认日期时间格式 yyyy-MM-dd HH:mm:ss 例: 2019-07-17 19:23:34
     */
    public static String toString(LocalDateTime datetime) {

        return toString(datetime, FormatPattern.DATETIME_UNIVERSAL);
    }

    /**
     * LocalDate ==> string
     *
     * @param date localDate 例: LocalDate.now()
     * @return 默认日期时间格式 yyyy-MM-dd 例: 2019-07-17
     */
    public static String toString(LocalDate date) {

        return date.toString();
    }

    /**
     * LocalTime ==> string
     *
     * @param time localDateTime 例: LocalTime.now()
     * @return 默认日期时间格式 HH:mm:ss 例: 19:23:34
     */
    public static String toString(LocalTime time) {

        return toString(time, FormatPattern.TIME_UNIVERSAL);
    }


    /**
     * LocalDateTime, LocalDate, LocalTime ==> string
     *
     * @param temporal LocalDateTime, LocalDate, LocalTime 中任意, 如果传 Temporal 其他实现 返回 null
     * @param pattern  时间格式
     * @return 按着 patter 格式化后的字符串
     */
    public static String toString(@Nonnull Temporal temporal, @Nonnull FormatPattern pattern) {

        Class<? extends Temporal> temporalClass = temporal.getClass();
        if (pattern.accessorClass.equals(temporalClass) || temporalClass.equals(LocalDateTime.class)) {
            return pattern.getDateTimeFormatter().format(temporal);
        }
        if (temporalClass.equals(LocalDate.class)) {
            if (pattern.accessorClass.equals(LocalDateTime.class)) {
                LocalDate localDate = (LocalDate) temporal;
                return toString(localDate.atTime(ZERO_CLOCK), pattern); // localDate + 00:00:00
            } else {
                return toString(ZERO_CLOCK, pattern); //00:00:00
            }
        } else if (temporalClass.equals(LocalTime.class)) {
            if (pattern.accessorClass.equals(LocalDateTime.class)) {
                LocalTime localTime = (LocalTime) temporal;
                return toString(localTime.atDate(EPOCH), pattern); //1970-01-01 + LocalTime
            } else {
                return toString(EPOCH, pattern);
            }
        }
        throw new IllegalArgumentException("temporal don't match the pattern");
    }

    /**
     * string ==> LocalDateTime
     *
     * @param datetime 默认格式的日期时间字符串 例: 2019-07-17 19:23:34
     * @return 2019-07-17T19:23:34
     */
    public static LocalDateTime toDatetime(String datetime) {

        return toDatetime(datetime, FormatPattern.DATETIME_UNIVERSAL);
    }

    /**
     * seconds ==> LocalDateTime
     *
     * @param seconds 秒数
     * @return 2019-07-17T19:23:34
     */
    public static LocalDateTime toDatetimeBySeconds(long seconds) {

        return LocalDateTime.ofEpochSecond(seconds, 0, DEFAULT_ZONE_OFFSET);
    }

    /**
     * seconds ==> LocalDateTime
     *
     * @param millis 毫秒数
     * @return 2019-07-17T19:23:34
     */
    public static LocalDateTime toDatetimeByMillis(long millis) {

        return new Timestamp(millis).toLocalDateTime();
    }

    /**
     * string ==> LocalDateTime
     *
     * @param datetime 指定格式的日期时间字符串 例: 2019/07/17 19:23:34
     * @param pattern  日期时间格式 例: yyyy/MM/dd HH:mm:ss
     * @return 2019-07-17T19:23:34
     */
    public static LocalDateTime toDatetime(String datetime, @Nonnull FormatPattern pattern) {

        if (pattern.accessorClass.equals(LocalDateTime.class)) {
            return LocalDateTime.parse(datetime, pattern.getDateTimeFormatter());
        }
        throw new IllegalArgumentException("string datetime don't match the pattern");
    }

    /**
     * string(date) ==> LocalDateTime
     *
     * @param date 默认格式的(日期)字符串 例: 2019-07-17
     * @return 所传日期的零点 2019-07-17T00:00:00
     */
    public static LocalDateTime toDatetimeByDate(String date) {

        return toDatetime(String.format("%s 00:00:00", date), FormatPattern.DATETIME_UNIVERSAL);
    }

    /**
     * string(date) ==> LocalDateTime
     *
     * @param date    指定格式的(日期)字符串 例: 2019-07-17
     * @param pattern 必须是日期时间格式 例如: yyyy-MM-dd HH:mm:ss
     * @return 所传日期的零点 2019-07-17T00:00:00
     */
    public static LocalDateTime toDatetimeByDate(String date, @Nonnull FormatPattern pattern) {

        return toDatetime(String.format("%s 00:00:00", date), pattern);
    }

    /**
     * string(time) ==> LocalDateTime
     *
     * @param time 默认格式的(时间)字符串 例: 19:23:34
     * @return 1970-01-01的所传时刻 1970-01-01T19:23:34
     */
    public static LocalDateTime toDatetimeByTime(String time) {

        return toDatetime(String.format("%s %s", EPOCH, time), FormatPattern.DATETIME_UNIVERSAL);
    }

    /**
     * string(time) ==> LocalDateTime
     *
     * @param time    指定格式的(时间)字符串 例: 19:23:34
     * @param pattern 必须是日期时间格式 例如: yyyy-MM-dd HH:mm:ss
     * @return 1970-01-01的所传时刻 1970-01-01T19:23:34
     */
    public static LocalDateTime toDatetimeByTime(String time, @Nonnull FormatPattern pattern) {

        return toDatetime(String.format("%s %s", EPOCH, time), pattern);
    }

    /**
     * string ==> LocalDate
     *
     * @param date 默认格式的日期(yyyy-MM-dd) 例: 2019/07/17
     * @return LocalDate
     */
    public static LocalDate toDate(String date) {

        return toDate(date, FormatPattern.DATE_UNIVERSAL);
    }

    /**
     * string ==> LocalDate
     *
     * @param date    指定格式的日期字符串 例: 2019/07/17
     * @param pattern 指定的日期格式 例: yyyy/MM/dd
     * @return LocalDate
     */
    public static LocalDate toDate(String date, @Nonnull FormatPattern pattern) {

        if (pattern.accessorClass.equals(LocalDate.class)) {
            return LocalDate.parse(date, pattern.getDateTimeFormatter());
        }
        throw new IllegalArgumentException("string date don't match the pattern");
    }

    /**
     * string(datetime) ==> LocalDate
     *
     * @param datetime 时间日期 例: 2019-07-17 19:23:34
     * @param pattern  指定的日期格式 例: yyyy-MM-dd
     * @return LocalDate 会自动舍弃时间
     */
    public static LocalDate toDateByDatetime(String datetime, @Nonnull FormatPattern pattern) {

        return toDate(datetime.split("(\\s+)")[0], pattern);
    }

    /**
     * string ==> LocalTime
     *
     * @param time 默认格式的时间 例: 19:23:34
     * @return LocalTime
     */
    public static LocalTime toTime(String time) {

        return toTime(time, FormatPattern.TIME_UNIVERSAL);
    }

    /**
     * string ==> LocalTime
     *
     * @param time    指定格式的时间 例: 19时23分34秒
     * @param pattern 指定的时间格式 例: HH时mm时ss秒
     * @return LocalTime
     */
    public static LocalTime toTime(String time, @Nonnull FormatPattern pattern) {

        if (pattern.accessorClass.equals(LocalTime.class)) {
            return LocalTime.parse(time, pattern.getDateTimeFormatter());
        }
        throw new IllegalArgumentException("string time don't match the pattern");
    }

    /**
     * string(datetime) ==> LocalTime
     *
     * @param datetime 日期时间 例: 2019-07-17 19:23:34
     * @param pattern  指定的时间格式 HH:mm:ss
     * @return LocalTime 会自动舍弃日期
     */
    public static LocalTime toTimeByDatetime(String datetime, @Nonnull FormatPattern pattern) {

        return toTime(datetime.split("(\\s+)")[1], pattern);
    }

    /**
     * localDateTime ==> millis
     *
     * @param datetime LocalDateTime
     * @return 毫秒
     */
    public static long toMillis(@Nonnull LocalDateTime datetime) {

        return Optional.of(datetime)
                .map(Timestamp::valueOf)
                .map(Timestamp::getTime)
                .orElse(0L);
    }

    /**
     * localDateTime ==> seconds
     *
     * @param datetime LocalDateTime
     * @return 秒
     */
    public static long toSeconds(@Nonnull LocalDateTime datetime) {

        return Optional.of(datetime)
                .map(ths -> ths.toEpochSecond(DEFAULT_ZONE_OFFSET))
                .orElse(0L);
    }

    /**
     * @param startInclusive 包含开始
     * @param endExclusive   包含结束
     * @param unitFn         要转换的时间单位函数
     * @return 区间值
     */
    public static long between(@Nonnull Temporal startInclusive, @Nonnull Temporal endExclusive, Function<Duration, Long> unitFn) {

        return unitFn.apply(Duration.between(startInclusive, endExclusive));
    }


    /**
     * 时间格式枚举
     */
    public enum FormatPattern {

        /* LocalDateTime */
        DATETIME_UNIVERSAL("yyyy-MM-dd HH:mm:ss", LocalDateTime.class),
        DATETIME_NON_SECOND("yyyy-MM-dd HH:mm", LocalDateTime.class),
        DATETIME_CHINESE("yyyy年MM月dd日 HH时mm时ss秒", LocalDateTime.class),

        /* LocalDate */
        DATE_UNIVERSAL("yyyy-MM-dd", LocalDate.class),
        DATE_NON_DAY("yyyy-MM", LocalDate.class),
        DATE_SLASH("yyyy/MM/dd", LocalDate.class),
        DATE_CHINESE("yyyy年MM月dd日", LocalDate.class),

        /* LocalTime */
        TIME_UNIVERSAL("HH:mm:ss", LocalTime.class),
        TIME_NON_SECONDS("HH:mm", LocalTime.class),
        TIME_CHINESE("HH时mm分ss秒", LocalTime.class),

        ;

        private final String pattern;
        private final Class<? extends Temporal> accessorClass;

        FormatPattern(String pattern, Class<? extends Temporal> accessorClass) {
            this.pattern = pattern;
            this.accessorClass = accessorClass;
        }

        private DateTimeFormatter getDateTimeFormatter() {

            return DateTimeFormatter.ofPattern(pattern);
        }
    }


    /**
     * LocalDateTime 构造器
     */
    public static class DatetimeBuilder {

        private LocalDateTime datetime;

        private DatetimeBuilder(LocalDateTime datetime) {

            this.datetime = datetime;
        }

        @Override
        public String toString() {

            return toString(FormatPattern.DATETIME_UNIVERSAL);
        }

        public String toString(FormatPattern pattern) {

            return DatetimeKit.toString(datetime, pattern);
        }

        public LocalDateTime toDatetime() {
            return datetime;
        }

        public long toSeconds() {

            return DatetimeKit.toSeconds(datetime);
        }

        public long toMillis() {

            return DatetimeKit.toMillis(datetime);
        }
    }

    /**
     * 通过 LocalDateTime 构造
     */
    public static DatetimeBuilder init(@Nonnull LocalDateTime datetime) {

        return new DatetimeBuilder(datetime);
    }

    /**
     * 通过 String 构造
     */
    public static DatetimeBuilder stringInit(String datetime, FormatPattern pattern) {

        return new DatetimeBuilder(DatetimeKit.toDatetime(datetime, pattern));
    }

    /**
     * 通过毫秒数构造
     */
    public static DatetimeBuilder millisInit(long millis) {

        return new DatetimeBuilder(toDatetimeByMillis(millis));
    }

    /**
     * 通过秒数构造
     */
    public static DatetimeBuilder secondsInit(long seconds) {

        return new DatetimeBuilder(toDatetimeBySeconds(seconds));
    }

}
