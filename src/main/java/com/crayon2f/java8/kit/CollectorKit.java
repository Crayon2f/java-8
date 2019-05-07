package com.crayon2f.java8.kit;

import com.crayon2f.common.pojo.Article;
import com.crayon2f.java8.collector.impl.ToMap;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Created by feifan.gou@gmail.com on 2019/5/6 16:25.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CollectorKit {

    public static <T, K, V> Collector<T, Map<K, V>, Map<K, V>> toMap(Function<T, K> keyMapper, Function<T, V> valueMapper) {

        return toMap(keyMapper, valueMapper, HashMap::new);
    }

    public static <T, K, V> Collector<T, Map<K, V>, Map<K, V>> toTreeMap(Function<T, K> keyMapper, Function<T, V> valueMapper) {

        return toMap(keyMapper, valueMapper, TreeMap::new);
    }

    public static <T, K, V> Collector<T, Map<K, V>, Map<K, V>> toLinkedHashMap(Function<T, K> keyMapper, Function<T, V> valueMapper) {

        return toMap(keyMapper, valueMapper, LinkedHashMap::new);
    }

    public static <T, K, V> Collector<T, Map<K, V>, Map<K, V>> toMap(Function<T, K> keyMapper, Function<T, V> valueMapper,
                                                                     Supplier<Map<K, V>> supplier) {

        return toMap(keyMapper, valueMapper, (pre, next) -> next, supplier);
    }

    public static <T, K, V> Collector<T, Map<K, V>, Map<K, V>> toMap(Function<T, K> keyMapper, Function<T, V> valueMapper,
                                                                     BinaryOperator<V> mergeFunction, Supplier<Map<K, V>> supplier) {

        return new ToMap<>(keyMapper, valueMapper, mergeFunction, supplier);
    }

    public static void main(String[] args) {

        ArrayList<Article> list = Lists.newArrayList(new Article("标题4", "作者1", 12),
                new Article("标题4", "作者3", 10),
                new Article("标题4", "作者4", 11, 3.4),
                new Article("标题4", null, 1),
                new Article("标题10", "作者5", 3, 3d),
                new Article("标题6", "作者6", 4, 7d),
                new Article("标题2", "作者7", 7),
                new Article("标题8", "作者8", 5),
                new Article("标题9", "作者9", 2, 8.3),
                new Article("标题5", "作者10", 6),
                new Article("标题11", "作者11", 9),
                new Article("标题12", "作者12", 8, 9.0d));
//        Map<String, String> map = list.stream().collect(Collectors.toMap(Article::getTitle, Article::getAuthor));
        Map<String, String> map = list.stream().collect(CollectorKit.toTreeMap(Article::getTitle, Article::getAuthor));
        System.out.println(map);

    }
}
