package com.ivan.java8.stream;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ivan.java8.kit.StringKit;
import com.ivan.java8.pojo.Article;
import com.ivan.java8.pojo.Employee;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by feiFan.gou on 2017/10/14 17:16.
 */
public class StreamLearn {


    @Test
    public void sort() {

        List<Article> sortList = Article.data.stream().sorted(Comparator.comparing(Article::getCount)).collect(Collectors.toList());

        List<Article> reversedList = Article.data.stream().sorted(Comparator.comparing(Article::getCount).reversed()).collect(Collectors.toList());

        sortList.forEach(System.out::println);

        System.out.println("==========================");

        reversedList.forEach(System.out::println);
    }

    @Test
    public void filter() {

        List<Article> filterList = Article.data.stream().filter(article -> null != article.getAuthor() && article.getAuthor().length() == 4).collect(Collectors.toList());

        filterList.forEach(System.out::println);
    }

    @Test
    public void map() {

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
    public void flatMap() {

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
    public void peek() {

        int number = Stream.of(1,2,3,4,5).peek(e -> System.out.println("Taking integer: " + e))
                .filter(n -> n % 2 == 1)
                .peek(e -> System.out.println("Filtered integer: " + e))
                .map(n -> n * n).peek(e -> System.out.println("Mapped integer: " + e))
                .reduce(0, Integer::sum);
        System.out.println(number);
    }

    @Test
    public void limit() {

        System.out.println(Article.data.stream().limit(3).collect(Collectors.toList()));
    }

    @Test
    public void skip() {

        System.out.println(Article.data.stream().skip(9).collect(Collectors.toList()));

        //于limit结合
        System.out.println("======= 与limit结合 =======");
        System.out.println(Article.data.stream().limit(10).skip(9).collect(Collectors.toList()));
    }

    @Test
    public void mapToInt() {

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

    /**
     * findFirst : 不管是stream() 还是 parallelStream(),都获取第一个
     * findAny : stream()时,与findFirst一样,获取第一个;但是当是parallelStream()时候,获得随机
     */
    @Test
    public void find() {

//        List<String> stringList = Collections.checkedList()
        List<String> stringList = Lists.newArrayList();
        stringList.add("a");
        stringList.add("ccc");
        stringList.add("ddd");
        stringList.add("eight");
        stringList.add("face");
        stringList.add("face4");
        stringList.add("face6");
        stringList.add("bb");
        stringList.add("face1");
        stringList.add("face3");
        stringList.add("face5");
//        Collections.shuffle(stringList);
        for (int i = 0; i < 1000; i++) {
            Optional<String> stringOptional = stringList.parallelStream().findAny();
            stringOptional.ifPresent(System.out::println);
        }
        System.out.println("+++++++++++++++++++++++++++++++++");
        for (int i = 0; i < 20; i++) {
            Optional<String> stringOptional = stringList.parallelStream().findFirst();
            stringOptional.ifPresent(System.out::println);
        }


    }

    @Test
    public void reduce() {

        List<String> stringList = Lists.newArrayList("a", "b", "c", "d", "e", "f", "g");
        Stream<String> stringStream = stringList.stream();
        { // param_type 1 : BinaryOperator
            Optional<String> reduceBinaryOperator = stringStream.reduce((first, second) -> {
                System.out.println(first);
                System.out.println(second);
                System.out.println("==================");
                if (second.equals("c")) {
                    throw new RuntimeException();
                }
                return first + second;
            });
            reduceBinaryOperator.ifPresent(System.out::println);
        }
        { //param_type 2: identity参数，用来指定Stream循环的初始值。如果Stream为空，就直接返回该值。另一方面，该方法不会返回Optional，因为该方法不会出现null。
//            String reduceIdentity = stringStream.reduce("-1", (first, second) -> {
//                System.out.println(first);
//                System.out.println(second);
//                System.out.println("==================");
//                return first + second;
//            });
//            System.out.println(reduceIdentity);
        }
        { //param_type 3 Stream是支持并发操作的，为了避免竞争，对于reduce线程都会有独立的result，combiner的作用在于合并每个线程的result得到最终结果。
            // 这也说明了了第三个函数参数的数据类型必须为返回数据类型了 当

            // 使用队列可以保证线程安全
//            ArrayBlockingQueue<String> queue = Queues.newArrayBlockingQueue(100);
//            CopyOnWriteArrayList<String> copyOnWriteList = new CopyOnWriteArrayList<>();
//            ArrayBlockingQueue blockingQueue = Lists.newArrayList("1", "2", "3").parallelStream().reduce(queue, (first, second) -> {
//                try {
//                    first.put(second);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("accumulator");
//                System.out.println(first);
//                return first;
//            }, (first, second) -> {
//                System.out.println("combiner");
//                System.out.println(first);
//                return first;
//            });
//            System.out.println(stringArrayList);
//            System.out.println(i);
        }
    }

    @Test
    /*
      由并行转化成串行
     */
    public void sequential() {
        Stream<String> stream = Arrays.stream(new String[]{"1", "3", "4"});
        System.out.println(stream.sequential().collect(Collectors.toList()));
    }

    @Test
    public void match() {

        System.out.println(Employee.persons().stream().anyMatch(Employee::isMale));
        System.out.println(Employee.persons().stream().noneMatch(Employee::isMale));
        if (Article.data.stream().allMatch(Objects::nonNull)) {
            System.out.println("all not null");
        } else {
            System.out.println("has null article");
        }
    }

    @Test
    public void test() {

        List<String> stringList = Lists.newArrayList();
        Map<String, List<Article>> collect = Article.data.parallelStream().peek(article -> {
            stringList.add(article.getTitle());
        }).collect(Collectors.groupingBy(Article::getAuthor));

        System.out.println(stringList);
        collect.forEach((key, value) -> {
            System.out.println("key = " + key);
            System.out.println("value = " + value.parallelStream().count());
        });
    }
}
