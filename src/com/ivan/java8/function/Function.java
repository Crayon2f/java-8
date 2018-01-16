package com.ivan.java8.function;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ivan.java8.pojo.Article;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.*;

/**
 * Predicate<T>——接收T对象并返回boolean
 * Consumer<T>——接收T对象，不返回值
 * Function<T, R>——接收T对象，返回R对象
 * Supplier<T>——提供T对象（例如工厂），不接收值
 * UnaryOperator<T>——接收T对象，返回T对象
 * BinaryOperator<T>——接收两个T对象，返回T对象
 * BiFunction<T, U, R>，它接收T对象和U对象，返回R对象。
 * <p>
 * Created by feiFan.gou on 2017/8/15 18:33.
 */
public class Function {

    @Test
    public void function() {

        java.util.function.Function<List<String>, String> function = (x) -> {
            x.forEach(System.out::println); //当放只有一个参数,且,参数类型刚好是Function中参数T的类型,可使用lambda表达式Object::method,且不用写参数
            return "function";
        };
        System.out.println(function.apply(Lists.newArrayList("a", "b")));

        java.util.function.Function<Integer, String> fun = (integer) -> {
            if (null != integer) {
                return integer.toString() + "[string]";
            }
            return "integer is null";
        };
        System.out.println(fun.apply(null));
//        // guava Maps asMap 方法实例
//        {
//            Map<String, String> map = Maps.asMap(Sets.newHashSet("123", "345", "444"), key -> key + 111);
//            System.out.println(map);
//        }
//
//        // after 和 compose 方法的 case
//        Function<Integer, Integer> times2 = e -> e * 2;
//        Function<Integer, Integer> squared = e -> e * e;
//
//        times2.compose(squared).apply(4); // Returns 32,以调用者(times)为基础,在调用者之前,执行squared, 4² * 2 = 32
//        times2.andThen(squared).apply(4); // Returns 64,以调用者(times)为基础,在调用者之后,执行squared, (4 * 2)² = 64


    }

    /**
     * Consumer<T>——接收T对象，不返回值
     */
    @Test
    public void consumer() throws InterruptedException {

        Consumer<String> consumer = x -> {
            System.out.println(x);
            System.out.println(x + x);
        };

        Consumer<Thread> threadConsumer = Thread::start;
        threadConsumer.accept(new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("0000000000000");
        }));
        consumer.accept("xx");

        Thread.sleep(3000);

    }

    /**
     * Supplier<T>——提供T对象（例如工厂），不接收值
     */
    @Test
    public void supplier() {

        Supplier<List<String>> supplier = () -> Lists.newArrayList("aa", "bb");

        System.out.println(supplier.get());
    }

    /**
     * Predicate<T>——接收T对象并返回boolean
     */
    @Test
    public void predicate() {

        Predicate<Integer> predicate = x -> x == 12;
        /**
         * test():执行函数
         * negate():取反
         * and():取两个值的并集
         * or():取两个值的合集
         */
        System.out.println(predicate.test(12)); // 12 == 12 so return true
        System.out.println(predicate.negate().test(12));// 12 == 12的取反 so return false
        System.out.println(predicate.and(y -> y == 3).test(12)); //12 == 12 && 12 == 3 so return false
        System.out.println(predicate.or(y -> y == 3).test(12)); // 12 == 12 || 12 ==3 so return true
    }

    /**
     * UnaryOperator<T>——接收T对象，返回T对象
     */
    @Test
    public void unaryOperator() { //一元运算,继承Function 和Function不同的是:传入值和返回值类型一致

        UnaryOperator<List<String>> operator = strings -> strings.subList(1, 3);

        System.out.println(operator.apply(Lists.newArrayList("0","1","2")));

        System.out.println();
    }


    @Test
    public void toIntFunction() {

        ToIntFunction<List<Article>> intFunction = List::size;

        System.out.println(intFunction.applyAsInt(Lists.newArrayList()));
    }

//    public void
}


