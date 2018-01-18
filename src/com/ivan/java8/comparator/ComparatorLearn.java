package com.ivan.java8.comparator;

import com.ivan.java8.pojo.Article;
import org.junit.Test;

import java.util.Comparator;

/**
 * Created by feiFan.gou on 2018/1/12 14:53.
 * 比较器,正对集合、数组
 */
public class ComparatorLearn {

    @Test
    public void comparing() {

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
    public void comparingGeneric() {

        Comparator<Article> doubleComparator = Comparator.comparingDouble(Article::getPrice);
        Article.data.sort(doubleComparator.reversed());
        System.out.println(Article.data);
    }
}
