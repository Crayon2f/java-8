package com.ivan.java8;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ivan.java8.kit.StringKit;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Map<String, String> map = Maps.newHashMap();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        map.put("d", "4");
        map.put("e", "5");
        map.put("f", "6");
        map.put("g", "7");
        map.put("h", "8");
        map.put("i", "9");
        map.put("j", "10");

        String a = map.computeIfAbsent("a", String::trim);
        System.out.println(a);

        List<String> stringList = Lists.newArrayList();
        stringList.add("sss");
        stringList.add("dd");
        stringList.add("dd");
        stringList.add("cc");
        stringList.add("ee");
        System.out.println(stringList.removeIf(String::isEmpty));
        stringList.replaceAll(String::toUpperCase);
        System.out.println(stringList);
        boolean b = stringList.stream().anyMatch(string -> string.length() > 2);
        System.out.println(b);
        Spliterator spliterator = Spliterators.spliterator(stringList, stringList.size());
        spliterator.forEachRemaining(System.out::println);
    }

    @Test
    void test() throws IOException, ParseException {

//        System.out.println(Stream.of("a", "b", "c").peek(System.out::println).peek(System.out::println).collect(Collectors.toList()));

//        URL url = new URL("");

//        String string = "8,9,2,3,4,9,2,7,0,5,9,2,3,8,4,7,5,6,2,8,3,4,5,6,7,8";
//
//        StringBuilder builder = new StringBuilder("");
//        List<Integer> collect = Lists.newArrayList(string.split(",")).parallelStream().peek(s -> builder.append(",").append(s))
//                .map(Integer::valueOf).collect(Collectors.toList());
//        System.out.println(builder);
//        System.out.println(collect);

        System.out.println(StringKit.isEmpty(StringKit.empty));
        System.out.println(StringKit.isNotEmpty(null));
        System.out.println(StringKit.trim(null).length());
    }

}
