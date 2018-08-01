package com.ivan.java8.function;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.crayon2f.common.pojo.Article;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.*;
import java.util.regex.Pattern;

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
class Function {

    @Test
    void function() {

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
    void consumer() throws InterruptedException {

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
    void supplier() {

        Supplier<List<String>> supplier = () -> Lists.newArrayList("aa", "bb");

        System.out.println(supplier.get());
    }

    /**
     * Predicate<T>——接收T对象并返回boolean
     */
    @Test
    void predicate() {

        Predicate<Integer> predicate = x -> x == 12;
        /*
         * test():执行函数
         * negate():取反
         * and():取两个值的并集
         * or():取两个值的合集
         */
        System.out.println(predicate.test(12)); // 12 == 12 so return true
        System.out.println(predicate.negate().test(12));// 12 == 12的取反 so return false
        System.out.println(predicate.and(y -> y == 3).test(12)); //12 == 12 && 12 == 3 so return false
        System.out.println(predicate.or(y -> y == 3).test(12)); // 12 == 12 || 12 ==3 so return true
        System.out.println(Pattern.compile("[\\d+]").asPredicate().test("34"));
    }

    /**
     * UnaryOperator<T>——接收T对象，返回T对象
     */
    @Test
    void unaryOperator() { //一元运算,继承Function 和Function不同的是:传入值和返回值类型一致

        UnaryOperator<List<String>> operator = strings -> strings.subList(1, 3);

        System.out.println(operator.apply(Lists.newArrayList("0","1","2")));

        System.out.println();
    }


    @Test
    void toIntFunction() {

        ToIntFunction<List<Article>> intFunction = List::size;

        System.out.println(intFunction.applyAsInt(Lists.newArrayList()));
    }

//    void


    //各类函数汇总
    /**
     *
     *  1	BiConsumer<T,U>
     代表了一个接受两个输入参数的操作，并且不返回任何结果

     2	BiFunction<T,U,R>
     代表了一个接受两个输入参数的方法，并且返回一个结果

     3	BinaryOperator<T>
     代表了一个作用于于两个同类型操作符的操作，并且返回了操作符同类型的结果

     4	BiPredicate<T,U>
     代表了一个两个参数的boolean值方法

     5	BooleanSupplier
     代表了boolean值结果的提供方

     6	Consumer<T>
     代表了接受一个输入参数并且无返回的操作

     7	DoubleBinaryOperator
     代表了作用于两个double值操作符的操作，并且返回了一个double值的结果。

     8	DoubleConsumer
     代表一个接受double值参数的操作，并且不返回结果。

     9	DoubleFunction<R>
     代表接受一个double值参数的方法，并且返回结果

     10	DoublePredicate
     代表一个拥有double值参数的boolean值方法

     11	DoubleSupplier
     代表一个double值结构的提供方

     12	DoubleToIntFunction
     接受一个double类型输入，返回一个int类型结果。

     13	DoubleToLongFunction
     接受一个double类型输入，返回一个long类型结果

     14	DoubleUnaryOperator
     接受一个参数同为类型double,返回值类型也为double 。

     15	Function<T,R>
     接受一个输入参数，返回一个结果。

     16	IntBinaryOperator
     接受两个参数同为类型int,返回值类型也为int 。

     17	IntConsumer
     接受一个int类型的输入参数，无返回值 。

     18	IntFunction<R>
     接受一个int类型输入参数，返回一个结果 。

     19	IntPredicate
     ：接受一个int输入参数，返回一个布尔值的结果。

     20	IntSupplier
     无参数，返回一个int类型结果。

     21	IntToDoubleFunction
     接受一个int类型输入，返回一个double类型结果 。

     22	IntToLongFunction
     接受一个int类型输入，返回一个long类型结果。

     23	IntUnaryOperator
     接受一个参数同为类型int,返回值类型也为int 。

     24	LongBinaryOperator
     接受两个参数同为类型long,返回值类型也为long。

     25	LongConsumer
     接受一个long类型的输入参数，无返回值。

     26	LongFunction<R>
     接受一个long类型输入参数，返回一个结果。

     27	LongPredicate
     R接受一个long输入参数，返回一个布尔值类型结果。

     28	LongSupplier
     无参数，返回一个结果long类型的值。

     29	LongToDoubleFunction
     接受一个long类型输入，返回一个double类型结果。

     30	LongToIntFunction
     接受一个long类型输入，返回一个int类型结果。

     31	LongUnaryOperator
     接受一个参数同为类型long,返回值类型也为long。

     32	ObjDoubleConsumer<T>
     接受一个object类型和一个double类型的输入参数，无返回值。

     33	ObjIntConsumer<T>
     接受一个object类型和一个int类型的输入参数，无返回值。

     34	ObjLongConsumer<T>
     接受一个object类型和一个long类型的输入参数，无返回值。

     35	Predicate<T>
     接受一个输入参数，返回一个布尔值结果。

     36	Supplier<T>
     无参数，返回一个结果。

     37	ToDoubleBiFunction<T,U>
     接受两个输入参数，返回一个double类型结果

     38	ToDoubleFunction<T>
     接受一个输入参数，返回一个double类型结果

     39	ToIntBiFunction<T,U>
     接受两个输入参数，返回一个int类型结果。

     40	ToIntFunction<T>
     接受一个输入参数，返回一个int类型结果。

     41	ToLongBiFunction<T,U>
     接受两个输入参数，返回一个long类型结果。

     42	ToLongFunction<T>
     接受一个输入参数，返回一个long类型结果。

     43	UnaryOperator<T>
     接受一个参数为类型T,返回值类型也为T。
     */
}


