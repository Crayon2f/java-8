package com.crayon2f.java8.joda.date;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by feiFan.gou on 2018/2/1 11:54.
 * 使用方法,和LocalDate基本一致
 */
class LocalDateTimeLearn {

    @Test
    void staticMethod() {

        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        System.out.println(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        System.out.println(now.withHour(12));
    }
}
