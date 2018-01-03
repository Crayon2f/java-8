package com.ivan.java8.optional;

import com.ivan.java8.kit.StringKit;
import com.ivan.java8.pojo.Article;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * Created by feiFan.gou on 2017/12/22 17:56.
 */
class OptionalLearn {


    private static boolean bool = false;

    static {
        int a = 34, b = 45;
        if (a * 3 > b) {
            bool = true;
        }
    }

    @Test
    void ifPresent() {

        Article article = new Article("title");
        System.out.println(testIfPresent(article));
        System.out.println(testIfPresent(null));

        Optional<String> stringOptional = Optional.empty();
        stringOptional.ifPresent(System.out::print);
    }

    /**
     * Optional<T> stringOptional = Optional.ofNullable(t);
     * t为null时候,返回一个T类型的(默认)值
     */
    @Test
    void orElse() {

        Optional<String> stringOptional = Optional.empty();
        System.out.println(stringOptional.orElse("empty"));
    }

    /**
     * Optional<T> stringOptional = Optional.ofNullable(t);
     * t为null时候,传入一个接口,并返回类型T的值
     */
    @Test
    void orElseGet() {

        Optional<String> stringOptional = Optional.empty();
        stringOptional.orElseGet(this::empty);

    }

    /**
     * Optional<T> stringOptional = Optional.ofNullable(t);
     * t为null时候,抛出一个异常
     */
    @Test
    void orElseThrow() {

        String string = StringKit.empty;
        if (bool) {
            string = null;
        }
        Optional<String> stringOptional = Optional.ofNullable(string);
        try {
            System.out.println(stringOptional.orElseThrow(RuntimeException::new));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Optional<T> stringOptional = Optional.ofNullable(t);
     * t 不为null时，执行函数，并有返回值,且是可以无限级联的(中间中断则不往下走)
     * !!! 需要有结束函数
     */
    @Test
    void map() {

        Optional<String> stringOptional = Optional.of("23");

        stringOptional.map(s -> s + "000").map(s -> s + "0000").ifPresent(System.out::println);
        stringOptional.map(s -> {
            System.out.println(s);
            return null;
        }).map(s -> s + "0000").ifPresent(System.out::println);

        System.out.println(stringOptional.map(s -> s + "000").orElse("1000"));
        System.out.println(stringOptional.map(s -> {
            System.out.println(s);
            return null;
        }).orElse("1000"));


        //普通的String可能看不出联写的好处 实体可能就容易看出了(省掉了好多if判断)
        //case :
        System.out.println(getTitleUpper());
    }

    /**
     * 过滤器,同stream中的filter
     */
    @Test
    void filter() {

        Optional<String> stringOptional = Optional.empty();
//        stringOptional.filter(String::isEmpty).ifPresent(s -> System.out.println("empty"));
        System.out.println(stringOptional.filter(s -> {
            System.out.println(s);
            return s.length() > 2;
        }).orElse("empty"));
    }

    /**
     * flatMap方法与map方法类似，区别在于mapping函数的返回值不同。map方法的mapping函数返回值可以是任何类型T，
     * 而flatMap方法的mapping函数必须是Optional。
     */
    @Test
    void flatMap() {

        Optional<String> stringOptional = Optional.of("abc");
        stringOptional.flatMap(s -> Optional.of(s.toUpperCase())).ifPresent(System.out::print);
    }

    private String getTitleUpper() {
        Article article;
        if (!bool) {
            article = null;
        } else {
            article = new Article("title");
        }
        Optional<Article> optional = Optional.ofNullable(article);
        return optional.map(Article::getTitle).map(String::toUpperCase).orElseGet(this::empty);
    }

    private String testIfPresent(Article article) {

        Optional<Article> articleOptional = Optional.ofNullable(article);
        return articleOptional.map(article1 -> {
            System.out.println(article1.getTitle());
            System.out.println(article1.getCount());
            return "success";
        }).orElse("fail");
    }

    private String empty() {

        return "this is an empty string";
    }
}
