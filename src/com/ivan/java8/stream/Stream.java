package com.ivan.java8.stream;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Streams;
import com.ivan.java8.kit.StringKit;
import com.ivan.java8.pojo.Article;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by feiFan.gou on 2017/10/14 17:16.
 */
public class Stream {


    /**
     * 如何生成
     */
    @Test
    void generator() {

        /* 1.从 Collection 和数组
            Collection.stream()
            Collection.parallelStream()
            Arrays.stream(T array) or Stream.of()
         */
        List<Integer> list = Lists.newArrayList(2,1,4,6,3,6);

        System.out.println(list.stream().max(Comparator.comparing(integer -> integer >= 6)));

        list.parallelStream().forEachOrdered(System.out::print);
        System.out.println();
        System.out.println(" =========== ");
        list.parallelStream().forEach(System.out::print);
        System.out.println();

        Arrays.stream(new int[]{1, 2, 3, 4, 5, 6, 6, 5}).distinct().forEach(System.out::print);
        System.out.println();
        System.out.println(Arrays.stream(new int[]{1, 3, 4, 5, 6, 34, 3}).peek(integer -> System.out.println("======")));

    }

    @Test
    void sort() {

        List<Article> sortList = Article.data.stream().sorted(Comparator.comparing(Article::getCount)).collect(Collectors.toList());

        List<Article> reversedList = Article.data.stream().sorted(Comparator.comparing(Article::getCount).reversed()).collect(Collectors.toList());

        sortList.forEach(System.out::println);

        System.out.println("==========================");

        reversedList.forEach(System.out::println);
    }

    @Test
    void filter() {

        List<Article> filterList = Article.data.stream().filter(article -> {

            if (null == article.getAuthor()) {
                return false;
            }
            return article.getAuthor().length() == 4;
        }).collect(Collectors.toList());

        filterList.forEach(System.out::println);
    }

    @Test
    void map() {

        List<String> mapList = Article.data.stream().map(article -> {
            String author = StringKit.trim(article.getAuthor());
            if (author.length() == 0) {
                return StringKit.empty;
            }
            return author.substring(2);
        }).collect(Collectors.toList());

        System.out.println(mapList);
    }

    /**
     * 和map类似，不同的是其每个元素转换得到的是Stream对象，会把子Stream中的元素压缩到父集合中；
     */
    @Test
    void flatMap() {

        List<Integer> countList = Article.data.stream().flatMap(article -> { //继续对父级进行操作后,再进行其他操作
            article.setCount(23);
//            return Arrays.stream(new Article[]{article});
            return Lists.newArrayList(article).stream();
        }).map(Article::getCount).collect(Collectors.toList());
        System.out.println(countList); //[23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23]
    }

    /**
     * 生成一个包含原Stream的所有元素的新Stream，同时会提供一个消费函数（Consumer实例），
     * 新Stream每个元素被消费的时候都会执行给定的消费函数,不会消费流；
     */
    @Test
    void peek() {

        System.out.println(java.util.stream.Stream.of("one", "two", "three", "four")
                .peek(e -> System.out.println("Peeked value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e)));
    }

    @Test
    void limit() {

        System.out.println(Article.data.stream().limit(3).collect(Collectors.toList()));
    }

    @Test
    void skip() {

        System.out.println(Article.data.stream().skip(9).collect(Collectors.toList()));

        //于limit结合
        System.out.println("======= 与limit结合 =======");
        System.out.println(Article.data.stream().limit(10).skip(9).collect(Collectors.toList()));
    }

    @Test
    void mapToInt() {

        Map<String, String> map = Maps.newHashMap();
        map.put("1", "a");
        map.put("2", "b");
        List<Map<String, String>> mapList = Lists.newArrayList(map);
        mapList.forEach(stringStringMap -> {
            System.out.println(stringStringMap);
            System.out.println(stringStringMap);
        });
        System.out.println(mapList);
    }

    @Test
    void find() {

        Article.data.stream().findFirst();
    }

    @Test
    /*
      由并行转化成串行
     */
    void sequential() {
        java.util.stream.Stream<String> stream = Arrays.stream(new String[]{"1", "3", "4"});
        System.out.println(stream.sequential().collect(Collectors.toList()));
    }
}
