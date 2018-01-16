package com.ivan.java8.comparator;

import com.ivan.java8.pojo.Article;
import org.junit.Test;

import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Created by feiFan.gou on 2018/1/12 14:53.
 * 比较器,正对集合、数组
 */
public class ComparatorLearn {

    @Test
    public void comparing() {

        System.out.println(Article.data.stream().map(Article::getCount).collect(Collectors.toList()));
        Article.data.sort(Comparator.comparing(Article::getCount));
        System.out.println(Article.data.stream().map(Article::getCount).collect(Collectors.toList()));

        Comparator compareByCount = Comparator.comparing(Article::getCount);
//        Comparator compareByTitle = Comparator.comparing(Integer::intValue, key -> {
//
//        });

    }

//    public void
}
