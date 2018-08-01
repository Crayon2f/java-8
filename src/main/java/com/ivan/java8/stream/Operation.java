package com.ivan.java8.stream;

import com.crayon2f.common.pojo.Article;
import com.ivan.java8.kit.StringKit;
import com.crayon2f.common.pojo.Employee;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by feiFan.gou on 2018/2/9 10:18.
 */
class Operation {

    /**
     * 在每消费一个流之前,偷偷提前消费。
     */
    @Test
    void peek() {

        int sum = Stream.of(1, 2, 3, 4, 5)
                .peek(e -> System.out.println("Taking integer: " + e))
                .peek(e -> System.out.println("Taking again integer: " + e))
                .filter(n -> n % 2 == 1)
                .peek(e -> System.out.println("Filtered integer: " + e))
                .map(n -> n * n).peek(e -> System.out.println("Mapped integer: " + e))
                .reduce(0, Integer::sum);
        System.out.println("Sum = " + sum);
    }

    /**
     * 消费
     */
    @Test
    void forEach() {
    }


    /**
     * 过滤器
     */
    @Test
    void filter() {

        Article.data.stream().filter(Article::getWhetherPrivate).forEach(System.out::println);
    }

    /**
     * You can apply the map operation on a stream using one of the following methods of the Stream<T> interface
     */
    @Test
    void map() {

        IntStream.rangeClosed(1, 5).map(n -> n * n).forEach(System.out::println); //1~5
        IntStream.range(1, 5).map(n -> n * n).forEach(System.out::println); // 1~4

        Article.data.stream().map(Article::getTitle).forEach(System.out::println);
    }

    /**
     * 和map类似，不同的是其每个元素转换得到的是Stream对象，会把子Stream中的元素压缩到父集合中,并且会覆盖父集合；
     * Streams flatMap（）支持一对多映射。它将每个元素映射到一个流，然后将流的流化成流。
     */
    @Test
    void flatMap() {

        Stream.of(1, 22, 333, 4444).flatMap(integer -> {
            Stream<String> stringStream = Stream.<String>builder().add("dd").add("ddcc").add("ddf").build();
//            Stream<String> stringStream = Stream.<String>builder().add(integer.toString()).build();
            System.out.println(StringKit.divide);
            return stringStream;
            //XXX 执行顺序是,每转化一个元素，消费一个
//        }).forEach(System.out::println);
//            print :
            /*
             *   ========================================== divide line ===============================================
                1
                 ========================================== divide line ===============================================
                2
                 ========================================== divide line ===============================================
                3
                 ========================================== divide line ===============================================
                4
             */
//        }).findFirst().filter(s -> s.length() > 2).ifPresent(System.out::println); //findFirst 只转化一次
        /*
             ========================================== divide line ===============================================
             1
         */
//        }).filter(s -> s.length() > 2).forEach(System.out::println);
        /*
         ========================================== divide line ===============================================
         ========================================== divide line ===============================================
         ========================================== divide line ===============================================
        333
         ========================================== divide line ===============================================
        4444

         */
        }).map(s -> String.format("==== %s ====", s)).forEach(System.out::println);

        "string".chars().mapToObj(i -> new Article("title", "author", i)).forEach(System.out::println);
    }


    @Test
    void reduce() {

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        int sum = numbers.stream()
                .reduce(0, Integer::sum);
        System.out.println(sum); //15
        System.out.println(StringKit.divide);

        double doubleSum = Employee
                .persons()
                .parallelStream()
                .reduce(
                        0.0,
                        (Double partialSum, Employee p) -> {
                            double accumulated = partialSum + p.getIncome();
                            System.out.println(Thread.currentThread().getName()
                                    + "  - Accumulator: partialSum  = " + partialSum
                                    + ",  person = " + p + ", accumulated = " + accumulated);
                            return accumulated;
                        },
                        (a, b) -> {
                            double combined = a + b;
                            System.out.println(Thread.currentThread().getName()
                                    + "  - Combiner:  a  = " + a + ", b  = " + b
                                    + ", combined  = " + combined);
                            return combined;
                        });
        System.out.println(doubleSum);
        System.out.println(StringKit.divide);
        doubleSum = Employee
                .persons()
                .stream()
                .reduce(
                        0.0,
                        (Double partialSum, Employee p) -> {
                            double accumulated = partialSum + p.getIncome();
                            System.out.println(Thread.currentThread().getName()
                                    + "  - Accumulator: partialSum  = " + partialSum
                                    + ",  person = " + p + ", accumulated = " + accumulated);
                            return accumulated;
                        },
                        (a, b) -> {
                            double combined = a + b;
                            System.out.println(Thread.currentThread().getName()
                                    + "  - Combiner:  a  = " + a + ", b  = " + b
                                    + ", combined  = " + combined);
                            return combined;
                        });
        System.out.println(doubleSum);
    }

    /**
     * 聚合
     */
    @Test
    void aggregation() {

        double sum = Employee.persons()
                .stream()
                .mapToDouble(Employee::getIncome)
                .sum();
        System.out.println(sum);
        System.out.println(StringKit.divide);
        sum = Employee.persons().stream().mapToDouble(Employee::getIncome).sum();
        System.out.println(sum);
        System.out.println(StringKit.divide);
        Employee.persons().stream().max(Comparator.comparingDouble(Employee::getIncome)).ifPresent(employee -> System.out.println(employee.getName()));

    }

    @Test
    void toMap() {

        Employee.persons().stream()
                .collect(Collectors.toMap(
                        Employee::getGender,
                        employee -> 1,
                        (prev, next) -> prev + next)).forEach((key, value) -> System.out.println(String.format("{%s:%d}", key, value)
        ));

        Employee.persons().stream()
                .collect(Collectors.groupingBy(Employee::getGender)).forEach((key, value) -> System.out.println(String.format("{%s:%d}", key, value.size())));
    }

    @Test
    void test() {

//        Article.data.parallelStream().forEachOrdered(article -> {
//            System.out.println(String.format("thread-name : %s", Thread.currentThread().getName()));
//            System.out.println(article.getTitle());
//        });
//        DoubleSummaryStatistics summaryStatistics = new DoubleSummaryStatistics();
////        Collectors.toMap()
//        System.out.println(Function.identity().apply("string").toString());

        System.out.println(Article.data.stream().map(article -> article.getCount().toString()).collect(Collectors.joining(",")));
        System.out.println(Article.data.parallelStream().map(article -> {
            System.out.println(Thread.currentThread().getName());
            return article.getCount().toString();
        }).collect(Collectors.joining(",")));
        System.out.println(Article.data.stream()
                .filter(article -> article.getCount() > 3)
                .parallel()
                .map(article -> article.getCount().toString()).collect(Collectors.joining(",")));
    }
}
