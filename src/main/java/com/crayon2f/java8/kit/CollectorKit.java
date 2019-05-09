package com.crayon2f.java8.kit;

import com.crayon2f.java8.collector.impl.ToMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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
}
