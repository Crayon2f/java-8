package com.ivan.java8.collection.list;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Spliterator;

/**
 * Created by feiFan.gou on 2017/12/1 10:28.
 */
class ListNewly {

    List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5, 6);

    @Test
    void splIterator() {

        Spliterator<Integer> a =  list.spliterator();
        //此时结果：a:0-9（index-fence）
//        if (a.tryAdvance(integer -> integer * 2) {
//            a.forEachRemaining(System.out::println);
//        }
        Spliterator<Integer> b = a.trySplit();
        //此时结果：b:4-9,a:0-4
        Spliterator<Integer> c = a.trySplit();
        //此时结果：c:4-6,b:4-9,a:6-9
        Spliterator<Integer> d = a.trySplit();
        //此时结果：d:6-7,c:4-6,b:4-9,a:7-9
    }
}
