package com.ivan.java8.optional;

import com.ivan.java8.pojo.Article;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * Created by feiFan.gou on 2017/12/22 17:56.
 */
class OptionalLearn {


    @Test
    void isPresent() {

        Article article = new Article("title");
        System.out.println(test(article));
        System.out.println(test(null));
    }

    private String test(Article article) {

        Optional<Article> articleOptional = Optional.ofNullable(article);
        return articleOptional.map(article1 -> {
            System.out.println(article1.getTitle());
            System.out.println(article1.getCount());
            return "success";
        }).orElse("fail");
    }
}
