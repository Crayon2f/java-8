package com.ivan.java8.comparator;

import com.google.common.collect.Lists;
import com.crayon2f.common.pojo.Article;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by feiFan.gou on 2018/1/12 14:53.
 * 比较器,正对集合、数组
 */
class ComparatorLearn {

    @Test
    void comparing() {

        // <注意泛型>  !!!!!
        // param_1 : 直接指定要比较的key,按默认顺序比较
        Comparator<Article> compareByCount = Comparator.comparing(Article::getCount);
        // param_2 : 指定要比较的key,然后还要指定如何比较(适合较负复杂的比较逻辑)
        Comparator<Article> compareByTitle = Comparator.comparing(Article::getCount, (count1, count2) -> {
            System.out.println(count1);
            System.out.println(count2);
            return count1.compareTo(count2);
        });

        // example :
        Article.data.sort(Comparator.comparing(Article::getCount));
    }

    @Test
    void comparingGeneric() {

        Comparator<Article> doubleComparator = Comparator.comparingDouble(Article::getPrice);
        Article.data.sort(doubleComparator.reversed());
        System.out.println(Article.data);

        System.out.println("-------------------------------------------");

        Comparator<Article> intComparator = Comparator.comparingInt(Article::getCount);
        Article.data.sort(intComparator);
        System.out.println(Article.data);

        // long类型 同上 Comparator.comparingLong()

    }

    @Test
    void reverseOrder() {

        //倒序,泛型必须实现Comparable
        Comparator<String> comparator = Comparator.reverseOrder();
        List<String> reverseList = Lists.newArrayList("ad","b","nice","center");
        reverseList.sort(Comparator.comparing(String::length).reversed().thenComparing(comparator));
        System.out.println(reverseList);

    }

    @Test
    void naturalOrder() {

        //自然顺序,同样泛型必须实现Comparable
        List<String> reverseList = Lists.newArrayList("ad","b","nice","center");
        reverseList.sort(Comparator.comparing(String::length).reversed().thenComparing(Comparator.naturalOrder()));

    }

    @Test
    void thenComparing() {

        //1.传入一个Function,指定比较的key(即用什么比较)
        Comparator<Article> articleComparator = Comparator.comparing(Article::getAuthor).thenComparing(Article::getTitle).reversed();
        Article.data.sort(articleComparator);
        System.out.println(Article.data);

        //2.传入一个Comparator(也可重写)
        Comparator.comparing(Article::getAuthor).thenComparing(Collections.reverseOrder());

        //合并1&2 道理同comparing ,第二个参数处理复杂的比较逻辑
        Comparator.comparing(Article::getAuthor).thenComparing(Article::getTitle, Comparator.comparingInt(String::length));

//        Comparator.
    }

    @Test
    void thenComparingGeneric() {

        //同comparingInt,comparingDouble,comparingLong 原理一致

        Comparator<Article> articleComparator = Comparator.comparing(Article::getAuthor).thenComparingDouble(Article::getPrice);
        Article.data.sort(articleComparator);
        System.out.println(Article.data);

    }

    @Test
    void nullsFirst() {

        // 看到函数名字大概就知道了是啥意思了,碰到null值,不会报空指针,而是排到前面
        List<String> list = Lists.newArrayList("ad", "b", "nice", "center", null);
        Comparator<String> stringComparator = Comparator.nullsFirst(Comparator.comparing(String::length));
        list.sort(stringComparator);
        System.out.println(list); //[null, b, ad, nice, center]
    }

    @Test
    void nullsLast() {

        // 看到函数名字大概就知道了是啥意思了,碰到null值,不会报空指针,而是排到前面
        List<String> list = Lists.newArrayList("ad", "b", "nice", "center", null);
        Comparator<String> stringComparator = Comparator.nullsLast(Comparator.comparing(String::length));
        list.sort(stringComparator);
        System.out.println(list); //[b, ad, nice, center, null]
    }

    @Test
    void reversed() {

        //倒序
        List<String> list = Lists.newArrayList("ad", "b", "nice", "center", null);
        Comparator<String> stringComparator = Comparator.nullsLast(Comparator.comparing(String::length));
        Comparator<String> stringComparatorReversed = Comparator.nullsLast(Comparator.comparing(String::length)).reversed();
        list.sort(stringComparator);
        System.out.println(list); //[b, ad, nice, center, null]
        list.sort(stringComparatorReversed);
        System.out.println(list); //[null, center, nice, ad, b]

    }








}
