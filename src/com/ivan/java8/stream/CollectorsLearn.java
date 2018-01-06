package com.ivan.java8.stream;

import com.google.common.collect.Maps;
import com.ivan.java8.kit.StringKit;
import com.ivan.java8.pojo.Article;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by feiFan.gou on 2018/1/2 15:50.
 */
class CollectorsLearn {

    @Test
    void toList() {

        List<String> titleList = Article.data.parallelStream()
                .filter(article -> StringKit.isNotEmpty(article.getTitle())).map(Article::getTitle).collect(Collectors.toList());
        titleList.forEach(System.out::println);
    }

    @Test
    void toMap() {

        // param_type 1 : keyMapper, valueMapper
        Map<String, String> stringMap = Article.data.parallelStream().collect(Collectors.toMap(article ->  Optional.ofNullable(article.getTitle()).orElse("")
                , article -> Optional.ofNullable(article.getAuthor()).orElse("")));
        stringMap.forEach((key, value) -> System.out.println(key + " = " + value));

        System.out.println("==========================================");

        //param_type 2 : keyMapper, valueMapper, marginFunction
        Map<String, String> mergeMap = Article.data.parallelStream().collect(Collectors.toMap(
                article ->  Optional.ofNullable(article.getTitle()).orElse(""),
                article -> Optional.ofNullable(article.getAuthor()).orElse(""),
                (key, value) -> {
                    System.out.println(key + " = " + value);
                    return value;
                }
        ));
        mergeMap.forEach((key, value) -> System.out.println(key + " = " + value));
    }


    @Test
    void groupingBy() {

        Map<String, List<Article>> collect = Article.data.parallelStream().collect(Collectors.groupingBy(article->Optional.ofNullable(article.getAuthor()).orElse("")));
        collect.forEach((key, value) -> System.out.println(key + " = " + value.size()));

//        Collectors.
    }


}
