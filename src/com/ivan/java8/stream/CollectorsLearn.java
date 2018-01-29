package com.ivan.java8.stream;

import com.google.common.collect.Maps;
import com.ivan.java8.kit.StringKit;
import com.ivan.java8.pojo.Article;
import com.ivan.java8.pojo.Employee;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by feiFan.gou on 2018/1/2 15:50.
 */
public class CollectorsLearn {

    @Test
    public void toList() {

        List<String> titleList = Article.data.parallelStream()
                .filter(article -> StringKit.isNotEmpty(article.getTitle())).map(Article::getTitle).collect(Collectors.toList());
        titleList.forEach(System.out::println);
    }

    @Test
    public void toMap() {

        // param_type 1 : keyMapper, valueMapper （key不能重复，value不能为null）
//        Map<String, String> stringMap = Article.data.parallelStream().collect(Collectors.toMap(article ->  Optional.ofNullable(article.getAuthor()).orElse("")
//                , article -> Optional.ofNullable(article.getTitle()).orElse("")));
//        stringMap.forEach((key, value) -> System.out.println(key + " = " + value));
//
//        System.out.println("==========================================");

        //param_type 2 : keyMapper, valueMapper, marginFunction(弥补param_type 1,重复key会报 'Duplicate key' 的错误，当有重复key 时候，会调用merge方法，具体怎么合并，
        // 就是mergeFunction里面的内容，如果没有重复的key，则不执行marginFunction )
//        Map<String, String> mergeMap = Article.data.stream().collect(Collectors.toMap(
//                article -> Optional.ofNullable(article.getAuthor()).orElse(""),
//                article -> Optional.ofNullable(article.getTitle()).orElse(""),
//                (oldValue, newValue) -> {
//                    System.out.println("same key ++++++ "+oldValue + " = " + newValue);
//                    return oldValue + newValue;
//                }
//        ));
//        mergeMap.forEach((key, value) -> System.out.println(key + " = " + value));
        // param_type 3 :
        Map<String, String> collect = Article.data.stream().collect(Collectors.toMap(
                article -> Optional.ofNullable(article.getAuthor()).orElse(""),
                article -> Optional.ofNullable(article.getTitle()).orElse(""),
                (oldValue, newValue) -> {
                    System.out.println("same key ++++++ " + oldValue + " = " + newValue);
                    return oldValue + newValue;
                }, () -> {
                    Map<String, String> result = Maps.newHashMap();
                    result.put("111", "3333");
                    result.put("1113", "3333");
                    result.put("1114", "3333");
                    return result;
                }
        ));
        collect.forEach((key, value) -> System.out.println(key + " = " + value));
    }


    @Test
    public void groupingBy() {

        Map<String, List<Article>> collect = Article.data.parallelStream().collect(Collectors.groupingBy(article -> Optional.ofNullable(article.getAuthor()).orElse("")));
        collect.forEach((key, value) -> System.out.println(key + " = " + value.size()));

    }

    @Test
    public void counting() {

        java.util.stream.Stream<String> stringStream = java.util.stream.Stream.of("1", "2", "3");

        System.out.println(stringStream.collect(Collectors.counting()));

    }

    @Test
    public void joining() {

        IntStream parallel = IntStream.range(0, 3000).parallel();
        Stream<String> stringStream = parallel.mapToObj(String::valueOf).parallel();
        System.out.println(stringStream.collect(Collectors.joining(",", "[", "]")));
    }

    @Test
    public void toSet() {

        java.util.stream.Stream<String> stringStream = Stream.of("1", "1", "2", "3", "3");

        System.out.println(stringStream.collect(Collectors.toSet()));
    }


    @Test
    public void mapping() {

        Map<String, String> collect = Article.data.stream().collect(Collectors.groupingBy(Article::getAuthor,
                Collectors.mapping(Article::getTitle,
                        Collectors.joining("|"))));
        System.out.println(collect);
    }


    @Test
    public void maxBy_minBy() {

        java.util.stream.Stream<String> stringStream = Stream.of("a", "c", "g", "v", "b");
//        Optional<String> maxOptional = stringStream.collect(Collectors.maxBy(Comparator.reverseOrder()));
//        maxOptional.ifPresent(System.out::println);
        Optional<String> minOptional = stringStream.collect(Collectors.minBy(Comparator.reverseOrder()));
        minOptional.ifPresent(System.out::println);

    }

    /**
     * collecting 之后,再操作
     */
    @Test
    public void collectingAndThen() {

        Map<String, String> map = Article.data.stream().collect(
                Collectors.collectingAndThen(Collectors.groupingBy(Article::getAuthor, Collectors.mapping(Article::getTitle, Collectors.joining(","))), result -> {
                    result.putIfAbsent("result", "aa");
                    return Collections.unmodifiableMap(new TreeMap<>(result));
                }));

        System.out.println(map);
    }


    /**
     * 分区
     */
    @Test
    public void partitioningBy() {

        // 默认以true false分区, 返回分区实体集合
        Map<Boolean, List<Employee>> collect = Employee.persons().stream().collect(Collectors.partitioningBy(Employee::isMale));
        System.out.println(collect);

        // 再次拼装
//        Map<Boolean,String> partitioningByMaleGender = Employee.persons()
//                .stream()
//                .collect(Collectors.partitioningBy(Employee::isMale,
//                        Collectors.mapping(Employee::getName, Collectors.joining(", "))));
        Map<Boolean,String> partitioningByMaleGender = Employee.persons()
                .stream()
                .collect(Collectors.partitioningBy(Employee::isMale,
                        Collectors.mapping(Employee::getName, Collectors.joining(", "))));
        System.out.println(partitioningByMaleGender);
    }

    @Test
    public void averaging() {

//        Stream<String> stringStream = Stream.of("1.4","2.4","3.5"); // double 值
//        Stream<String> stringStream = Stream.of("1","2","3"); //int 值
        Stream<String> stringStream = Stream.of("1","2","3"); //long 值
//        double d =  stringStream.collect(Collectors.averagingDouble(Double::parseDouble));
//        double d = stringStream.collect(Collectors.averagingInt(Integer::parseInt));
        double d = stringStream.collect(Collectors.averagingLong(Long::valueOf));
        System.out.println(d);

    }

    /**
     * 返回 SummaryStatistics 统计
     */
    @Test
    public void summarizing() {

        Stream<String> stringStream = Stream.of("1","2","3");

//        IntSummaryStatistics statistics = stringStream.collect(Collectors.summarizingInt(Integer::valueOf));
//        System.out.println(statistics.getMax());

//        DoubleSummaryStatistics collect = stringStream.collect(Collectors.summarizingDouble(Double::valueOf));
//        System.out.println(collect.getAverage());

        LongSummaryStatistics longSummaryStatistics = stringStream.collect(Collectors.summarizingLong(Long::valueOf));
        System.out.println(longSummaryStatistics.getSum());

    }

    @Test
    public void summing() {

        Stream<String> stringStream = Stream.of("1","2","3");
        Integer collect = stringStream.collect(Collectors.summingInt(Integer::parseInt));
        System.out.println(collect);

        // double,long 同上 summingDouble summingLong
    }

    @Test
    public void test() {


    }
}
