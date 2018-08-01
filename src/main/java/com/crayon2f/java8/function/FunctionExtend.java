package com.crayon2f.java8.function;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import javafx.util.Builder;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileFilter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * Created by feiFan.gou on 2018/7/6 15:28.
 * Function 的扩展, 争取把函数式编程吃透
 */
class FunctionExtend {

    @Test
    @DisplayName("function的扩展")
    void function() {

        Function<String, Integer> function = String::length;

        Function<Integer, Person> then = Person::new;

        Function<Double, String> compose = Object::toString;

        System.out.println(function.andThen(then).apply("14"));

        System.out.println(function.compose(compose).apply(23d)); // 23d toString() = 23.0 so length = 4

        Map<Integer, String> map = ImmutableMap.of(12, "jack", 23, "tom");

        BiFunction<Integer, String, Person> biFunction = Person::new;

        System.out.println(biFunction.andThen(Person::toString).apply(23, "jack"));

        map.forEach((key, value) -> System.out.println(biFunction.apply(key, value)));

        /* print:
            Person(age=2, name=null)
            4
            Person(age=23, name=jack)
            Person(age=12, name=jack)
            Person(age=23, name=tom)
         */

        //Returns a function that always returns its input argument.
        Function<Object, Object> identity = Function.identity();
        System.out.println(identity.apply("34"));
    }

    @Test
    @DisplayName("consumer的扩展")
    void consumer() {

        Consumer<String> consumer = System.out::println;

        Consumer<String> andThen = consumer.andThen(consumer).andThen(consumer);

        List<String> list = Lists.newArrayList("000", "123", "34");

        list.forEach(andThen);

        BiConsumer<String, Integer> biConsumer = ((s, integer) -> System.out.println(s + ":" + integer));

        BiConsumer<String, Integer> then = biConsumer.andThen(biConsumer).andThen(biConsumer);

        Map<String, Integer> map = ImmutableMap.of("aa", 2, "34", 90);

        map.forEach(then);

    }

    @Test
    @DisplayName("predicate的扩展")
    void predicate() {

        Predicate<BooleanObject> predicate1 = BooleanObject::isFlag;
        Predicate<BooleanObject> and1 = predicate1
                .and(BooleanObject::isFlag1).and(BooleanObject::isFlag2).and(BooleanObject::isFlag3);

        Predicate<BooleanObject> predicate2 = BooleanObject::isFlag4;
        Predicate<BooleanObject> and2 =
                predicate2.and(BooleanObject::isFlag5).and(BooleanObject::isFlag6).and(BooleanObject::isFlag7);

        //嵌套 类似 sql里面的 select * from table where (cnd1 and cnd2) or (cnd3 and cnd4)
        Predicate<BooleanObject> or = and1.or(and2);
        System.out.println(or.test(new BooleanObject()));

        List<BooleanObject> list = Lists.newArrayList(new BooleanObject(), new BooleanObject(), new BooleanObject());
        List<BooleanObject> filterList = list.stream().filter(or).collect(Collectors.toList());
        filterList.forEach(System.out::println);

        //取反
        predicate1.negate().test(new BooleanObject());

        //equals 相当于创建了一个 equals 的 Predicate 函数

        System.out.println(Predicate.isEqual("9999").test("999"));

        BiPredicate<String, Integer> biPredicate = (s, integer) -> Integer.valueOf(s) > integer;
        System.out.println(biPredicate.test("12", 10));

    }

    @Getter
    @ToString
    private class BooleanObject {
        private boolean flag = false;
        private boolean flag1 = true;
        private boolean flag2 = true;
        private boolean flag3 = false;
        private boolean flag4 = true;
        private boolean flag5 = false;
        private boolean flag6 = false;
        private boolean flag7 = true;
    }

    @Test
    @DisplayName("javafx.util.Builder")
    void builder() {

        Builder<String> builder = () -> "s";
        System.out.println(builder.build());
        //和 supplier一样好不好
    }

    @Test
    void fileFilter() {

        FileFilter filter = File::exists;

        System.out.println(filter.accept(new File("C:\\Users\\Lenovo\\Desktop\\test.xlsx")));
    }

    @Test
    @SneakyThrows
    void callable() {

        Callable<String> callable = () -> "s";

        System.out.println(callable.call());

        Maps.EntryTransformer<String, String, Integer> transformer = (key, value) -> key != null ? key.length() : 0;

        System.out.println(transformer.transformEntry("12", "2dd3"));
    }

    @Test
    void operator() {

        //extends Function<T, T> 入参和返回值类型一致的Function
        UnaryOperator<String> operator = UnaryOperator.identity();
        //其实就是 operator = t -> t 原值返回
        List<Person> personList = Lists.newArrayList(new Person(12), new Person(23, "jack"));

        Map<Integer, Person> personMap = personList.stream()
                .map(UnaryOperator.identity())
                .filter(ths -> ths.getAge() > 9)
                .collect(Collectors.toMap(Person::getAge, UnaryOperator.identity()));

//        Map<Integer, String> map = personList.stream().collect(Collectors.toMap(Person::getAge, Person::getName));

        //extends BiFunction<T,T,T> 两个入参和返回的类型一致的BiFunction
        BinaryOperator<String> binaryOperator = (s, s2) -> s;

        System.out.println(BinaryOperator.<Integer>minBy(Comparator.naturalOrder()).apply(23, 34));
        System.out.println(BinaryOperator.<Integer>maxBy(Comparator.naturalOrder()).apply(23, 34));

    }

    @Getter
    class Person {

        private int age;
        private String name;

        Person(int age, String name) {
            this.age = age;
            this.name = name;
        }

        Person(int age) {
            this.age = age;
        }

        public Person(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object obj) {
            if (null == obj) {
                return false;
            }
            Person person = (Person) obj;
            return person.name.equals(this.name);
        }

        @Override
        public int hashCode() {
            return this.age;
        }
    }


}
