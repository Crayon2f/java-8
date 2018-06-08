package com.ivan.java8;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ivan.java8.function.FunInterface;
import com.ivan.java8.kit.StringKit;
import com.ivan.java8.pojo.Article;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
    public void test() throws IOException, ParseException {

        BufferedReader reader = new BufferedReader(new FileReader("D:\\downloads\\剧本1.lrc"));
        String line;
        while (null != (line = reader.readLine())) {
            System.out.println("===================");
            Optional.of(line).filter(StringKit::isNotEmpty).ifPresent(System.out::println);
        }
        reader.close();

        FunInterface funInterface = ((x, y) -> {
            System.out.println(x + y);
            System.out.println(x + y);
        });


        Float f = 0.345456f;
        System.out.println(String.format("%.2f", f * 100));

        System.out.println(Character.isDigit('a'));
        System.out.println(Character.isWhitespace(' '));
        System.out.println(Charset.forName("UTF-8").toString());

        "abc".chars().forEach(c -> System.out.println((char) c));

    }

    @Test
    @SuppressWarnings("unchecked")
    public void test2() throws IOException {

//        Path path = Paths.get("C:\\Users\\feifan.gou\\Desktop\\download\\increase_copper_cache.backup");
//
//        String strJson = new String(Files.readAllBytes(path));
//        Map<Integer,List<Long>> map = JSONObject.parseObject(strJson, Map.class);
//
//        map.forEach((key, value) -> System.out.println(key + "==>" + value.size()));

        LocalDate date = LocalDate.now().minusDays(30);
        System.out.println(date);

//        Collectors.groupingBy()
        Map<Boolean, String> map = Article.data.stream().collect(Collectors.partitioningBy(article -> article.getCount() == 0,
                Collectors.mapping(Article::getTitle, Collectors.joining())));
    }

}
