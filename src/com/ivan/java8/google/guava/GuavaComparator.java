package com.ivan.java8.google.guava;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.ivan.java8.pojo.Article;
import org.junit.Test;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by feiFan.gou on 2017/12/8 11:57.
 */
public class GuavaComparator {

    @Test
    public void ordering() {

        List<String> list = Lists.newArrayList();
        list.add("neron");
        list.add("peida");
        list.add("jerry");
        list.add("eva");
        list.add("harry");
        list.add("jhon");

//        System.out.println("list:"+ list);
//
//        Ordering<String> naturalOrdering = Ordering.natural();
//        Ordering<Object> usingToStringOrdering = Ordering.usingToString();
//        Ordering<Object> arbitraryOrdering = Ordering.arbitrary();
//
//        System.out.println("naturalOrdering:"+ naturalOrdering.sortedCopy(list));
//        System.out.println("usingToStringOrdering:"+ usingToStringOrdering.sortedCopy(list));
//        System.out.println("arbitraryOrdering:"+ arbitraryOrdering.sortedCopy(list));

        List<Article> articleList = Lists.newArrayList();
        articleList.add(new Article("harry"));
        articleList.add(new Article("jerry"));
        articleList.add(new Article("neron"));
        articleList.add(new Article("peida"));
        articleList.add(new Article("jhon"));
        articleList.add(new Article("2222"));
        articleList.add(new Article("eva"));
//        System.out.println(new Article("eva").getTitle());
        Ordering<String> ordering = Ordering.explicit(list);
        System.out.println(articleList.stream().sorted(Comparator.comparing(Article::getTitle)).collect(Collectors.toList()));

        articleList.parallelStream().sorted((article1, article2) -> {
            if (list.contains(article1.getTitle()) && list.contains(article2.getTitle())) {
                return ordering.nullsFirst().compare(article1.getTitle(), article2.getTitle());
            }
            return 1;
        }).collect(Collectors.toList()).forEach(article -> System.out.println(article.getTitle()));

    }
}
